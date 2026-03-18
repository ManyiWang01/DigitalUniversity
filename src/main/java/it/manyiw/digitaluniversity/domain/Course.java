package it.manyiw.digitaluniversity.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SoftDelete;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@SoftDelete(columnName = "isClosed")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int id;

    @NotBlank(message = "Enter a valid title for this course")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Add a valid description for this course")
    @Column(nullable = false)
    private String description = "This is the description the course";

    @Min(value = 1, message = "Minimum student to this course must be higher than 0")
    private int registrationLimit = 1;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "majorId", nullable = false)
    private Major major;

    @Min(value = 1)
    @Max(value = 3)
    @Column(nullable = false)
    private int year = 1;

    // not changing really much adding them
//    private int semester;
//    private int credit;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
//    private Set<Student> attendingStudents = new HashSet<>();
    @OneToMany(mappedBy = "course", cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Set<Enrollment> enrolledStudents = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Set<Assignment> teachingProfs = new HashSet<>();

    @Column(nullable = false)
    private boolean isClosed = true;

    public Course() {}

    public Integer getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setRegistrationLimit(int registrationLimit) {
        this.registrationLimit = registrationLimit;
    }
    public int getRegistrationLimit() {
        return registrationLimit;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
    public Major getMajor() {
        return major;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public int getYear() {
        return year;
    }

    public Set<Enrollment> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Set<Assignment> getTeachingProfs() {
        return teachingProfs;
    }

    public boolean isClosed() {
        return isClosed;
    }
    public void setClosed(boolean closed) {
        this.isClosed = closed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return id != 0 && id == course.id; // Compare IDs
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
