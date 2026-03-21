package it.manyiw.digitaluniversity.controller;

import it.manyiw.digitaluniversity.domain.Enrollment;
import it.manyiw.digitaluniversity.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(@Autowired EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/students/{studentId}/enrollments")
    public List<Enrollment> findAllEnrolledCourses(@PathVariable Integer studentId) {
        return enrollmentService.findAllEnrollmentsByStudent(studentId);
    }
    @PostMapping("/students/{studentId}/enrollments")
    public void enrollStudentToCourse(@PathVariable Integer studentId,
                                      @Valid @RequestParam Integer courseId) {
        // TODO return url to the student enrollments
        enrollmentService.enroll(studentId, courseId);
    }
    @DeleteMapping(value = "/students/{studentId}/enrollments", params = "courseId")
    public void eliminateStudentToCourse(@PathVariable Integer studentId, @RequestParam("courseId") Integer courseId) {
        enrollmentService.unenroll(studentId, courseId);
    }
    @GetMapping(value = "/students/{studentId}/enrollments", params = "courseId")
    public Enrollment findEnrollmentByStudent(@PathVariable Integer studentId, @RequestParam("courseId") Integer courseId) {
        return enrollmentService.findEnrollment(studentId, courseId);
    }

    @GetMapping("/courses/{courseId}/enrollments")
    public List<Enrollment> findAllEnrolledStudents(@PathVariable Integer courseId) {
        return enrollmentService.findAllEnrollmentsByCourse(courseId);
    }
    @GetMapping(value = "/courses/{courseId}/enrollments", params = "studentId")
    public Enrollment findEnrollmentByCourse(@PathVariable Integer courseId, @RequestParam("studentId") Integer studentId) {
        return enrollmentService.findEnrollment(studentId, courseId);
    }
    // TODO update method, if there is vote: X then registerVote, if there is vote: null or cancel: true then cancelVote
//    @PatchMapping("/courses/{courseId}/enrollments")
//    public void patchVote(@PathVariable Integer courseId, @Valid @RequestBody Enrollment enrollment) {
//
//    }
}
