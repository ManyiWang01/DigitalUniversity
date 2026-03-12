package it.manyiw.digitaluniversity.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
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

    @Min(value = 1990)
    @Column(nullable = false)
    private int academicYear = 1990;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> attendingStudents = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Set<ProfessorCourse> teachingProfs = new HashSet<>();

    @Column(nullable = false)
    private boolean active = true;

    public Course() {}

    public int getId() {
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

    public void setAcademicYear(int academicYear) {
        this.academicYear = academicYear;
    }
    public int getAcademicYear() {
        return academicYear;
    }

    public Set<Student> getAttendingStudents() {
        return attendingStudents;
    }
    public void addAttendingStudent(Student student) {
        if (student == null) {return;}
        this.attendingStudents.add(student);
        student.getAttendedCourses().add(this);
    }
    public void removeAttendingStudent(Student student) {
        if (student == null) {return;}
        this.attendingStudents.remove(student);
        student.getAttendedCourses().remove(this);
    }

    public Set<ProfessorCourse> getTeachingProfs() {
        return teachingProfs;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
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
