package it.manyiw.digitaluniversity.domain.compositekey;

import java.io.Serializable;
import java.util.Objects;

// Composite Primary Key Class
public class EnrollmentId implements Serializable {
    private Integer student;
    private Integer course;

    public EnrollmentId() {}
    public EnrollmentId(Integer student, Integer course) {
        this.student = student;
        this.course = course;
    }

    public void setStudent(Integer student) {
        this.student = student;
    }
    public Integer getStudent() {
        return student;
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
        if (!(o instanceof EnrollmentId)) return false;
        EnrollmentId that = (EnrollmentId) o;
        return Objects.equals(student, that.student) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }
}
