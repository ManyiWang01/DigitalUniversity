package it.manyiw.digitaluniversity.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "professors")
public class Professor extends Person {

    @OneToMany(mappedBy = "professor",  cascade = CascadeType.ALL)
    private Set<Assignment> assignments = new HashSet<>();
}
