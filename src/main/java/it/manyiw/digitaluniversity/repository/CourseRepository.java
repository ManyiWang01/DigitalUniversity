package it.manyiw.digitaluniversity.repository;

import it.manyiw.digitaluniversity.domain.Course;
import it.manyiw.digitaluniversity.domain.ProfessorCourse;
import it.manyiw.digitaluniversity.domain.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CourseRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Course course) {
        em.persist(course);
    }

    public Course find(int id) {
        return em.find(Course.class, id);
    }

    public Course merge(Course course) {
        return em.merge(course);
    }

    public Course findCourseByTitle(String title) {
        String jpql = "select c from Course c where c.title = :title and c.active = :active";
        return em.createQuery(jpql, Course.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    public List<Course> findAllCourses() {
        String jpql = "select c from Course c";
        return em.createQuery(jpql, Course.class)
                .getResultList();
    }

    private List<Course> findAllCoursesByStatus(boolean status) {
        String jpql = "select c from Course c where c.active = :status";
        return em.createQuery(jpql, Course.class)
                .setParameter("status", status)
                .getResultList();
    }

    public List<Course> findAllOpenCourses() {
        return findAllCoursesByStatus(true);
    }

    public List<Course> findAllClosedCourses() {
        return findAllCoursesByStatus(false);
    }

    public List<Course> findCoursesByAcademicYear(int academicYear) {
        String jpql = "select c from Course c where c.academicYear = :academicYear";
        return em.createQuery(jpql, Course.class)
                .setParameter("academicYear", academicYear)
                .getResultList();
    }

    public Set<Student> findAttendingStudents(int courseId) {
        String jpql = "select s from Course c join c.attendingStudents s where c.id = :courseId";
        List<Student> students = em.createQuery(jpql, Student.class)
                .setParameter("courseId", courseId)
                .getResultList();
        return new HashSet<>(students);
    }

    public Set<ProfessorCourse> findTeachingProfs(int courseId) {
        String jpql = "select pc from Course c join c.teachingProfs pc where c.id = :courseId";
        List<ProfessorCourse> teachingProfs = em.createQuery(jpql, ProfessorCourse.class)
                .setParameter("courseId", courseId)
                .getResultList();
        return new HashSet<>(teachingProfs);
    }

    public List<Course> findAllOpenCoursesByMajor(int majorId) {
        String jpql = "select c from Course c where c.major.id = :majorId and c.active = true";
        return em.createQuery(jpql, Course.class)
                .setParameter("majorId", majorId)
                .getResultList();
    }

    public List<Course> findAllCoursesByMajor(int majorId) {
        String jpql = "select c from Course c where c.major.id = :majorId";
        return em.createQuery(jpql, Course.class)
                .setParameter("majorId", majorId)
                .getResultList();
    }

}
