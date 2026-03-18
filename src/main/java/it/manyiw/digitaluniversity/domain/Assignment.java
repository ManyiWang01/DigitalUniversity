package it.manyiw.digitaluniversity.domain;

import it.manyiw.digitaluniversity.domain.compositekey.AssignmentId;
import it.manyiw.digitaluniversity.domain.enums.Role;
import jakarta.persistence.*;

@Entity
@IdClass(AssignmentId.class)
public class Assignment {
    @Id
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Id
    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Column(nullable = false)
    private boolean assigned = true;

    @Column(nullable = false)
    private Role role;

    public Assignment() {}

    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public Professor getProfessor() {
        return professor;
    }
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public boolean isAssigned() {
        return assigned;
    }
    public void assign() {
        this.assigned = true;
    }
    public void unassign() {this.assigned = false;}

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
