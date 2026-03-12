package it.manyiw.digitaluniversity.domain;

import java.io.Serializable;
import java.util.Objects;

// Composite Primary Key Class
public class ProfessorCourseId implements Serializable {
    private Integer professor;
    private Integer course;

    public ProfessorCourseId() {}

    public void setProfessor(Integer professor) {
        this.professor = professor;
    }
    public Integer getProfessor() {
        return professor;
    }
    public void setCourse(Integer course) {
        this.course = course;
    }
    public Integer getCourse() {
        return course;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfessorCourseId)) return false;
        ProfessorCourseId that = (ProfessorCourseId) o;
        return Objects.equals(professor, that.professor) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(professor, course);
    }
}
