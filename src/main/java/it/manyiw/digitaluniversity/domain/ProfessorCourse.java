package it.manyiw.digitaluniversity.domain;

import jakarta.persistence.*;

@Entity
@IdClass(ProfessorCourseId.class)
public class ProfessorCourse {
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

    public ProfessorCourse() {}

    public int getCourseId() {
        return course.getId();
    }

    public int getProfessorId() {
        return professor.getId();
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }
}
