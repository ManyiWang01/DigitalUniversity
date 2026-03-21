package it.manyiw.digitaluniversity.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@SoftDelete(columnName = "isClosed", strategy = SoftDeleteType.DELETED)
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
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "majorId", nullable = false)
    private Major major;

    @Min(value = 1)
    @Max(value = 3)
    @Column(nullable = false, name = "yearOfStudy")
    private int year = 1;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Enrollment> enrolledStudents = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Set<Assignment> teachingProfs = new HashSet<>();

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
