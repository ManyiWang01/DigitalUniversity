package it.manyiw.digitaluniversity.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends Person {

    @Column(nullable = false)
    private StudentStatus status = StudentStatus.ENROLLED;

    @ManyToMany(mappedBy = "attendingStudents")
    private Set<Course> attendedCourses = new HashSet<>();

    @Min(value = 1, message = "A student cannot be lower than grade 1")
    @Max(value = 3, message = "A student cannot be higher than grade 3")
    @Column(nullable = false)
    private Integer grade = 1;

    public Student() {}

    public void setStatus(StudentStatus status) {
        this.status = status;
    }
    public StudentStatus getStatus() {
        return status;
    }

    public Set<Course> getAttendedCourses() {
        return attendedCourses;
    }
    // Since the owner of the relationship is on Course, by doing add here has no effect in DB
//    public void addAttendedCourses(Course course) {
//        this.attendedCourses.add(course);
//    }

    public Integer getGrade() {
        return grade;
    }
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

}
