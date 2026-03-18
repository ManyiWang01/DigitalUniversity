package it.manyiw.digitaluniversity.domain.compositekey;

import java.io.Serializable;
import java.util.Objects;

// Composite Primary Key Class
public class AssignmentId implements Serializable {
    private Integer professor;
    private Integer course;

    public AssignmentId() {}

    public AssignmentId(Integer professor, Integer course) {
        this.professor = professor;
        this.course = course;
    }

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
        if (!(o instanceof AssignmentId)) return false;
        AssignmentId that = (AssignmentId) o;
        return Objects.equals(professor, that.professor) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(professor, course);
    }
}
