package it.manyiw.digitaluniversity.repository;

import it.manyiw.digitaluniversity.domain.Professor;
import it.manyiw.digitaluniversity.domain.ProfessorCourse;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ProfessorRepository extends PersonRepository<Professor> {

    public ProfessorRepository() {
        super(Professor.class);
    }

    public Set<ProfessorCourse> findActivateAssignments(int professorId) {
        return new HashSet<>(findAssignmentsByStatus(professorId, true));
    }

    public Set<ProfessorCourse> findPastAssignments(int professorId) {
        return new HashSet<>(findAssignmentsByStatus(professorId, false));
    }

    private List<ProfessorCourse> findAssignmentsByStatus(int professorId, boolean assigned) {
        String jpql = "select pc from Professor p join p.assignments pc where p.id = :id and pc.assigned = :status";
        return em.createQuery(jpql, ProfessorCourse.class)
                .setParameter("id", professorId)
                .setParameter("status", assigned)
                .getResultList();
    }

    public Set<ProfessorCourse> findAllAssignments(int professorId) {
        String jpql = "select pc from Professor p join p.assignments pc where p.id = :id";
        List<ProfessorCourse> pc = em.createQuery(jpql, ProfessorCourse.class)
                .setParameter("id", professorId)
                .getResultList();
        return new HashSet<>(pc);
    }
}
