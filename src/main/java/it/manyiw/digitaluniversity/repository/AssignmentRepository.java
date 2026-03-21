package it.manyiw.digitaluniversity.repository;

import it.manyiw.digitaluniversity.domain.Assignment;
import it.manyiw.digitaluniversity.domain.Course;
import it.manyiw.digitaluniversity.domain.compositekey.AssignmentId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AssignmentRepository {
    @PersistenceContext
    private EntityManager em;

    public AssignmentRepository() {}

    public void save(Assignment assignment) {
        em.persist(assignment);
    }
    public void remove(Assignment assignment) {
        em.remove(assignment);
    }
    public void remove(Integer professorId, Integer courseId) {
        em.remove(em.find(Assignment.class, new AssignmentId(professorId, courseId)));
    }

    public Assignment find(Integer professorId, Integer courseId) {
        AssignmentId assignmentId = new AssignmentId(professorId, courseId);
        return em.find(Assignment.class, assignmentId);
    }

    public List<Assignment> findAllAssignmentByProfessor(Integer professorId) {
        String jpql = "select a from Assignment a where a.professor.id = :professorId";
        return em.createQuery(jpql, Assignment.class)
                .setParameter("professorId", professorId)
                .getResultList();
    }

    public List<Assignment> findAllAssignmentByCourse(Integer courseId) {
        String jpql = "select a from Assignment a where a.course.id = :courseId";
        return em.createQuery(jpql, Assignment.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    public boolean isAssigned(Integer professorId, Integer courseId) {
        String jpql = "select count(a) from Assignment a where a.professor.id = :professorId and a.course.id = :courseId";
        return em.createQuery(jpql, Integer.class)
                .setParameter("professorId", professorId)
                .setParameter("courseId", courseId)
                .getSingleResult() > 0;
    }

}
