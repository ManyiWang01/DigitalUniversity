package it.manyiw.digitaluniversity.config;

import it.manyiw.digitaluniversity.domain.Assignment;
import it.manyiw.digitaluniversity.domain.Course;
import it.manyiw.digitaluniversity.domain.Enrollment;
import it.manyiw.digitaluniversity.domain.Major;
import it.manyiw.digitaluniversity.domain.Professor;
import it.manyiw.digitaluniversity.domain.Student;
import it.manyiw.digitaluniversity.domain.enums.Role;
import it.manyiw.digitaluniversity.domain.enums.StudentStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Transactional
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Run only once for the root context
        if (event.getApplicationContext().getParent() != null) {
            return;
        }

        // Idempotency guard: if students already exist, assume seed already loaded
        Long studentCount = em.createQuery("select count(s) from Student s", Long.class).getSingleResult();
        if (studentCount != null && studentCount > 0) {
            return;
        }

        // -------------------------
        // Majors
        // -------------------------
        Major cs = new Major();
        cs.setName("Computer Science");
        em.persist(cs);

        Major math = new Major();
        math.setName("Mathematics");
        em.persist(math);

        Major economics = new Major();
        economics.setName("Economics");
        em.persist(economics);

        // This one will be soft-deleted to test /majors/past and /majors/all
        Major archivedMajor = new Major();
        archivedMajor.setName("Classical Studies");
        em.persist(archivedMajor);

        // -------------------------
        // Courses (open)
        // -------------------------
        Course algorithms = new Course();
        algorithms.setTitle("Algorithms");
        algorithms.setDescription("Algorithms and data structures");
        algorithms.setRegistrationLimit(120);
        algorithms.setMajor(cs);
        algorithms.setYear(1);
        em.persist(algorithms);

        Course databases = new Course();
        databases.setTitle("Databases");
        databases.setDescription("Relational databases and SQL");
        databases.setRegistrationLimit(90);
        databases.setMajor(cs);
        databases.setYear(2);
        em.persist(databases);

        Course linearAlgebra = new Course();
        linearAlgebra.setTitle("Linear Algebra");
        linearAlgebra.setDescription("Matrices, vectors and linear transformations");
        linearAlgebra.setRegistrationLimit(80);
        linearAlgebra.setMajor(math);
        linearAlgebra.setYear(1);
        em.persist(linearAlgebra);

        Course microeconomics = new Course();
        microeconomics.setTitle("Microeconomics");
        microeconomics.setDescription("Market behavior and pricing models");
        microeconomics.setRegistrationLimit(70);
        microeconomics.setMajor(economics);
        microeconomics.setYear(1);
        em.persist(microeconomics);

        // This one will be soft-deleted to test open/all course behavior
        Course archivedCourse = new Course();
        archivedCourse.setTitle("Ancient Philosophy");
        archivedCourse.setDescription("Historical overview of ancient philosophy");
        archivedCourse.setRegistrationLimit(30);
        archivedCourse.setMajor(cs);
        archivedCourse.setYear(3);
        em.persist(archivedCourse);

        // -------------------------
        // Students
        // -------------------------
        Student s1 = new Student();
        s1.setFirstName("John");
        s1.setLastName("Doe");
        s1.setPersonalEmail("john.doe.personal@example.com");
        s1.setInstitutionalMail("john.doe@mail.digitaluniversity.com");
        s1.setAge(20);
        s1.setMajor(cs);
        s1.setGrade(1);
        s1.setStatus(StudentStatus.ENROLLED);
        em.persist(s1);

        Student s2 = new Student();
        s2.setFirstName("Jane");
        s2.setLastName("Rossi");
        s2.setPersonalEmail("jane.rossi.personal@example.com");
        s2.setInstitutionalMail("jane.rossi@mail.digitaluniversity.com");
        s2.setAge(23);
        s2.setMajor(math);
        s2.setGrade(3);
        s2.setStatus(StudentStatus.GRADUATED);
        em.persist(s2);

        Student s3 = new Student();
        s3.setFirstName("Mark");
        s3.setLastName("Bianchi");
        s3.setPersonalEmail("mark.bianchi.personal@example.com");
        s3.setInstitutionalMail("mark.bianchi@mail.digitaluniversity.com");
        s3.setAge(21);
        s3.setMajor(cs);
        s3.setGrade(2);
        s3.setStatus(StudentStatus.SUSPENDED);
        em.persist(s3);

        // -------------------------
        // Professors
        // -------------------------
        Professor p1 = new Professor();
        p1.setFirstName("Alan");
        p1.setLastName("Turing");
        p1.setPersonalEmail("alan.turing.personal@example.com");
        p1.setInstitutionalMail("alan.turing@digitaluniversity.com");
        p1.setAge(45);
        p1.setMajor(cs);
        em.persist(p1);

        Professor p2 = new Professor();
        p2.setFirstName("Emmy");
        p2.setLastName("Noether");
        p2.setPersonalEmail("emmy.noether.personal@example.com");
        p2.setInstitutionalMail("emmy.noether@digitaluniversity.com");
        p2.setAge(42);
        p2.setMajor(math);
        em.persist(p2);

        // -------------------------
        // Enrollments
        // -------------------------
        Enrollment e1 = new Enrollment();
        e1.setStudent(s1);
        e1.setCourse(algorithms);
        e1.setVote(new BigDecimal("28.00"));
        e1.setEnrollmentDate(LocalDate.now().minusDays(30));
        em.persist(e1);

        Enrollment e2 = new Enrollment();
        e2.setStudent(s1);
        e2.setCourse(linearAlgebra);
        e2.setVote(BigDecimal.ZERO); // enrolled but no final vote yet
        e2.setEnrollmentDate(LocalDate.now().minusDays(10));
        em.persist(e2);

        Enrollment e3 = new Enrollment();
        e3.setStudent(s2);
        e3.setCourse(databases);
        e3.setVote(new BigDecimal("30.00"));
        e3.setEnrollmentDate(LocalDate.now().minusDays(120));
        em.persist(e3);

        // -------------------------
        // Assignments
        // -------------------------
        Assignment a1 = new Assignment();
        a1.setProfessor(p1);
        a1.setCourse(algorithms);
        a1.setRole(Role.PROFESSOR);
        em.persist(a1);

        Assignment a2 = new Assignment();
        a2.setProfessor(p1);
        a2.setCourse(databases);
        a2.setRole(Role.ASSISTANT);
        em.persist(a2);

        Assignment a3 = new Assignment();
        a3.setProfessor(p2);
        a3.setCourse(linearAlgebra);
        a3.setRole(Role.PROFESSOR);
        em.persist(a3);

        // Force insert before soft-delete operations
        em.flush();

        // Soft-delete one major and one course for "past/all" testing
        em.remove(archivedMajor);
        em.remove(archivedCourse);
    }
}
