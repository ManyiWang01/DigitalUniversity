package it.manyiw.digitaluniversity.service;

import it.manyiw.digitaluniversity.domain.Course;
import it.manyiw.digitaluniversity.domain.Major;
import it.manyiw.digitaluniversity.repository.CourseRepository;
import it.manyiw.digitaluniversity.repository.MajorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;
    private final MajorRepository majorRepository;

    public CourseService(@Autowired CourseRepository courseRepository,
                        @Autowired MajorRepository majorRepository) {
        this.courseRepository = courseRepository;
        this.majorRepository = majorRepository;
    }

    public Integer createCourse(String title, String description, Integer studentLimit, Integer majorId, Integer year) {
        // TODO: wrap in a exception handler, and validate all the inputs.
        //  Wrap the parameters into DTO/Projection depending on what is required by controller
        Course course = new Course();
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        else {
            course.setTitle(title);
        }
        if (description != null && !description.isEmpty()) {
            course.setDescription(description);
        }
        if (studentLimit != null && studentLimit > 0) {
            course.setRegistrationLimit(studentLimit);
        }
        else {
            throw new IllegalArgumentException("Student Limit cannot be null or empty");
        }
        if (majorId != null) {
            Major major = majorRepository.findMajorById(majorId);
            if (major != null) {
                course.setMajor(major);
            }
            else {
                throw new IllegalArgumentException("Major with id " + majorId + " cannot be found");
            }
        }
        else  {
            throw new IllegalArgumentException("Major cannot be null");
        }
        if (year != null && year > 1990) {
            course.setYear(year);
        }
        else {
            throw new IllegalArgumentException("Academic Year cannot be null or less than 1990");
        }
        return courseRepository.save(course);
    }

    public Course findCourseById(Integer id) {
        return courseRepository.find(id);
    }
    public List<Course> findAllOpenCourses() {
        return courseRepository.findAllCourses();
    }

    public List<Course> findAllOpenCoursesByMajor(Integer majorId) {
        if (majorId == null) {
            throw new IllegalArgumentException("Major cannot be null");
        }
        else {
            // TODO replace this with check with DTO, its just a read in DB
            if (majorRepository.findMajorById(majorId) == null) {
                throw new IllegalArgumentException("Major with id " + majorId + " cannot be found");
            }
        }
        return courseRepository.findAllOpenCoursesByMajor(majorId);
    }
    public List<Course> findAllCoursesByMajor(Integer majorId) {
        if (majorId == null) {
            throw new IllegalArgumentException("Major cannot be null");
        }
        else {
            // TODO replace this with check with DTO, its just a read in DB
            if (majorRepository.findMajorById(majorId) == null) {
                throw new IllegalArgumentException("Major with id " + majorId + " cannot be found");
            }
        }
        return courseRepository.findAllCoursesByMajor(majorId);
    }

    public List<Course> findAllOpenCoursesByYear(Integer year) {
        if (year == null) {
            throw new IllegalArgumentException("Year cannot be null");
        }
        else {
            if (year < 1 || year > 3) {
                throw new IllegalArgumentException("Year must be between 1 and 3");
            }
        }
        return courseRepository.findAllOpenCoursesByYear(year);
    }
    public List<Course> findAllCoursesByYear(Integer year) {
        if (year == null) {
            throw new IllegalArgumentException("Year cannot be null");
        }
        else {
            if (year < 1 || year > 3) {
                throw new IllegalArgumentException("Year must be between 1 and 3");
            }
        }
        return courseRepository.findAllCoursesByYear(year);
    }
}
