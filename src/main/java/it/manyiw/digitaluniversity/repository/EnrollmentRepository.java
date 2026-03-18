package it.manyiw.digitaluniversity.repository;

import it.manyiw.digitaluniversity.domain.Enrollment;
import it.manyiw.digitaluniversity.domain.compositekey.EnrollmentId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnrollmentRepository {
    // TODO findStudents, findCourses, ...
    @PersistenceContext
    private EntityManager em;

    public EnrollmentRepository() {}

    public void save(Enrollment enrollment) {
        enrollment.setEnrollmentDate(java.time.LocalDate.now());
        em.persist(enrollment);
    }

    public Enrollment find(Integer studentId, Integer courseId) {
        return em.find(Enrollment.class, new EnrollmentId(studentId, courseId));
    }

    public void remove(Enrollment enrollment) {
        em.remove(enrollment);
    }

    public void remove(Integer studentId, Integer courseId) {
        em.remove(em.find(Enrollment.class, new EnrollmentId(studentId, courseId)));
    }

    public List<Enrollment> findAllEnrollmentsByStudentId(Integer studentId) {
        String jpql = "select e from Enrollment e where e.student = :studentId";
        return em.createQuery(jpql, Enrollment.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }

    public List<Enrollment> findAllEnrollmentsByCourseId(Integer courseId) {
        String jpql = "select e from Enrollment e where e.course = :courseId";
        return em.createQuery(jpql, Enrollment.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    public boolean isEnrolledInCourse(Integer studentId, Integer courseId) {
        String jpql = "select count(e) from Enrollment e where e.student = :studentId and e.course = :courseId";
        return em.createQuery(jpql, Integer.class)
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId)
                .getSingleResult() > 0;
    }
}
