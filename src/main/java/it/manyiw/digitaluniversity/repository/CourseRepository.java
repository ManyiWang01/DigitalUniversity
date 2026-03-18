package it.manyiw.digitaluniversity.repository;

import it.manyiw.digitaluniversity.domain.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseRepository {

    @PersistenceContext
    private EntityManager em;

    public Integer save(Course course) {
        em.persist(course);
        return course.getId();
    }

    public Course find(int id) {
        return em.find(Course.class, id);
    }

    public Course findCourseByTitle(String title) {
        String jpql = "select c from Course c where c.title = :title";
        return em.createQuery(jpql, Course.class)
                .setParameter("title", title)
                .getSingleResult();
    }

    public List<Course> findAllCourses() {
        String jpql = "select c from Course c";
        return em.createQuery(jpql, Course.class)
                .getResultList();
    }

    public Course findClosedCourseById(Integer courseId) {
        String sql = "select * from courses where course_id = ? and isClosed = true";
        return (Course) em.createNativeQuery(sql, Course.class)
                .setParameter(1, courseId)
                .getSingleResult();
    }

    public boolean isClosed(Integer courseId) {
        String sql = "select count(*) from courses where course_id = ? and isClosed = true";
        return (Integer) em.createNativeQuery(sql, Course.class)
                .setParameter(1, courseId)
                .getSingleResult() > 0;
    }

    public List<Course> findAllOpenCoursesByYear(int year) {
        String jpql = "select c from Course c where c.year = :year";
        return em.createQuery(jpql, Course.class)
                .setParameter("year", year)
                .getResultList();
    }
    public List<Course> findAllCoursesByYear(int year) {
        String sql = "select * from courses where year = ?";
        return em.createNativeQuery(sql, Course.class)
                .setParameter(1, year)
                .getResultList();
    }

    public List<Course> findAllOpenCoursesByMajor(int majorId) {
        String jpql = "select c from Course c where c.major.id = :majorId";
        return em.createQuery(jpql, Course.class)
                .setParameter("majorId", majorId)
                .getResultList();
    }
    public List<Course> findAllCoursesByMajor(int majorId) {
        String sql = "select * from courses where majorId = ?";
        return em.createNativeQuery(sql, Course.class)
                .setParameter(1, majorId)
                .getResultList();
    }

    public boolean existsById(Integer courseId) {
        String jpql = "select count(c) from Course c where c.id = :courseId";
        return em.createQuery(jpql, Long.class)
                .setParameter("courseId", courseId)
                .getSingleResult() > 0;
    }

}
