package it.manyiw.digitaluniversity.controller;

import it.manyiw.digitaluniversity.domain.Course;
import it.manyiw.digitaluniversity.repository.CourseRepository;
import it.manyiw.digitaluniversity.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    public CourseController(@Autowired CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> getCourses() {
        return courseService.findAllOpenCourses();
    }
    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable Integer courseId) {
        return courseService.findCourseById(courseId);
    }
    @GetMapping(params = "title")
    public List<Course> getCoursesByTitle(@RequestParam("title") String title) {
        return courseRepository.findCoursesByTitle(title);
    }
    @GetMapping(params = "year")
    public List<Course> getCoursesByYear(@RequestParam("year") Integer year) {
        return courseRepository.findAllOpenCoursesByYear(year);
    }
    @GetMapping(params = "major")
    public List<Course> findAllOpenCoursesByMajor(@RequestParam("major") Integer majorId) {
        return courseRepository.findAllOpenCoursesByMajor(majorId);
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable Integer courseId) {
        courseService.deleteCourse(courseId);
    }
    // TODO update method
    // TODO change to input DTO and return a responseEntity
    @PostMapping
    public Integer createCourse(@Valid @RequestBody Course course) {
        return courseService.createCourse(course.getTitle(), course.getDescription(), course.getRegistrationLimit(),
                course.getMajor() != null ? course.getMajor().getId() : null, course.getYear());
    }
}
