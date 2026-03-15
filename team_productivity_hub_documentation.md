# Team Productivity Hub -- Complete Project Documentation

## 1. Project Overview

The **Team Productivity Hub** is a web-based application designed to
help small teams manage projects, tasks, and time tracking in one
centralized platform.\
The goal is to reduce tool fragmentation while providing visibility into
productivity and workload.

------------------------------------------------------------------------

## 2. Scope & Objectives

-   Provide secure user authentication using **JWT**
-   Enable **team-based project and task management**
-   Track **time spent on tasks**
-   Generate **reports and dashboards**
-   Follow **Web Development 2 technical rubrics strictly**

------------------------------------------------------------------------

## 3. Technology Stack

  Layer            Technology
  ---------------- ---------------------------------
  Frontend         Vue.js (Vue Router, Pinia/Vuex)
  Backend          Java (SpringBoot)
  Database         MySQL / MS SQL
  Styling          Bootstrap or Tailwind CSS
  Authentication   JWT

------------------------------------------------------------------------

## 4. Non-Functional Requirements

**NFR-UI-1**: Responsive UI using CSS framework\
**NFR-FE-1**: Component-based frontend with routing and state management

**NFR-API-1**: RESTful API supporting: - GET - POST - PUT - DELETE

**NFR-API-2**: Filtering & pagination for GET endpoints\
**NFR-API-3**: Proper error handling with HTTP status codes

**NFR-AUTH-1**: JWT-based authentication and role authorization

**NFR-BE-1**: Backend follows **MVC pattern** with namespaces and
autoloading

------------------------------------------------------------------------

## 5. Epics

1.  Authentication & Teams
2.  Projects & Tasks
3.  Time Tracking
4.  Reports
5.  Dashboards
6.  Infrastructure & QA

------------------------------------------------------------------------

## 6. User Stories (Refined)

  ID     User Story
  ------ ---------------------------------
  US1    User registration & login (JWT)
  US2    Create and manage teams
  US3    Invite team members
  US4    Assign roles
  US5    Create projects
  US6    Create and assign tasks
  US7    Update task status
  US8    View task lists with filters
  US9    Start/stop timer
  US10   Manual time logs
  US11   View personal time logs
  US12   Weekly summaries
  US13   Team total hours
  US14   Completed tasks report
  US15   Trends overview
  US16   Personal dashboard
  US17   Team dashboard
  US18   Task & time overview
  US19   Database schema
  US20   MVC backend structure
  US21   Testing & bug fixing

------------------------------------------------------------------------

## 7. Sprint Plan

### Sprint 1 -- Core Foundation (14 pts)

-   Authentication
-   Teams
-   Database schema
-   Backend MVC setup

### Sprint 2 -- Tasks & Time Tracking (15 pts)

-   Projects
-   Tasks
-   Task assignment
-   Timer & manual time logs

### Sprint 3 -- Reports & Dashboards (15 pts)

-   Reports
-   Analytics
-   Dashboards
-   Final testing

------------------------------------------------------------------------

## 8. AI Disclosure

AI tools were used for:

-   Planning
-   Requirement refinement
-   Documentation drafting

All generated code and structures were **reviewed, understood, and
manually implemented by the student**.

------------------------------------------------------------------------

## 9. Conclusion

This document defines a **rubric-aligned, MVP-focused project plan**
ensuring compliance with **Web Development 2 requirements** while
delivering a functional **Team Productivity Hub** application.
