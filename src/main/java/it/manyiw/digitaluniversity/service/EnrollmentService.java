package it.manyiw.digitaluniversity.service;

import it.manyiw.digitaluniversity.domain.Course;
import it.manyiw.digitaluniversity.domain.Enrollment;
import it.manyiw.digitaluniversity.domain.Student;
import it.manyiw.digitaluniversity.repository.CourseRepository;
import it.manyiw.digitaluniversity.repository.EnrollmentRepository;
import it.manyiw.digitaluniversity.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
public class EnrollmentService {
    // TODO business logic for Enrollment, eg. enrollStudentToCourse, eliminateStudentToCourse, registerVote, cancelVote
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentService(@Autowired EnrollmentRepository enrollmentRepository,
                             @Autowired StudentRepository studentRepository,
                             @Autowired CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public void enroll(Integer studentId, Integer courseId) {
        Student student = studentRepository.find(studentId);
        if (student == null) {
            throw new IllegalArgumentException("student with id " + studentId + " not found");
        }
        Course course = courseRepository.find(courseId);
        if (course == null) {
            throw new IllegalArgumentException("course with id " + courseId + " not found");
        }
        Enrollment enrollment = enrollmentRepository.find(studentId, courseId);
        if (enrollment != null) {
            throw new IllegalStateException("student with id " + studentId + " is already enrolled in course with id " + courseId);
        }
        enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);
    }
    public void unenroll(Integer studentId, Integer courseId) {
        if (!enrollmentRepository.isEnrolledInCourse(studentId, courseId)) {
            throw new IllegalStateException("student with id " + studentId + " is not enrolled in course with id " + courseId);
        }
        enrollmentRepository.remove(studentId, courseId);
    }
    public Enrollment findEnrollment(Integer studentId, Integer courseId) {
        return  enrollmentRepository.find(studentId, courseId);
    }
    public List<Enrollment> findAllEnrollmentsByStudent(Integer studentId) {
        return enrollmentRepository.findAllEnrollmentsByStudentId(studentId);
    }
    public List<Enrollment> findAllEnrollmentsByCourse(Integer courseId) {
        return enrollmentRepository.findAllEnrollmentsByCourseId(courseId);
    }
    public void registerVoteToStudent(Integer studentId, Integer courseId, BigDecimal vote) {
        Enrollment enrollment = enrollmentRepository.find(studentId, courseId);
        if (enrollment == null) {
            throw new IllegalStateException("Student with id " + studentId + " is not enrolled in course with id " + courseId);
        }
        enrollment.setVote(vote);

    }
    public void cancelVoteToStudent(Integer studentId, Integer courseId) {
        Enrollment enrollment = enrollmentRepository.find(studentId, courseId);
        if (enrollment == null) {
            throw new IllegalStateException("Student with id " + studentId + " is not enrolled in course with id " + courseId);
        }
        enrollment.setVote(BigDecimal.ZERO);
    }
    public BigDecimal computeAverageVoteByStudent(Integer studentId) {
        Student student = studentRepository.find(studentId);
        if (student == null) {
            throw new IllegalArgumentException("student with id " + studentId + " not found");
        }
        List<Enrollment> enrollments = enrollmentRepository.findAllEnrollmentsByStudentId(studentId);
        BigDecimal averageVote = BigDecimal.ZERO;
        int passedExams = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getVote().compareTo(BigDecimal.valueOf(18)) >= 0) {
                averageVote = averageVote.add(enrollment.getVote());
                passedExams++;
            }
        }
        return averageVote.divide(passedExams == 0 ? BigDecimal.ONE : BigDecimal.valueOf(passedExams), 2, RoundingMode.DOWN);
    }

}
