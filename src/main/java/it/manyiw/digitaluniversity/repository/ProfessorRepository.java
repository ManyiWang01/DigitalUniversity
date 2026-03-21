package it.manyiw.digitaluniversity.repository;

import it.manyiw.digitaluniversity.domain.Professor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ProfessorRepository extends PersonRepository<Professor> {
    @PersistenceContext
    private EntityManager em;

    public ProfessorRepository() {
        super(Professor.class);
    }
}
