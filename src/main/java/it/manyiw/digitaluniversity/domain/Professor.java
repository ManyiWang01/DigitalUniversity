package it.manyiw.digitaluniversity.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "professors")
public class Professor extends Person {

    @OneToMany(mappedBy = "professor")
    private Set<ProfessorCourse> assignments = new HashSet<>();
}
