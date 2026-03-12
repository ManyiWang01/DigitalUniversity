# 🎓 Project Specification: Digital University

## 1. Project Overview

The **Digital University** is a backend system designed to manage the lifecycle of a student’s academic journey and faculty teaching schedules. It focuses on robust relational modeling (Inheritance, Composite Keys), high-integrity data (no orphaned records), efficient fetching (no N+1 issues), and a discoverable API (HATEOAS).

---

## 2. Core Modules & Responsibilities

| **Module**                   | **Responsibility**                                                                   | **Key Entities**                                       |
| ---------------------------- | ------------------------------------------------------------------------------------ | ------------------------------------------------------ |
| **Identity & Profiles**      | Manages the base data and hierarchy for all users in the system.                     | `Person` (Base), `Student`, `Professor`                |
| **Academic Blueprint**       | Handles the creation of academic paths and their curriculum.                         | `Major`, `Course`                                      |
| **Enrollment & Assignments** | The "Logic Center" connecting people to courses with extra metadata (Grades, Roles). | `Enrollment`, `ProfessorCourse` (Association Entities) |
| **Analytical & Records**     | Manages system-wide statistics, GPA calculations, and history.                       | `Grade`, `Transcript`                                  |

---

## 3. Detailed Module Specifications

### A. Identity Module

- **Purpose:** To maintain a unique record for every person using a JPA Inheritance strategy.
- **Logic:** `Student` and `Professor` inherit from a base `Person` class. A `Student` must belong to one `Major`.
- **Modern Twist:** We will use **Soft Delete** here. If a student or professor leaves, we don't wipe their data; we mark their `status` as `INACTIVE` to preserve historical enrollment data.

### B. Academic Blueprint (Curriculum)

- **Purpose:** Defines the "Products" of the university. The `Major` acts as the structural foundation.
- **Logic:** A `Major` contains a Set of `Course`s (its curriculum) and a Set of `Student`s (its enrollees). A `Course` has a maximum capacity. Once reached, the "Enroll" link in our HATEOAS response should disappear.
- **Modern Twist:** Uses `@NamedEntityGraph` or `JOIN FETCH` to fetch the Major and its Curriculum efficiently when requested, preventing N+1 queries.

### C. Enrollment Engine (The "Business" Hub)

- **Purpose:** Handles complex many-to-many relationships by promoting them to full **Association Entities** with **Composite Primary Keys** (`@IdClass`).
- **Logic:** - `Enrollment`: Links `Student` and `Course`, storing the `EnrollmentDate` and `Grade`.
  - `ProfessorCourse`: Links `Professor` and `Course`, storing the teaching `Role` (Lead/Assistant).
  - When a Person is deleted, their association records must be deleted automatically (**Orphan Removal**), but the `Course` itself must remain untouched.
- **Modern Twist:** This module will use `EntityManager.getReference()` to efficiently link entities without unnecessary database hits, and will trigger **ProblemDetail** responses for constraints (e.g., "Student not in correct Major" or "Schedule Conflict").

---

## 4. Technical Requirements (Plain Spring)

Since we are avoiding Spring Boot for now, we will manually configure the following "Spring Core" components:

1. **`AnnotationConfigApplicationContext`**: Our engine to start the app.
2. **`DataSource` & `LocalContainerEntityManagerFactoryBean`**: To manually set up Hibernate/JPA without `application.properties`.
3. **`PlatformTransactionManager`**: To handle `@Transactional` boundaries within our Service Layer.
4. **`DispatcherServlet` Configuration**: To handle web requests via a `WebApplicationInitializer`.

---

## 5. The "User Stories" for our Project

1. **Efficient Fetching:** "As an Admin, I want to see a Student and all their enrolled Courses (with Grades) in **one** database hit so the system stays fast during grading week."
2. **Standardized Errors:** "As a Frontend Developer, when an enrollment fails, I want a **ProblemDetail** JSON so I know exactly why (e.g., 'Course Capacity Reached' or 'Missing Prerequisite')."
3. **Self-Discovery:** "As a Client, when I view a Course, I want a **Link** to the enrollment endpoint so I don't have to guess the URL."
4. **Data Integrity:** "As a Registrar, I want to ensure a professor cannot be assigned to the same course twice, guaranteed by a Composite Primary Key."

---

## 6. Project Roadmap (Plain Spring Style)

- **Step 1:** Setup the Maven `pom.xml` with `spring-context`, `spring-orm`, and `hibernate-core`.
- **Step 2:** Create the `JavaConfig.java` class with `@Configuration` and `@Bean` definitions for the `EntityManager`.
- **Step 3:** Implement the Domain Layer (`Person`, `Major`, `Course`) including the Association Entities (`Enrollment`, `ProfessorCourse`) and Composite Keys.
- **Step 4:** Build the Service Layer (Academic, People, Enrollment, Analytics) using the `EntityManager` and `@Transactional`.
- **Step 5:** Setup the Web Layer, **Assemblers** for HATEOAS, and the **GlobalExceptionHandler**.

# Dependencies

| **Group ID** | **Artifact ID** | **Purpose** |
| ----------------------------- | --------------------- | ---------------------------------- |
| `org.springframework`         | `spring-context`      | Core Container & Annotations       |
| `org.springframework`         | `spring-orm`          | JPA & Transaction Management       |
| `org.springframework`         | `spring-webmvc`       | Web Layer & DispatcherServlet      |
| `org.springframework.hateoas` | `spring-hateoas`      | HATEOAS Links & Assemblers         |
| `org.hibernate.orm`           | `hibernate-core`      | JPA Implementation (Hibernate 6.x) |
| `com.fasterxml.jackson.core`  | `jackson-databind`    | JSON Marshalling & ProblemDetail   |
| `jakarta.servlet`             | `jakarta.servlet-api` | Servlet 6.0 Specs (Compile only)   |
