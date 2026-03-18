package it.manyiw.digitaluniversity.domain;

import it.manyiw.digitaluniversity.domain.compositekey.EnrollmentId;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@IdClass(EnrollmentId.class)
public class Enrollment {
    @Id
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @DecimalMax("33.00")
    private BigDecimal vote = new BigDecimal(0);
    @Column(nullable = false)
    private LocalDate enrollmentDate;

    public Enrollment() {}
    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public BigDecimal getVote() {
        return vote;
    }
    public void setVote(BigDecimal vote) {
        this.vote = vote;
    }
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

}
