package com.rashid.backend.config;

import com.rashid.backend.model.Project;
import com.rashid.backend.model.Task;
import com.rashid.backend.model.Team;
import com.rashid.backend.model.TeamInvite;
import com.rashid.backend.model.TeamMember;
import com.rashid.backend.model.TimeLog;
import com.rashid.backend.model.TimerSession;
import com.rashid.backend.model.User;
import com.rashid.backend.model.enums.InviteStatus;
import com.rashid.backend.model.enums.Role;
import com.rashid.backend.model.enums.TeamRole;
import com.rashid.backend.repository.ProjectRepository;
import com.rashid.backend.repository.TaskRepository;
import com.rashid.backend.repository.TeamInviteRepository;
import com.rashid.backend.repository.TeamMemberRepository;
import com.rashid.backend.repository.TeamRepository;
import com.rashid.backend.repository.TimeLogRepository;
import com.rashid.backend.repository.TimerSessionRepository;
import com.rashid.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DemoDataSeeder implements CommandLineRunner {

    private static final String DEMO_PASSWORD = "Password123!";

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamInviteRepository teamInviteRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TimeLogRepository timeLogRepository;
    private final TimerSessionRepository timerSessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final boolean seedDemoData;

    public DemoDataSeeder(
            UserRepository userRepository,
            TeamRepository teamRepository,
            TeamMemberRepository teamMemberRepository,
            TeamInviteRepository teamInviteRepository,
            ProjectRepository projectRepository,
            TaskRepository taskRepository,
            TimeLogRepository timeLogRepository,
            TimerSessionRepository timerSessionRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.seed-demo-data:true}") boolean seedDemoData
    ) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.teamInviteRepository = teamInviteRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.timeLogRepository = timeLogRepository;
        this.timerSessionRepository = timerSessionRepository;
        this.passwordEncoder = passwordEncoder;
        this.seedDemoData = seedDemoData;
    }

    @Override
    public void run(String... args) {
        if (!seedDemoData) {
            return;
        }

        if (userRepository.findByUsername("owner.alpha").isPresent()) {
            return;
        }

        Random random = new Random(42);
        List<User> users = createUsers();
        List<Team> teams = createTeams();
        List<Project> projects = createProjects(teams);
        List<Task> tasks = createTasks(projects, users, random);
        createTimeLogs(tasks, users, random);
        createInvites(teams, users);
        createActiveTimer(tasks, users);
    }

    private List<User> createUsers() {
        String[][] userData = new String[][]{
                {"owner.alpha", "owner.alpha@example.com"},
                {"manager.beta", "manager.beta@example.com"},
                {"manager.gamma", "manager.gamma@example.com"},
                {"member.delta", "member.delta@example.com"},
                {"member.echo", "member.echo@example.com"},
                {"member.foxtrot", "member.foxtrot@example.com"},
                {"member.golf", "member.golf@example.com"},
                {"member.hotel", "member.hotel@example.com"},
                {"member.india", "member.india@example.com"},
                {"member.juliet", "member.juliet@example.com"},
                {"member.kilo", "member.kilo@example.com"},
                {"member.lima", "member.lima@example.com"},
                {"member.mike", "member.mike@example.com"},
                {"member.november", "member.november@example.com"},
                {"member.oscar", "member.oscar@example.com"},
                {"member.papa", "member.papa@example.com"},
                {"member.quebec", "member.quebec@example.com"},
                {"member.romeo", "member.romeo@example.com"}
        };

        List<User> users = new ArrayList<>();
        for (String[] entry : userData) {
            User user = new User();
            user.setUsername(entry[0]);
            user.setEmail(entry[1]);
            user.setPassword(passwordEncoder.encode(DEMO_PASSWORD));
            user.setRole(Role.USER);
            users.add(userRepository.save(user));
        }
        return users;
    }

    private List<Team> createTeams() {
        String[][] teamData = new String[][]{
                {"Atlas Product", "Platform and product delivery squad"},
                {"Nova Marketing", "Campaign execution and web funnel optimization"},
                {"Orbit Operations", "Internal operations and workflow automation"},
                {"Pixel Studio", "Design system and frontend delivery"},
                {"Quantum Support", "Customer success process improvements"}
        };

        List<Team> teams = new ArrayList<>();
        for (String[] entry : teamData) {
            Team team = new Team();
            team.setName(entry[0]);
            team.setDescription(entry[1]);
            teams.add(teamRepository.save(team));
        }
        return teams;
    }

    private List<Project> createProjects(List<Team> teams) {
        List<Project> projects = new ArrayList<>();
        String[] projectNames = {
                "Client Portal Refresh", "Mobile Workflow", "Analytics Rollout", "Support Dashboard",
                "Design Tokens", "Campaign Automations", "Billing Ops", "Knowledge Base",
                "Release Train", "Hiring Pipeline"
        };

        int projectIndex = 0;
        for (Team team : teams) {
            for (int i = 0; i < 4; i++) {
                Project project = new Project();
                project.setTeam(team);
                project.setName(projectNames[projectIndex % projectNames.length] + " " + (i + 1));
                project.setDescription("Seeded project for " + team.getName());
                projects.add(projectRepository.save(project));
                projectIndex++;
            }
        }

        return projects;
    }

    private List<Task> createTasks(List<Project> projects, List<User> users, Random random) {
        assignTeamMembers(users);

        String[] prefixes = {
                "Implement", "Review", "Design", "Refine", "Ship", "Document", "Test", "Monitor", "Migrate", "Audit"
        };
        String[] subjects = {
                "authentication flow", "report cards", "team onboarding", "task automation", "support workflow",
                "dashboard layout", "API filters", "timer lifecycle", "role permissions", "release checklist"
        };
        String[] statuses = {"TODO", "IN_PROGRESS", "DONE"};

        List<Task> tasks = new ArrayList<>();
        int titleIndex = 0;
        for (Project project : projects) {
            List<TeamMember> members = teamMemberRepository.findByTeamId(project.getTeam().getId());
            for (int i = 0; i < 8; i++) {
                Task task = new Task();
                task.setProject(project);
                task.setTitle(prefixes[titleIndex % prefixes.length] + " " + subjects[titleIndex % subjects.length] + " #" + (i + 1));
                task.setStatus(statuses[(titleIndex + i) % statuses.length]);
                task.setAssignee(members.get(random.nextInt(members.size())).getUser());
                tasks.add(taskRepository.save(task));
                titleIndex++;
            }
        }

        return tasks;
    }

    private void assignTeamMembers(List<User> users) {
        List<Team> teams = teamRepository.findAll();

        createMembership(teams.get(0), users.get(0), TeamRole.OWNER);
        createMembership(teams.get(0), users.get(1), TeamRole.MANAGER);
        createMembership(teams.get(0), users.get(3), TeamRole.MEMBER);
        createMembership(teams.get(0), users.get(4), TeamRole.MEMBER);
        createMembership(teams.get(0), users.get(5), TeamRole.MEMBER);

        createMembership(teams.get(1), users.get(1), TeamRole.OWNER);
        createMembership(teams.get(1), users.get(2), TeamRole.MANAGER);
        createMembership(teams.get(1), users.get(6), TeamRole.MEMBER);
        createMembership(teams.get(1), users.get(7), TeamRole.MEMBER);
        createMembership(teams.get(1), users.get(8), TeamRole.MEMBER);

        createMembership(teams.get(2), users.get(2), TeamRole.OWNER);
        createMembership(teams.get(2), users.get(9), TeamRole.MANAGER);
        createMembership(teams.get(2), users.get(10), TeamRole.MEMBER);
        createMembership(teams.get(2), users.get(11), TeamRole.MEMBER);
        createMembership(teams.get(2), users.get(12), TeamRole.MEMBER);

        createMembership(teams.get(3), users.get(0), TeamRole.OWNER);
        createMembership(teams.get(3), users.get(13), TeamRole.MANAGER);
        createMembership(teams.get(3), users.get(14), TeamRole.MEMBER);
        createMembership(teams.get(3), users.get(15), TeamRole.MEMBER);
        createMembership(teams.get(3), users.get(16), TeamRole.MEMBER);

        createMembership(teams.get(4), users.get(1), TeamRole.OWNER);
        createMembership(teams.get(4), users.get(2), TeamRole.MANAGER);
        createMembership(teams.get(4), users.get(5), TeamRole.MEMBER);
        createMembership(teams.get(4), users.get(17), TeamRole.MEMBER);
        createMembership(teams.get(4), users.get(4), TeamRole.MEMBER);
    }

    private void createMembership(Team team, User user, TeamRole role) {
        TeamMember member = new TeamMember();
        member.setTeam(team);
        member.setUser(user);
        member.setRole(role);
        teamMemberRepository.save(member);
    }

    private void createTimeLogs(List<Task> tasks, List<User> users, Random random) {
        LocalDateTime now = LocalDateTime.now().minusDays(1);

        for (int i = 0; i < 180; i++) {
            Task task = tasks.get(random.nextInt(tasks.size()));
            User user = task.getAssignee() != null ? task.getAssignee() : users.get(random.nextInt(users.size()));
            int dayOffset = random.nextInt(28);
            int startHour = 8 + random.nextInt(8);
            int durationMinutes = 30 + random.nextInt(180);

            TimeLog log = new TimeLog();
            log.setTask(task);
            log.setUser(user);
            log.setStartTime(now.minusDays(dayOffset).withHour(startHour).withMinute(random.nextInt(2) * 30));
            log.setEndTime(log.getStartTime().plusMinutes(durationMinutes));
            log.setDescription("Seeded work log for " + task.getTitle());
            log.setSource(i % 4 == 0 ? "TIMER" : "MANUAL");
            timeLogRepository.save(log);
        }
    }

    private void createInvites(List<Team> teams, List<User> users) {
        createInvite(teams.get(0), users.get(17), users.get(0), InviteStatus.PENDING, null);
        createInvite(teams.get(1), users.get(15), users.get(1), InviteStatus.PENDING, null);
        createInvite(teams.get(2), users.get(14), users.get(2), InviteStatus.ACCEPTED, LocalDateTime.now().minusDays(3));
        createInvite(teams.get(3), users.get(11), users.get(0), InviteStatus.DECLINED, LocalDateTime.now().minusDays(5));
        createInvite(teams.get(4), users.get(16), users.get(1), InviteStatus.PENDING, null);
    }

    private void createInvite(Team team, User invitedUser, User invitedBy, InviteStatus status, LocalDateTime respondedAt) {
        TeamInvite invite = new TeamInvite();
        invite.setTeam(team);
        invite.setInvitedUser(invitedUser);
        invite.setInvitedBy(invitedBy);
        invite.setStatus(status);
        invite.setCreatedAt(LocalDateTime.now().minusDays(7));
        invite.setRespondedAt(respondedAt);
        teamInviteRepository.save(invite);
    }

    private void createActiveTimer(List<Task> tasks, List<User> users) {
        Task task = tasks.stream()
                .filter(existingTask -> existingTask.getAssignee() != null && existingTask.getAssignee().getUsername().equals("member.delta"))
                .findFirst()
                .orElse(tasks.getFirst());

        TimerSession timer = new TimerSession();
        timer.setTask(task);
        timer.setUser(task.getAssignee() != null ? task.getAssignee() : users.get(3));
        timer.setStartedAt(LocalDateTime.now().minusMinutes(45));
        timer.setDescription("Active seeded timer session");
        timer.setActive(true);
        timerSessionRepository.save(timer);
    }
}
