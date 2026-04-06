-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: mariadb:3306
-- Generation Time: Apr 06, 2026 at 05:37 PM
-- Server version: 11.4.10-MariaDB-ubu2404
-- PHP Version: 8.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `appdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `projects`
--

CREATE TABLE `projects` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `team_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Dumping data for table `projects`
--

INSERT INTO `projects` (`id`, `description`, `name`, `team_id`) VALUES
(1, 'Seeded project for Atlas Product', 'Client Portal Refresh 1', 1),
(2, 'Seeded project for Atlas Product', 'Mobile Workflow 2', 1),
(3, 'Seeded project for Atlas Product', 'Analytics Rollout 3', 1),
(4, 'Seeded project for Atlas Product', 'Support Dashboard 4', 1),
(5, 'Seeded project for Nova Marketing', 'Design Tokens 1', 2),
(6, 'Seeded project for Nova Marketing', 'Campaign Automations 2', 2),
(7, 'Seeded project for Nova Marketing', 'Billing Ops 3', 2),
(8, 'Seeded project for Nova Marketing', 'Knowledge Base 4', 2),
(9, 'Seeded project for Orbit Operations', 'Release Train 1', 3),
(10, 'Seeded project for Orbit Operations', 'Hiring Pipeline 2', 3),
(11, 'Seeded project for Orbit Operations', 'Client Portal Refresh 3', 3),
(12, 'Seeded project for Orbit Operations', 'Mobile Workflow 4', 3),
(13, 'Seeded project for Pixel Studio', 'Analytics Rollout 1', 4),
(14, 'Seeded project for Pixel Studio', 'Support Dashboard 2', 4),
(15, 'Seeded project for Pixel Studio', 'Design Tokens 3', 4),
(16, 'Seeded project for Pixel Studio', 'Campaign Automations 4', 4),
(17, 'Seeded project for Quantum Support', 'Billing Ops 1', 5),
(18, 'Seeded project for Quantum Support', 'Knowledge Base 2', 5),
(19, 'Seeded project for Quantum Support', 'Release Train 3', 5),
(20, 'Seeded project for Quantum Support', 'Hiring Pipeline 4', 5);

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `id` bigint(20) NOT NULL,
  `status` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `assignee_id` bigint(20) DEFAULT NULL,
  `project_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`id`, `status`, `title`, `assignee_id`, `project_id`) VALUES
(1, 'IN_PROGRESS', 'Implement authentication flow #1', 1, 1),
(2, 'DONE', 'Review report cards #2', 5, 1),
(3, 'IN_PROGRESS', 'Design team onboarding #3', 5, 1),
(4, 'TODO', 'Refine task automation #4', 6, 1),
(5, 'DONE', 'Ship support workflow #5', 1, 1),
(6, 'IN_PROGRESS', 'Document dashboard layout #6', 1, 1),
(7, 'TODO', 'Test API filters #7', 1, 1),
(8, 'DONE', 'Monitor timer lifecycle #8', 5, 1),
(9, 'DONE', 'Migrate role permissions #1', 6, 2),
(10, 'IN_PROGRESS', 'Audit release checklist #2', 5, 2),
(11, 'TODO', 'Implement authentication flow #3', 4, 2),
(12, 'DONE', 'Review report cards #4', 4, 2),
(13, 'IN_PROGRESS', 'Design team onboarding #5', 2, 2),
(14, 'TODO', 'Refine task automation #6', 4, 2),
(15, 'DONE', 'Ship support workflow #7', 2, 2),
(16, 'IN_PROGRESS', 'Document dashboard layout #8', 4, 2),
(17, 'IN_PROGRESS', 'Test API filters #1', 2, 3),
(18, 'TODO', 'Monitor timer lifecycle #2', 1, 3),
(19, 'DONE', 'Migrate role permissions #3', 5, 3),
(20, 'IN_PROGRESS', 'Audit release checklist #4', 6, 3),
(21, 'TODO', 'Implement authentication flow #5', 1, 3),
(22, 'DONE', 'Review report cards #6', 5, 3),
(23, 'IN_PROGRESS', 'Design team onboarding #7', 2, 3),
(24, 'TODO', 'Refine task automation #8', 5, 3),
(25, 'TODO', 'Ship support workflow #1', 5, 4),
(26, 'DONE', 'Document dashboard layout #2', 2, 4),
(27, 'IN_PROGRESS', 'Test API filters #3', 1, 4),
(28, 'TODO', 'Monitor timer lifecycle #4', 5, 4),
(29, 'DONE', 'Migrate role permissions #5', 4, 4),
(30, 'IN_PROGRESS', 'Audit release checklist #6', 2, 4),
(31, 'TODO', 'Implement authentication flow #7', 1, 4),
(32, 'DONE', 'Review report cards #8', 2, 4),
(33, 'DONE', 'Design team onboarding #1', 2, 5),
(34, 'IN_PROGRESS', 'Refine task automation #2', 2, 5),
(35, 'TODO', 'Ship support workflow #3', 7, 5),
(36, 'DONE', 'Document dashboard layout #4', 7, 5),
(37, 'IN_PROGRESS', 'Test API filters #5', 7, 5),
(38, 'TODO', 'Monitor timer lifecycle #6', 8, 5),
(39, 'DONE', 'Migrate role permissions #7', 8, 5),
(40, 'IN_PROGRESS', 'Audit release checklist #8', 9, 5),
(41, 'IN_PROGRESS', 'Implement authentication flow #1', 9, 6),
(42, 'TODO', 'Review report cards #2', 2, 6),
(43, 'DONE', 'Design team onboarding #3', 8, 6),
(44, 'IN_PROGRESS', 'Refine task automation #4', 7, 6),
(45, 'TODO', 'Ship support workflow #5', 2, 6),
(46, 'DONE', 'Document dashboard layout #6', 9, 6),
(47, 'IN_PROGRESS', 'Test API filters #7', 7, 6),
(48, 'TODO', 'Monitor timer lifecycle #8', 2, 6),
(49, 'TODO', 'Migrate role permissions #1', 7, 7),
(50, 'DONE', 'Audit release checklist #2', 8, 7),
(51, 'IN_PROGRESS', 'Implement authentication flow #3', 8, 7),
(52, 'TODO', 'Review report cards #4', 2, 7),
(53, 'DONE', 'Design team onboarding #5', 8, 7),
(54, 'IN_PROGRESS', 'Refine task automation #6', 3, 7),
(55, 'TODO', 'Ship support workflow #7', 2, 7),
(56, 'DONE', 'Document dashboard layout #8', 2, 7),
(57, 'DONE', 'Test API filters #1', 8, 8),
(58, 'IN_PROGRESS', 'Monitor timer lifecycle #2', 9, 8),
(59, 'TODO', 'Migrate role permissions #3', 3, 8),
(60, 'DONE', 'Audit release checklist #4', 8, 8),
(61, 'IN_PROGRESS', 'Implement authentication flow #5', 7, 8),
(62, 'TODO', 'Review report cards #6', 9, 8),
(63, 'DONE', 'Design team onboarding #7', 7, 8),
(64, 'IN_PROGRESS', 'Refine task automation #8', 3, 8),
(65, 'IN_PROGRESS', 'Ship support workflow #1', 13, 9),
(66, 'TODO', 'Document dashboard layout #2', 13, 9),
(67, 'DONE', 'Test API filters #3', 12, 9),
(68, 'IN_PROGRESS', 'Monitor timer lifecycle #4', 13, 9),
(69, 'TODO', 'Migrate role permissions #5', 3, 9),
(70, 'DONE', 'Audit release checklist #6', 13, 9),
(71, 'IN_PROGRESS', 'Implement authentication flow #7', 3, 9),
(72, 'TODO', 'Review report cards #8', 10, 9),
(73, 'TODO', 'Design team onboarding #1', 12, 10),
(74, 'DONE', 'Refine task automation #2', 13, 10),
(75, 'IN_PROGRESS', 'Ship support workflow #3', 12, 10),
(76, 'TODO', 'Document dashboard layout #4', 12, 10),
(77, 'DONE', 'Test API filters #5', 10, 10),
(78, 'IN_PROGRESS', 'Monitor timer lifecycle #6', 3, 10),
(79, 'TODO', 'Migrate role permissions #7', 3, 10),
(80, 'DONE', 'Audit release checklist #8', 12, 10),
(81, 'DONE', 'Implement authentication flow #1', 3, 11),
(82, 'IN_PROGRESS', 'Review report cards #2', 13, 11),
(83, 'TODO', 'Design team onboarding #3', 12, 11),
(84, 'DONE', 'Refine task automation #4', 11, 11),
(85, 'IN_PROGRESS', 'Ship support workflow #5', 11, 11),
(86, 'TODO', 'Document dashboard layout #6', 12, 11),
(87, 'DONE', 'Test API filters #7', 13, 11),
(88, 'IN_PROGRESS', 'Monitor timer lifecycle #8', 13, 11),
(89, 'IN_PROGRESS', 'Migrate role permissions #1', 12, 12),
(90, 'TODO', 'Audit release checklist #2', 13, 12),
(91, 'DONE', 'Implement authentication flow #3', 11, 12),
(92, 'IN_PROGRESS', 'Review report cards #4', 10, 12),
(93, 'TODO', 'Design team onboarding #5', 12, 12),
(94, 'DONE', 'Refine task automation #6', 3, 12),
(95, 'IN_PROGRESS', 'Ship support workflow #7', 13, 12),
(96, 'TODO', 'Document dashboard layout #8', 12, 12),
(97, 'TODO', 'Test API filters #1', 17, 13),
(98, 'DONE', 'Monitor timer lifecycle #2', 14, 13),
(99, 'IN_PROGRESS', 'Migrate role permissions #3', 14, 13),
(100, 'TODO', 'Audit release checklist #4', 16, 13),
(101, 'DONE', 'Implement authentication flow #5', 1, 13),
(102, 'IN_PROGRESS', 'Review report cards #6', 1, 13),
(103, 'TODO', 'Design team onboarding #7', 15, 13),
(104, 'DONE', 'Refine task automation #8', 17, 13),
(105, 'DONE', 'Ship support workflow #1', 16, 14),
(106, 'IN_PROGRESS', 'Document dashboard layout #2', 14, 14),
(107, 'TODO', 'Test API filters #3', 17, 14),
(108, 'DONE', 'Monitor timer lifecycle #4', 14, 14),
(110, 'TODO', 'Audit release checklist #6', 15, 14),
(111, 'DONE', 'Implement authentication flow #7', 14, 14),
(112, 'IN_PROGRESS', 'Review report cards #8', 17, 14),
(113, 'IN_PROGRESS', 'Design team onboarding #1', 15, 15),
(114, 'TODO', 'Refine task automation #2', 14, 15),
(115, 'DONE', 'Ship support workflow #3', 14, 15),
(116, 'IN_PROGRESS', 'Document dashboard layout #4', 1, 15),
(117, 'TODO', 'Test API filters #5', 14, 15),
(118, 'DONE', 'Monitor timer lifecycle #6', 1, 15),
(119, 'IN_PROGRESS', 'Migrate role permissions #7', 14, 15),
(120, 'TODO', 'Audit release checklist #8', 17, 15),
(121, 'TODO', 'Implement authentication flow #1', 14, 16),
(122, 'DONE', 'Review report cards #2', 16, 16),
(123, 'TODO', 'Design team onboarding #3', 16, 16),
(124, 'TODO', 'Refine task automation #4', 16, 16),
(125, 'DONE', 'Ship support workflow #5', 15, 16),
(126, 'DONE', 'Document dashboard layout #6', 14, 16),
(127, 'TODO', 'Test API filters #7', 15, 16),
(128, 'DONE', 'Monitor timer lifecycle #8', 16, 16),
(129, 'DONE', 'Migrate role permissions #1', 18, 17),
(130, 'IN_PROGRESS', 'Audit release checklist #2', 6, 17),
(131, 'TODO', 'Implement authentication flow #3', 6, 17),
(132, 'DONE', 'Review report cards #4', 6, 17),
(133, 'IN_PROGRESS', 'Design team onboarding #5', 18, 17),
(134, 'TODO', 'Refine task automation #6', 6, 17),
(135, 'DONE', 'Ship support workflow #7', 6, 17),
(136, 'IN_PROGRESS', 'Document dashboard layout #8', 18, 17),
(137, 'IN_PROGRESS', 'Test API filters #1', 2, 18),
(138, 'TODO', 'Monitor timer lifecycle #2', 6, 18),
(139, 'DONE', 'Migrate role permissions #3', 6, 18),
(140, 'IN_PROGRESS', 'Audit release checklist #4', 18, 18),
(141, 'TODO', 'Implement authentication flow #5', 18, 18),
(142, 'DONE', 'Review report cards #6', 6, 18),
(143, 'IN_PROGRESS', 'Design team onboarding #7', 3, 18),
(144, 'TODO', 'Refine task automation #8', 18, 18),
(145, 'TODO', 'Ship support workflow #1', 5, 19),
(146, 'DONE', 'Document dashboard layout #2', 18, 19),
(147, 'IN_PROGRESS', 'Test API filters #3', 18, 19),
(148, 'TODO', 'Monitor timer lifecycle #4', 18, 19),
(149, 'DONE', 'Migrate role permissions #5', 6, 19),
(150, 'IN_PROGRESS', 'Audit release checklist #6', 3, 19),
(151, 'TODO', 'Implement authentication flow #7', 2, 19),
(152, 'DONE', 'Review report cards #8', 2, 19),
(153, 'DONE', 'Design team onboarding #1', 3, 20),
(154, 'IN_PROGRESS', 'Refine task automation #2', 2, 20),
(155, 'TODO', 'Ship support workflow #3', 3, 20),
(156, 'DONE', 'Document dashboard layout #4', 5, 20),
(157, 'IN_PROGRESS', 'Test API filters #5', 2, 20),
(158, 'TODO', 'Monitor timer lifecycle #6', 3, 20),
(159, 'DONE', 'Migrate role permissions #7', 6, 20),
(160, 'IN_PROGRESS', 'Audit release checklist #8', 3, 20);

-- --------------------------------------------------------

--
-- Table structure for table `teams`
--

CREATE TABLE `teams` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Dumping data for table `teams`
--

INSERT INTO `teams` (`id`, `description`, `name`) VALUES
(1, 'Platform and product delivery squad', 'Atlas Product'),
(2, 'Campaign execution and web funnel optimization', 'Nova Marketing'),
(3, 'Internal operations and workflow automation', 'Orbit Operations'),
(4, 'Design system and frontend delivery', 'Pixel Studio'),
(5, 'Customer success process improvements', 'Quantum Support');

-- --------------------------------------------------------

--
-- Table structure for table `team_invites`
--

CREATE TABLE `team_invites` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `responded_at` datetime(6) DEFAULT NULL,
  `status` enum('ACCEPTED','DECLINED','PENDING') NOT NULL,
  `invited_by_user_id` bigint(20) NOT NULL,
  `invited_user_id` bigint(20) NOT NULL,
  `team_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Dumping data for table `team_invites`
--

INSERT INTO `team_invites` (`id`, `created_at`, `responded_at`, `status`, `invited_by_user_id`, `invited_user_id`, `team_id`) VALUES
(1, '2026-03-29 16:44:00.481490', '2026-04-05 16:53:25.980851', 'ACCEPTED', 1, 18, 1),
(2, '2026-03-29 16:44:00.494972', NULL, 'PENDING', 2, 16, 2),
(3, '2026-03-29 16:44:00.497819', '2026-04-02 16:44:00.497804', 'ACCEPTED', 3, 15, 3),
(4, '2026-03-29 16:44:00.500221', '2026-03-31 16:44:00.500208', 'DECLINED', 1, 12, 4),
(5, '2026-03-29 16:44:00.501797', NULL, 'PENDING', 2, 17, 5),
(6, '2026-04-06 16:37:07.555236', NULL, 'PENDING', 1, 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `team_members`
--

CREATE TABLE `team_members` (
  `id` bigint(20) NOT NULL,
  `role` enum('MANAGER','MEMBER','OWNER') NOT NULL,
  `team_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Dumping data for table `team_members`
--

INSERT INTO `team_members` (`id`, `role`, `team_id`, `user_id`) VALUES
(1, 'OWNER', 1, 1),
(2, 'OWNER', 1, 2),
(4, 'MEMBER', 1, 5),
(5, 'MEMBER', 1, 6),
(6, 'OWNER', 2, 2),
(7, 'MANAGER', 2, 3),
(8, 'MEMBER', 2, 7),
(9, 'MEMBER', 2, 8),
(10, 'MEMBER', 2, 9),
(11, 'OWNER', 3, 3),
(12, 'MANAGER', 3, 10),
(13, 'MEMBER', 3, 11),
(14, 'MEMBER', 3, 12),
(15, 'MEMBER', 3, 13),
(16, 'OWNER', 4, 1),
(17, 'MANAGER', 4, 14),
(18, 'MEMBER', 4, 15),
(19, 'MEMBER', 4, 16),
(20, 'MEMBER', 4, 17),
(21, 'OWNER', 5, 2),
(22, 'MANAGER', 5, 3),
(23, 'MEMBER', 5, 6),
(24, 'MEMBER', 5, 18),
(25, 'MEMBER', 5, 5),
(26, 'MEMBER', 1, 18);

-- --------------------------------------------------------

--
-- Table structure for table `timer_sessions`
--

CREATE TABLE `timer_sessions` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `started_at` datetime(6) NOT NULL,
  `stopped_at` datetime(6) DEFAULT NULL,
  `task_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Dumping data for table `timer_sessions`
--

INSERT INTO `timer_sessions` (`id`, `active`, `description`, `started_at`, `stopped_at`, `task_id`, `user_id`) VALUES
(1, b'0', 'Working on Refine task automation #4', '2026-04-05 16:49:14.099059', '2026-04-05 16:52:32.663088', 124, 1),
(2, b'0', 'Working on Test API filters #7', '2026-04-05 16:52:34.295108', '2026-04-05 16:52:37.541840', 127, 1),
(3, b'0', 'Working on Design team onboarding #3', '2026-04-05 17:01:05.809942', '2026-04-05 17:01:12.384332', 123, 1);

-- --------------------------------------------------------

--
-- Table structure for table `time_logs`
--

CREATE TABLE `time_logs` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `source` varchar(255) DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `task_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Dumping data for table `time_logs`
--

INSERT INTO `time_logs` (`id`, `description`, `end_time`, `source`, `start_time`, `task_id`, `user_id`) VALUES
(1, 'Seeded work log for Ship support workflow #5', '2026-03-11 13:50:59.963320', 'TIMER', '2026-03-11 12:30:59.963320', 45, 2),
(2, 'Seeded work log for Implement authentication flow #7', '2026-03-29 10:59:59.963320', 'MANUAL', '2026-03-29 10:00:59.963320', 71, 3),
(3, 'Seeded work log for Ship support workflow #5', '2026-03-13 18:18:59.963320', 'MANUAL', '2026-03-13 15:00:59.963320', 85, 11),
(4, 'Seeded work log for Review report cards #2', '2026-03-23 13:49:59.963320', 'MANUAL', '2026-03-23 10:30:59.963320', 42, 2),
(5, 'Seeded work log for Test API filters #7', '2026-04-03 12:49:59.963320', 'TIMER', '2026-04-03 11:00:59.963320', 127, 15),
(6, 'Seeded work log for Document dashboard layout #2', '2026-04-04 16:12:59.963320', 'MANUAL', '2026-04-04 15:30:59.963320', 106, 14),
(7, 'Seeded work log for Migrate role permissions #7', '2026-03-18 11:28:59.963320', 'MANUAL', '2026-03-18 09:00:59.963320', 39, 8),
(8, 'Seeded work log for Audit release checklist #4', '2026-03-30 15:43:59.963320', 'MANUAL', '2026-03-30 13:00:59.963320', 100, 16),
(9, 'Seeded work log for Audit release checklist #2', '2026-04-04 16:08:59.963320', 'TIMER', '2026-04-04 15:30:59.963320', 50, 8),
(10, 'Seeded work log for Monitor timer lifecycle #2', '2026-03-09 15:04:59.963320', 'MANUAL', '2026-03-09 13:00:59.963320', 138, 6),
(11, 'Seeded work log for Implement authentication flow #7', '2026-03-14 15:28:59.963320', 'MANUAL', '2026-03-14 12:30:59.963320', 71, 3),
(12, 'Seeded work log for Document dashboard layout #2', '2026-03-28 13:23:59.963320', 'MANUAL', '2026-03-28 11:30:59.963320', 66, 13),
(13, 'Seeded work log for Document dashboard layout #4', '2026-03-26 09:23:59.963320', 'TIMER', '2026-03-26 08:30:59.963320', 76, 12),
(14, 'Seeded work log for Refine task automation #2', '2026-03-23 18:34:59.963320', 'MANUAL', '2026-03-23 15:30:59.963320', 114, 14),
(15, 'Seeded work log for Document dashboard layout #6', '2026-04-03 17:27:59.963320', 'MANUAL', '2026-04-03 15:30:59.963320', 6, 1),
(16, 'Seeded work log for Audit release checklist #4', '2026-03-08 09:53:59.963320', 'MANUAL', '2026-03-08 08:30:59.963320', 60, 8),
(17, 'Seeded work log for Test API filters #3', '2026-03-21 12:40:59.963320', 'TIMER', '2026-03-21 12:00:59.963320', 147, 18),
(18, 'Seeded work log for Review report cards #2', '2026-03-26 16:00:59.963320', 'MANUAL', '2026-03-26 15:00:59.963320', 42, 2),
(19, 'Seeded work log for Refine task automation #2', '2026-03-26 14:15:59.963320', 'MANUAL', '2026-03-26 11:00:59.963320', 34, 2),
(20, 'Seeded work log for Document dashboard layout #8', '2026-03-20 16:15:59.963320', 'MANUAL', '2026-03-20 15:30:59.963320', 16, 4),
(21, 'Seeded work log for Migrate role permissions #7', '2026-03-26 15:16:59.963320', 'TIMER', '2026-03-26 14:30:59.963320', 79, 3),
(22, 'Seeded work log for Monitor timer lifecycle #4', '2026-03-22 16:39:59.963320', 'MANUAL', '2026-03-22 14:30:59.963320', 108, 14),
(23, 'Seeded work log for Ship support workflow #7', '2026-03-11 16:31:59.963320', 'MANUAL', '2026-03-11 14:30:59.963320', 15, 2),
(24, 'Seeded work log for Audit release checklist #8', '2026-03-13 09:03:59.963320', 'MANUAL', '2026-03-13 08:30:59.963320', 160, 3),
(25, 'Seeded work log for Monitor timer lifecycle #4', '2026-03-16 16:57:59.963320', 'TIMER', '2026-03-16 14:30:59.963320', 28, 5),
(26, 'Seeded work log for Test API filters #3', '2026-03-12 13:45:59.963320', 'MANUAL', '2026-03-12 11:30:59.963320', 147, 18),
(27, 'Seeded work log for Implement authentication flow #1', '2026-03-17 15:02:59.963320', 'MANUAL', '2026-03-17 12:30:59.963320', 1, 1),
(28, 'Seeded work log for Monitor timer lifecycle #6', '2026-03-12 13:41:59.963320', 'MANUAL', '2026-03-12 12:30:59.963320', 118, 1),
(29, 'Seeded work log for Monitor timer lifecycle #8', '2026-03-14 12:06:59.963320', 'TIMER', '2026-03-14 10:30:59.963320', 8, 5),
(30, 'Seeded work log for Audit release checklist #2', '2026-04-03 14:16:59.963320', 'MANUAL', '2026-04-03 13:00:59.963320', 50, 8),
(31, 'Seeded work log for Document dashboard layout #6', '2026-04-02 10:44:59.963320', 'MANUAL', '2026-04-02 08:00:59.963320', 86, 12),
(32, 'Seeded work log for Ship support workflow #7', '2026-04-01 11:45:59.963320', 'MANUAL', '2026-04-01 09:30:59.963320', 135, 6),
(33, 'Seeded work log for Monitor timer lifecycle #4', '2026-03-26 11:19:59.963320', 'TIMER', '2026-03-26 10:00:59.963320', 108, 14),
(34, 'Seeded work log for Migrate role permissions #7', '2026-03-18 10:06:59.963320', 'MANUAL', '2026-03-18 09:30:59.963320', 159, 6),
(35, 'Seeded work log for Migrate role permissions #1', '2026-03-14 13:37:59.963320', 'MANUAL', '2026-03-14 11:30:59.963320', 129, 18),
(36, 'Seeded work log for Test API filters #1', '2026-03-18 15:31:59.963320', 'MANUAL', '2026-03-18 12:30:59.963320', 17, 2),
(37, 'Seeded work log for Audit release checklist #6', '2026-03-17 15:36:59.963320', 'TIMER', '2026-03-17 14:30:59.963320', 70, 13),
(38, 'Seeded work log for Migrate role permissions #1', '2026-04-02 13:09:59.963320', 'MANUAL', '2026-04-02 12:00:59.963320', 9, 6),
(39, 'Seeded work log for Ship support workflow #1', '2026-03-10 13:36:59.963320', 'MANUAL', '2026-03-10 10:30:59.963320', 145, 5),
(40, 'Seeded work log for Audit release checklist #2', '2026-03-17 16:07:59.963320', 'MANUAL', '2026-03-17 13:30:59.963320', 130, 6),
(41, 'Seeded work log for Refine task automation #4', '2026-03-14 14:22:59.963320', 'TIMER', '2026-03-14 12:30:59.963320', 84, 11),
(42, 'Seeded work log for Ship support workflow #5', '2026-03-26 11:26:59.963320', 'MANUAL', '2026-03-26 10:00:59.963320', 45, 2),
(43, 'Seeded work log for Document dashboard layout #8', '2026-03-10 17:51:59.963320', 'MANUAL', '2026-03-10 15:00:59.963320', 96, 12),
(44, 'Seeded work log for Test API filters #7', '2026-03-18 15:05:59.963320', 'MANUAL', '2026-03-18 12:30:59.963320', 87, 13),
(45, 'Seeded work log for Design team onboarding #3', '2026-04-04 18:25:59.963320', 'TIMER', '2026-04-04 15:30:59.963320', 43, 8),
(46, 'Seeded work log for Monitor timer lifecycle #8', '2026-03-19 14:35:59.963320', 'MANUAL', '2026-03-19 13:30:59.963320', 128, 16),
(47, 'Seeded work log for Design team onboarding #1', '2026-03-17 15:29:59.963320', 'MANUAL', '2026-03-17 12:30:59.963320', 153, 3),
(48, 'Seeded work log for Test API filters #5', '2026-03-18 15:12:59.963320', 'MANUAL', '2026-03-18 12:30:59.963320', 157, 2),
(49, 'Seeded work log for Refine task automation #6', '2026-03-26 10:53:59.963320', 'TIMER', '2026-03-26 09:00:59.963320', 54, 3),
(50, 'Seeded work log for Refine task automation #2', '2026-03-27 13:30:59.963320', 'MANUAL', '2026-03-27 11:30:59.963320', 74, 13),
(51, 'Seeded work log for Implement authentication flow #1', '2026-04-01 14:48:59.963320', 'MANUAL', '2026-04-01 12:30:59.963320', 1, 1),
(52, 'Seeded work log for Ship support workflow #7', '2026-03-11 10:43:59.963320', 'MANUAL', '2026-03-11 09:00:59.963320', 55, 2),
(53, 'Seeded work log for Audit release checklist #2', '2026-03-23 13:45:59.963320', 'TIMER', '2026-03-23 12:00:59.963320', 10, 5),
(54, 'Seeded work log for Document dashboard layout #8', '2026-03-08 16:24:59.963320', 'MANUAL', '2026-03-08 15:00:59.963320', 16, 4),
(55, 'Seeded work log for Ship support workflow #7', '2026-03-22 14:58:59.963320', 'MANUAL', '2026-03-22 13:00:59.963320', 135, 6),
(56, 'Seeded work log for Audit release checklist #4', '2026-03-28 12:14:59.963320', 'MANUAL', '2026-03-28 11:00:59.963320', 60, 8),
(57, 'Seeded work log for Ship support workflow #7', '2026-03-25 13:35:59.963320', 'TIMER', '2026-03-25 12:00:59.963320', 135, 6),
(58, 'Seeded work log for Migrate role permissions #3', '2026-03-31 13:21:59.963320', 'MANUAL', '2026-03-31 10:30:59.963320', 139, 6),
(59, 'Seeded work log for Implement authentication flow #7', '2026-03-13 13:08:59.963320', 'MANUAL', '2026-03-13 11:00:59.963320', 71, 3),
(60, 'Seeded work log for Monitor timer lifecycle #4', '2026-03-23 14:28:59.963320', 'MANUAL', '2026-03-23 11:00:59.963320', 28, 5),
(61, 'Seeded work log for Review report cards #2', '2026-03-12 11:09:59.963320', 'TIMER', '2026-03-12 09:00:59.963320', 42, 2),
(62, 'Seeded work log for Monitor timer lifecycle #2', '2026-03-16 12:39:59.963320', 'MANUAL', '2026-03-16 11:30:59.963320', 58, 9),
(63, 'Seeded work log for Ship support workflow #1', '2026-03-30 11:36:59.963320', 'MANUAL', '2026-03-30 10:30:59.963320', 25, 5),
(64, 'Seeded work log for Ship support workflow #7', '2026-03-19 12:45:59.963320', 'MANUAL', '2026-03-19 11:30:59.963320', 95, 13),
(65, 'Seeded work log for Monitor timer lifecycle #6', '2026-03-13 15:38:59.963320', 'TIMER', '2026-03-13 15:00:59.963320', 78, 3),
(66, 'Seeded work log for Monitor timer lifecycle #8', '2026-03-09 12:36:59.963320', 'MANUAL', '2026-03-09 11:30:59.963320', 48, 2),
(67, 'Seeded work log for Implement authentication flow #7', '2026-03-12 14:34:59.963320', 'MANUAL', '2026-03-12 13:00:59.963320', 151, 2),
(68, 'Seeded work log for Review report cards #2', '2026-03-25 13:16:59.963320', 'MANUAL', '2026-03-25 10:30:59.963320', 42, 2),
(69, 'Seeded work log for Refine task automation #2', '2026-03-23 09:23:59.963320', 'TIMER', '2026-03-23 08:30:59.963320', 34, 2),
(70, 'Seeded work log for Implement authentication flow #3', '2026-03-09 10:14:59.963320', 'MANUAL', '2026-03-09 08:00:59.963320', 11, 4),
(71, 'Seeded work log for Audit release checklist #4', '2026-03-14 13:34:59.963320', 'MANUAL', '2026-03-14 10:30:59.963320', 140, 18),
(72, 'Seeded work log for Monitor timer lifecycle #6', '2026-03-10 17:48:59.963320', 'MANUAL', '2026-03-10 15:30:59.963320', 158, 3),
(73, 'Seeded work log for Migrate role permissions #5', '2026-03-25 14:51:59.963320', 'TIMER', '2026-03-25 11:30:59.963320', 69, 3),
(74, 'Seeded work log for Implement authentication flow #3', '2026-03-16 15:18:59.963320', 'MANUAL', '2026-03-16 12:30:59.963320', 131, 6),
(75, 'Seeded work log for Ship support workflow #1', '2026-03-21 17:40:59.963320', 'MANUAL', '2026-03-21 15:00:59.963320', 25, 5),
(76, 'Seeded work log for Document dashboard layout #6', '2026-03-09 14:13:59.963320', 'MANUAL', '2026-03-09 11:30:59.963320', 126, 15),
(77, 'Seeded work log for Implement authentication flow #5', '2026-03-26 17:14:59.963320', 'TIMER', '2026-03-26 15:30:59.963320', 61, 7),
(78, 'Seeded work log for Test API filters #3', '2026-03-21 14:08:59.963320', 'MANUAL', '2026-03-21 12:30:59.963320', 27, 1),
(79, 'Seeded work log for Migrate role permissions #1', '2026-03-27 10:46:59.963320', 'MANUAL', '2026-03-27 08:00:59.963320', 129, 18),
(80, 'Seeded work log for Design team onboarding #5', '2026-04-02 11:31:59.963320', 'MANUAL', '2026-04-02 08:30:59.963320', 13, 2),
(81, 'Seeded work log for Migrate role permissions #5', '2026-03-30 10:28:59.963320', 'TIMER', '2026-03-30 09:30:59.963320', 149, 6),
(82, 'Seeded work log for Refine task automation #6', '2026-03-31 11:27:59.963320', 'MANUAL', '2026-03-31 09:00:59.963320', 54, 3),
(83, 'Seeded work log for Ship support workflow #5', '2026-03-21 14:59:59.963320', 'MANUAL', '2026-03-21 12:00:59.963320', 85, 11),
(84, 'Seeded work log for Audit release checklist #2', '2026-03-13 14:23:59.963320', 'MANUAL', '2026-03-13 13:30:59.963320', 50, 8),
(85, 'Seeded work log for Test API filters #5', '2026-03-13 11:11:59.963320', 'TIMER', '2026-03-13 09:30:59.963320', 117, 14),
(86, 'Seeded work log for Ship support workflow #1', '2026-03-11 12:16:59.963320', 'MANUAL', '2026-03-11 09:00:59.963320', 105, 16),
(87, 'Seeded work log for Monitor timer lifecycle #2', '2026-03-30 15:23:59.963320', 'MANUAL', '2026-03-30 13:30:59.963320', 58, 9),
(88, 'Seeded work log for Ship support workflow #1', '2026-04-02 10:32:59.963320', 'MANUAL', '2026-04-02 10:00:59.963320', 25, 5),
(89, 'Seeded work log for Test API filters #3', '2026-03-28 15:53:59.963320', 'TIMER', '2026-03-28 14:30:59.963320', 27, 1),
(90, 'Seeded work log for Document dashboard layout #8', '2026-03-28 12:38:59.963320', 'MANUAL', '2026-03-28 09:30:59.963320', 136, 18),
(91, 'Seeded work log for Test API filters #7', '2026-04-03 08:59:59.963320', 'MANUAL', '2026-04-03 08:00:59.963320', 87, 13),
(92, 'Seeded work log for Review report cards #6', '2026-03-22 13:13:59.963320', 'MANUAL', '2026-03-22 12:30:59.963320', 22, 5),
(93, 'Seeded work log for Audit release checklist #8', '2026-03-27 16:52:59.963320', 'TIMER', '2026-03-27 15:00:59.963320', 120, 17),
(94, 'Seeded work log for Monitor timer lifecycle #8', '2026-04-03 13:41:59.963320', 'MANUAL', '2026-04-03 10:30:59.963320', 128, 16),
(95, 'Seeded work log for Review report cards #4', '2026-03-21 11:52:59.963320', 'MANUAL', '2026-03-21 11:00:59.963320', 92, 10),
(96, 'Seeded work log for Refine task automation #8', '2026-03-09 12:59:59.963320', 'MANUAL', '2026-03-09 11:30:59.963320', 104, 17),
(97, 'Seeded work log for Test API filters #7', '2026-03-12 16:28:59.963320', 'TIMER', '2026-03-12 15:30:59.963320', 7, 1),
(98, 'Seeded work log for Monitor timer lifecycle #8', '2026-03-10 16:48:59.963320', 'MANUAL', '2026-03-10 13:30:59.963320', 128, 16),
(99, 'Seeded work log for Design team onboarding #5', '2026-03-11 12:26:59.963320', 'MANUAL', '2026-03-11 10:00:59.963320', 13, 2),
(100, 'Seeded work log for Monitor timer lifecycle #8', '2026-03-11 15:01:59.963320', 'MANUAL', '2026-03-11 13:30:59.963320', 88, 13),
(101, 'Seeded work log for Audit release checklist #8', '2026-04-02 14:29:59.963320', 'TIMER', '2026-04-02 12:30:59.963320', 40, 9),
(102, 'Seeded work log for Monitor timer lifecycle #4', '2026-03-17 12:07:59.963320', 'MANUAL', '2026-03-17 11:30:59.963320', 148, 18),
(103, 'Seeded work log for Document dashboard layout #4', '2026-04-03 13:53:59.963320', 'MANUAL', '2026-04-03 13:00:59.963320', 76, 12),
(104, 'Seeded work log for Design team onboarding #5', '2026-03-23 17:03:59.963320', 'MANUAL', '2026-03-23 14:30:59.963320', 93, 12),
(105, 'Seeded work log for Migrate role permissions #1', '2026-03-18 18:19:59.963320', 'TIMER', '2026-03-18 15:30:59.963320', 89, 12),
(106, 'Seeded work log for Review report cards #8', '2026-04-04 16:31:59.963320', 'MANUAL', '2026-04-04 14:00:59.963320', 112, 17),
(107, 'Seeded work log for Migrate role permissions #5', '2026-03-12 17:12:59.963320', 'MANUAL', '2026-03-12 14:30:59.963320', 69, 3),
(108, 'Seeded work log for Ship support workflow #5', '2026-03-19 14:23:59.963320', 'MANUAL', '2026-03-19 13:00:59.963320', 125, 15),
(109, 'Seeded work log for Document dashboard layout #4', '2026-03-12 15:57:59.963320', 'TIMER', '2026-03-12 13:00:59.963320', 116, 1),
(110, 'Seeded work log for Migrate role permissions #5', '2026-03-14 12:36:59.963320', 'MANUAL', '2026-03-14 11:30:59.963320', 69, 3),
(111, 'Seeded work log for Audit release checklist #4', '2026-03-09 14:45:59.963320', 'MANUAL', '2026-03-09 13:30:59.963320', 20, 6),
(112, 'Seeded work log for Ship support workflow #1', '2026-03-16 11:11:59.963320', 'MANUAL', '2026-03-16 08:00:59.963320', 145, 5),
(113, 'Seeded work log for Document dashboard layout #4', '2026-04-01 11:19:59.963320', 'TIMER', '2026-04-01 09:00:59.963320', 36, 7),
(114, 'Seeded work log for Migrate role permissions #3', '2026-03-11 15:20:59.963320', 'MANUAL', '2026-03-11 12:30:59.963320', 19, 5),
(115, 'Seeded work log for Ship support workflow #1', '2026-04-01 15:18:59.963320', 'MANUAL', '2026-04-01 12:00:59.963320', 145, 5),
(116, 'Seeded work log for Migrate role permissions #5', '2026-03-31 14:08:59.963320', 'MANUAL', '2026-03-31 13:30:59.963320', 69, 3),
(117, 'Seeded work log for Monitor timer lifecycle #2', '2026-03-25 14:10:59.963320', 'TIMER', '2026-03-25 12:00:59.963320', 98, 14),
(118, 'Seeded work log for Design team onboarding #3', '2026-03-27 13:58:59.963320', 'MANUAL', '2026-03-27 10:30:59.963320', 123, 16),
(119, 'Seeded work log for Test API filters #5', '2026-03-25 12:16:59.963320', 'MANUAL', '2026-03-25 11:30:59.963320', 157, 2),
(120, 'Seeded work log for Review report cards #2', '2026-03-18 13:19:59.963320', 'MANUAL', '2026-03-18 11:30:59.963320', 122, 16),
(121, 'Seeded work log for Ship support workflow #5', '2026-04-01 09:33:59.963320', 'TIMER', '2026-04-01 08:00:59.963320', 45, 2),
(122, 'Seeded work log for Implement authentication flow #7', '2026-03-15 11:59:59.963320', 'MANUAL', '2026-03-15 09:30:59.963320', 111, 14),
(123, 'Seeded work log for Ship support workflow #5', '2026-03-18 11:55:59.963320', 'MANUAL', '2026-03-18 08:30:59.963320', 5, 1),
(124, 'Seeded work log for Monitor timer lifecycle #2', '2026-03-14 16:07:59.963320', 'MANUAL', '2026-03-14 13:30:59.963320', 138, 6),
(125, 'Seeded work log for Document dashboard layout #6', '2026-03-19 13:56:59.963320', 'TIMER', '2026-03-19 12:00:59.963320', 6, 1),
(126, 'Seeded work log for Review report cards #8', '2026-03-28 10:50:59.963320', 'MANUAL', '2026-03-28 08:00:59.963320', 72, 10),
(127, 'Seeded work log for Migrate role permissions #3', '2026-03-21 16:52:59.963320', 'MANUAL', '2026-03-21 14:00:59.963320', 19, 5),
(128, 'Seeded work log for Ship support workflow #7', '2026-03-12 14:00:59.963320', 'MANUAL', '2026-03-12 12:00:59.963320', 135, 6),
(129, 'Seeded work log for Document dashboard layout #2', '2026-03-08 14:16:59.963320', 'TIMER', '2026-03-08 12:00:59.963320', 106, 14),
(130, 'Seeded work log for Implement authentication flow #3', '2026-03-12 17:36:59.963320', 'MANUAL', '2026-03-12 14:30:59.963320', 51, 8),
(131, 'Seeded work log for Implement authentication flow #5', '2026-03-26 15:14:59.963320', 'MANUAL', '2026-03-26 12:30:59.963320', 21, 1),
(132, 'Seeded work log for Document dashboard layout #8', '2026-03-29 10:16:59.963320', 'MANUAL', '2026-03-29 09:00:59.963320', 96, 12),
(133, 'Seeded work log for Monitor timer lifecycle #2', '2026-03-26 15:53:59.963320', 'TIMER', '2026-03-26 12:30:59.963320', 138, 6),
(134, 'Seeded work log for Review report cards #8', '2026-03-28 10:28:59.963320', 'MANUAL', '2026-03-28 09:30:59.963320', 72, 10),
(135, 'Seeded work log for Monitor timer lifecycle #6', '2026-03-31 16:49:59.963320', 'MANUAL', '2026-03-31 15:30:59.963320', 38, 8),
(136, 'Seeded work log for Migrate role permissions #1', '2026-03-22 10:31:59.963320', 'MANUAL', '2026-03-22 09:00:59.963320', 129, 18),
(137, 'Seeded work log for Implement authentication flow #5', '2026-03-11 15:35:59.963320', 'TIMER', '2026-03-11 13:30:59.963320', 101, 1),
(138, 'Seeded work log for Migrate role permissions #1', '2026-03-23 17:04:59.963320', 'MANUAL', '2026-03-23 15:30:59.963320', 89, 12),
(139, 'Seeded work log for Migrate role permissions #1', '2026-03-23 14:08:59.963320', 'MANUAL', '2026-03-23 11:00:59.963320', 89, 12),
(140, 'Seeded work log for Document dashboard layout #6', '2026-03-24 12:04:59.963320', 'MANUAL', '2026-03-24 11:00:59.963320', 86, 12),
(141, 'Seeded work log for Refine task automation #6', '2026-04-01 12:24:59.963320', 'TIMER', '2026-04-01 10:30:59.963320', 134, 6),
(142, 'Seeded work log for Implement authentication flow #3', '2026-03-09 10:37:59.963320', 'MANUAL', '2026-03-09 09:00:59.963320', 131, 6),
(143, 'Seeded work log for Implement authentication flow #5', '2026-03-10 17:16:59.963320', 'MANUAL', '2026-03-10 14:00:59.963320', 101, 1),
(144, 'Seeded work log for Test API filters #3', '2026-03-10 10:36:59.963320', 'MANUAL', '2026-03-10 08:00:59.963320', 107, 17),
(145, 'Seeded work log for Refine task automation #8', '2026-03-20 11:12:59.963320', 'TIMER', '2026-03-20 10:00:59.963320', 24, 5),
(146, 'Seeded work log for Refine task automation #4', '2026-04-04 11:56:59.963320', 'MANUAL', '2026-04-04 11:00:59.963320', 4, 6),
(147, 'Seeded work log for Design team onboarding #3', '2026-03-23 11:43:59.963320', 'MANUAL', '2026-03-23 10:30:59.963320', 43, 8),
(148, 'Seeded work log for Design team onboarding #3', '2026-03-23 11:28:59.963320', 'MANUAL', '2026-03-23 08:00:59.963320', 83, 12),
(149, 'Seeded work log for Monitor timer lifecycle #6', '2026-03-11 17:57:59.963320', 'TIMER', '2026-03-11 15:30:59.963320', 38, 8),
(150, 'Seeded work log for Review report cards #2', '2026-03-16 13:53:59.963320', 'MANUAL', '2026-03-16 11:30:59.963320', 42, 2),
(151, 'Seeded work log for Document dashboard layout #2', '2026-03-23 11:01:59.963320', 'MANUAL', '2026-03-23 09:00:59.963320', 66, 13),
(152, 'Seeded work log for Implement authentication flow #3', '2026-03-25 13:29:59.963320', 'MANUAL', '2026-03-25 10:30:59.963320', 131, 6),
(153, 'Seeded work log for Refine task automation #8', '2026-04-01 12:05:59.963320', 'TIMER', '2026-04-01 11:30:59.963320', 144, 18),
(154, 'Seeded work log for Implement authentication flow #3', '2026-03-10 16:18:59.963320', 'MANUAL', '2026-03-10 15:30:59.963320', 131, 6),
(155, 'Seeded work log for Implement authentication flow #5', '2026-03-15 15:28:59.963320', 'MANUAL', '2026-03-15 12:00:59.963320', 101, 1),
(156, 'Seeded work log for Migrate role permissions #1', '2026-03-22 12:26:59.963320', 'MANUAL', '2026-03-22 09:00:59.963320', 89, 12),
(157, 'Seeded work log for Migrate role permissions #1', '2026-03-23 11:10:59.963320', 'TIMER', '2026-03-23 09:00:59.963320', 9, 6),
(158, 'Seeded work log for Migrate role permissions #5', '2026-03-10 16:00:59.963320', 'MANUAL', '2026-03-10 15:30:59.963320', 149, 6),
(159, 'Seeded work log for Implement authentication flow #3', '2026-04-03 16:03:59.963320', 'MANUAL', '2026-04-03 13:00:59.963320', 51, 8),
(160, 'Seeded work log for Design team onboarding #7', '2026-03-17 13:38:59.963320', 'MANUAL', '2026-03-17 10:30:59.963320', 63, 7),
(161, 'Seeded work log for Monitor timer lifecycle #4', '2026-03-31 13:53:59.963320', 'TIMER', '2026-03-31 12:00:59.963320', 108, 14),
(162, 'Seeded work log for Monitor timer lifecycle #4', '2026-04-01 12:42:59.963320', 'MANUAL', '2026-04-01 12:00:59.963320', 108, 14),
(163, 'Seeded work log for Audit release checklist #4', '2026-04-03 10:50:59.963320', 'MANUAL', '2026-04-03 10:00:59.963320', 100, 16),
(164, 'Seeded work log for Review report cards #6', '2026-03-22 15:35:59.963320', 'MANUAL', '2026-03-22 13:30:59.963320', 102, 1),
(165, 'Seeded work log for Implement authentication flow #5', '2026-03-31 10:24:59.963320', 'TIMER', '2026-03-31 09:30:59.963320', 61, 7),
(166, 'Seeded work log for Ship support workflow #3', '2026-03-31 11:38:59.963320', 'MANUAL', '2026-03-31 11:00:59.963320', 75, 12),
(167, 'Seeded work log for Implement authentication flow #5', '2026-03-08 17:42:59.963320', 'MANUAL', '2026-03-08 15:30:59.963320', 141, 18),
(168, 'Seeded work log for Review report cards #8', '2026-03-26 14:42:59.963320', 'MANUAL', '2026-03-26 13:00:59.963320', 112, 17),
(169, 'Seeded work log for Test API filters #5', '2026-03-19 14:58:59.963320', 'TIMER', '2026-03-19 13:30:59.963320', 157, 2),
(170, 'Seeded work log for Test API filters #1', '2026-04-04 14:23:59.963320', 'MANUAL', '2026-04-04 12:30:59.963320', 97, 17),
(171, 'Seeded work log for Migrate role permissions #1', '2026-03-14 12:14:59.963320', 'MANUAL', '2026-03-14 11:00:59.963320', 9, 6),
(172, 'Seeded work log for Design team onboarding #5', '2026-03-15 11:26:59.963320', 'MANUAL', '2026-03-15 09:00:59.963320', 13, 2),
(173, 'Seeded work log for Migrate role permissions #5', '2026-03-08 17:06:59.963320', 'TIMER', '2026-03-08 14:00:59.963320', 69, 3),
(174, 'Seeded work log for Review report cards #2', '2026-03-26 13:07:59.963320', 'MANUAL', '2026-03-26 11:00:59.963320', 122, 16),
(175, 'Seeded work log for Audit release checklist #8', '2026-03-31 15:36:59.963320', 'MANUAL', '2026-03-31 12:30:59.963320', 40, 9),
(176, 'Seeded work log for Test API filters #1', '2026-04-02 10:06:59.963320', 'MANUAL', '2026-04-02 08:30:59.963320', 97, 17),
(177, 'Seeded work log for Migrate role permissions #5', '2026-03-25 16:31:59.963320', 'TIMER', '2026-03-25 15:30:59.963320', 69, 3),
(178, 'Seeded work log for Review report cards #6', '2026-03-26 11:45:59.963320', 'MANUAL', '2026-03-26 09:30:59.963320', 142, 6),
(179, 'Seeded work log for Design team onboarding #5', '2026-04-01 16:44:59.963320', 'MANUAL', '2026-04-01 13:30:59.963320', 53, 8),
(180, 'Seeded work log for Implement authentication flow #1', '2026-04-02 12:45:59.963320', 'MANUAL', '2026-04-02 10:00:59.963320', 1, 1),
(181, 'Working on Refine task automation #4', '2026-04-05 16:52:32.663088', 'TIMER', '2026-04-05 16:49:14.099059', 124, 1),
(182, 'Working on Design team onboarding #3', '2026-04-05 17:01:12.384332', 'TIMER', '2026-04-05 17:01:05.809942', 123, 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `role`, `username`) VALUES
(1, 'owner.alpha@example.com', '$2a$12$QNrpbCeLhNCJQHp03jNPMuWxqsIGwPXBQ.d4GEh7ari7yEFmIkUq2', 'USER', 'owner.alpha'),
(2, 'manager.beta@example.com', '$2a$12$p1ZURQgiDyrM2A.kdOvkhuQq82cmPXCdVzMzDZHwp2HTS6TOEQvt2', 'USER', 'manager.beta'),
(3, 'manager.gamma@example.com', '$2a$12$EKNgCFvNRVyE/SfAPllAd.jIYt4wdYH.o8fG1scPtB9n3HTTG27GO', 'USER', 'manager.gamma'),
(4, 'member.delta@example.com', '$2a$12$NkTucYMDqUf2ByUU1AmkBOOuAvvlnpEoiMCTh99VuTKxwQNOqp8xe', 'USER', 'member.delta'),
(5, 'member.echo@example.com', '$2a$12$vDjSLvqmEHdkSPbZdQg7TeVmjQYYWK0W3rMpNzedJycLW.zFCYvcW', 'USER', 'member.echo'),
(6, 'member.foxtrot@example.com', '$2a$12$otqg5Qk9lky3zx5U47niOexx33NiKWDIt5Mtq4wYhIq7l14WNvJAy', 'USER', 'member.foxtrot'),
(7, 'member.golf@example.com', '$2a$12$ezG1r08AW8DBCiLynlLzJeDDdCTaPUMQZkzVDS5UFPm7ga003nxVy', 'USER', 'member.golf'),
(8, 'member.hotel@example.com', '$2a$12$NjlstlKqEr4c6oFVktkat.pckvUegKdMMat/d9AkKWK8noAdBKAxq', 'USER', 'member.hotel'),
(9, 'member.india@example.com', '$2a$12$F8GEj1rGeWPWIwtXt4KN0OhOJJC6du1yYl/v8XTFEOKLbi6FBcOne', 'USER', 'member.india'),
(10, 'member.juliet@example.com', '$2a$12$5dXbFIqDE9hLQmQtyHL6/.xpXbAu.XqqZycK.RCWtfzDKwLvhanVi', 'USER', 'member.juliet'),
(11, 'member.kilo@example.com', '$2a$12$GqxOjflkBGigUqZSWbukG.0tFxOTD//NcHLlRZuCqcphq/axsTAhG', 'USER', 'member.kilo'),
(12, 'member.lima@example.com', '$2a$12$2I.7WCIGMzj/TO0D1kgnte5nhd6e8LGUD0i9hTsdIhsz54GqVwbta', 'USER', 'member.lima'),
(13, 'member.mike@example.com', '$2a$12$tN4uQN5/iUHO7sR7KoxtP.W3njQtqqDRphKLYS12AXIlj.xz0re1C', 'USER', 'member.mike'),
(14, 'member.november@example.com', '$2a$12$d.UibdK05awiKqaNTyt8buUR38cLzk23QxgCy2OYLUQWL6RMJhAse', 'USER', 'member.november'),
(15, 'member.oscar@example.com', '$2a$12$MnW5IW160PJfxiElkpkwcu.UNtJ0v2gu6VsnURiF5zuB1hIMXIhsK', 'USER', 'member.oscar'),
(16, 'member.papa@example.com', '$2a$12$qDMsz7hto/lwY9kVDdIueOE/vkAh3w2/W/d0PMVi9BHKMlg.E4A4S', 'USER', 'member.papa'),
(17, 'member.quebec@example.com', '$2a$12$xXc6piipvIOyIt45ETJ8vuoE7OTuqbvZsKfUhA0w/aNufnDZyBuua', 'USER', 'member.quebec'),
(18, 'member.romeo@example.com', '$2a$12$qpXmRDzPhnb6ilY9ociT1e/V6Oc6eqxn0ydHgd8BhCfdHrmkaeFNC', 'USER', 'member.romeo');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `projects`
--
ALTER TABLE `projects`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmqih0928bq6r3gbuh47giq8w` (`team_id`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKekr1dgiqktpyoip3qmp6lxsit` (`assignee_id`),
  ADD KEY `FKsfhn82y57i3k9uxww1s007acc` (`project_id`);

--
-- Indexes for table `teams`
--
ALTER TABLE `teams`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `team_invites`
--
ALTER TABLE `team_invites`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKov0gp79k1o9vl06tdrcc8i43x` (`invited_by_user_id`),
  ADD KEY `FK75jy5ovu0dxaimd96mwvbrcx3` (`invited_user_id`),
  ADD KEY `FKkqvx6ai0f07yoc9p5a76qxdv` (`team_id`);

--
-- Indexes for table `team_members`
--
ALTER TABLE `team_members`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtgca08el3ofisywcf11f0f76t` (`team_id`),
  ADD KEY `FKee8x7x5026imwmma9kndkxs36` (`user_id`);

--
-- Indexes for table `timer_sessions`
--
ALTER TABLE `timer_sessions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcfs1ef59b0wb3hlqmmxx2icu7` (`task_id`),
  ADD KEY `FK36ydl8f110p7eodiejwirs9ak` (`user_id`);

--
-- Indexes for table `time_logs`
--
ALTER TABLE `time_logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKoun22vhbya8md711x7gbqv5j6` (`task_id`),
  ADD KEY `FKpa0td7bk535jt0143oslckvxj` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `projects`
--
ALTER TABLE `projects`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=161;

--
-- AUTO_INCREMENT for table `teams`
--
ALTER TABLE `teams`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `team_invites`
--
ALTER TABLE `team_invites`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `team_members`
--
ALTER TABLE `team_members`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `timer_sessions`
--
ALTER TABLE `timer_sessions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `time_logs`
--
ALTER TABLE `time_logs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=183;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `projects`
--
ALTER TABLE `projects`
  ADD CONSTRAINT `FKmqih0928bq6r3gbuh47giq8w` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`);

--
-- Constraints for table `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `FKekr1dgiqktpyoip3qmp6lxsit` FOREIGN KEY (`assignee_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKsfhn82y57i3k9uxww1s007acc` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`);

--
-- Constraints for table `team_invites`
--
ALTER TABLE `team_invites`
  ADD CONSTRAINT `FK75jy5ovu0dxaimd96mwvbrcx3` FOREIGN KEY (`invited_user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKkqvx6ai0f07yoc9p5a76qxdv` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`),
  ADD CONSTRAINT `FKov0gp79k1o9vl06tdrcc8i43x` FOREIGN KEY (`invited_by_user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `team_members`
--
ALTER TABLE `team_members`
  ADD CONSTRAINT `FKee8x7x5026imwmma9kndkxs36` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKtgca08el3ofisywcf11f0f76t` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`);

--
-- Constraints for table `timer_sessions`
--
ALTER TABLE `timer_sessions`
  ADD CONSTRAINT `FK36ydl8f110p7eodiejwirs9ak` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKcfs1ef59b0wb3hlqmmxx2icu7` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`);

--
-- Constraints for table `time_logs`
--
ALTER TABLE `time_logs`
  ADD CONSTRAINT `FKoun22vhbya8md711x7gbqv5j6` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`),
  ADD CONSTRAINT `FKpa0td7bk535jt0143oslckvxj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
