package it.manyiw.digitaluniversity.repository;

import it.manyiw.digitaluniversity.domain.Professor;
import org.springframework.stereotype.Repository;

@Repository
public class ProfessorRepository extends PersonRepository<Professor> {

    public ProfessorRepository() {
        super(Professor.class);
    }

}
