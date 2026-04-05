# Team Productivity Hub

Team Productivity Hub is a full-stack web application for managing teams, projects, tasks, and time tracking in one place. It was built for the Web Development 2 course and focuses on authenticated team collaboration, task workflow management, reporting, and a RESTful backend.

## Main Features

- JWT-based authentication with register and login flows
- Team creation, member invites, and role-based permissions
- Project and task management for team workspaces
- Task board with status changes and drag-and-drop movement
- Task timer with start, stop, and cancel actions
- Manual time log creation and personal time log history
- Weekly summaries, reports, and dashboard overviews
- Backend filtering and pagination for collection GET endpoints
- Responsive frontend with routed pages and shared component structure

## Tech Stack

- Frontend: Vue.js
- Backend: Java 21, Spring Boot, Spring Security, Spring Data JPA, JWT (As you approved to use java in the beginning of the term)
- Database: MariaDB
- DevOps: Docker 

## Project Structure

```text
.
├── frontend/   Vue application
├── backend/    Spring Boot REST API
└── compose.yaml
```

## Running The Project

### Option 1: Docker Compose

This is the quickest way to run the full application.

1. Make sure Docker Desktop is running.
2. From the project root, start the stack:

```bash
docker compose up --build
```

3. Open the apps:

- Frontend: `http://localhost:5173`
- Backend API: `http://localhost:8080`
- phpMyAdmin: `http://localhost:8081`


## Demo Data

The backend supports seeded demo data through:

- `APP_SEED_DEMO_DATA=true`

This is enabled in the Docker Compose setup, which makes the project easier to demo quickly.

## API Notes

The backend follows a REST-style structure with controllers, services, repositories, DTOs, and centralized error handling.

Examples of available resource groups:

- `/api/auth`
- `/api/teams`
- `/api/projects`
- `/api/tasks`
- `/api/timelogs`
- `/api/timers`
- `/api/reports`
- `/api/dashboard`

Collection GET endpoints support pagination using query parameters such as:

```html
page=0&size=20
```

Task listing also supports filtering, for example:

```text
/api/tasks?teamId=1&status=IN_PROGRESS&search=dashboard&page=0&size=20
```

## Authentication

- Login and registration return a JWT token
- The frontend stores the token and sends it in the `Authorization` header
- Protected backend routes require authentication
- Team permissions are enforced in the backend using role checks

## Testing And Verification

Frontend build:

```bash
cd frontend
npm run build
```

Backend tests:

```bash
cd backend
./mvnw test
```

## Course Submission Notes

I tried to follow the Assignment Rubrics:

- CSS framework usage and responsive layout
- ATOM design for frontend
- RESTful backend with GET, POST, PUT, and DELETE endpoints
- Filtering and pagination on collection endpoints
- JWT authentication and backend authorization
- Structured backend architecture using Spring MVC patterns

Additional planning and scope details are documented in [team_productivity_hub_documentation.md](/Users/rashidismayilzade/Projects/webdev2/web_dev_2_project/team_productivity_hub_documentation.md).

## AI Disclosure

AI tools were used during planning, refinement, documentation, and development support. All code should be reviewed and understood before submission or presentation.
