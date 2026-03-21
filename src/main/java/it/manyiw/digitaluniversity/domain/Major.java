package it.manyiw.digitaluniversity.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

@Entity
@Table(name = "majors")
@SoftDelete(columnName = "deleted", strategy = SoftDeleteType.DELETED)
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "major_id")
    private int id;

    @NotBlank(message = "Enter a valid name of this major")
    @Column(nullable = false, unique = true)
    private String name;

    public Major() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
