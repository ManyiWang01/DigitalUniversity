package it.manyiw.digitaluniversity.service;

import it.manyiw.digitaluniversity.domain.Assignment;
import it.manyiw.digitaluniversity.domain.Course;
import it.manyiw.digitaluniversity.domain.Professor;
import it.manyiw.digitaluniversity.domain.enums.Role;
import it.manyiw.digitaluniversity.repository.AssignmentRepository;
import it.manyiw.digitaluniversity.repository.CourseRepository;
import it.manyiw.digitaluniversity.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    public AssignmentService(@Autowired AssignmentRepository assignmentRepository,
                             @Autowired CourseRepository courseRepository,
                             @Autowired ProfessorRepository professorRepository) {
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    public List<Assignment> findAllAssignmentsByProfessor(Integer professorId) {
        return assignmentRepository.findAllAssignmentByProfessor(professorId);
    }
    public List<Assignment> findAllAssignmentsByCourse(Integer courseId) {
        return assignmentRepository.findAllAssignmentByCourse(courseId);
    }
    public Assignment findAssignment(Integer professorId, Integer courseId) {
        return assignmentRepository.find(professorId, courseId);
    }

    public void createAssignment(Integer professorId, Integer courseId, Role role) {
        Professor professor = professorRepository.find(professorId);
        if (professor == null) {
            throw new IllegalArgumentException("professor with id " +  professorId + " not found");
        }
        Course course = courseRepository.find(courseId);
        if (course == null) {
            throw new IllegalArgumentException("course with id " +  courseId + " not found");
        }
        if (role == null) {
            throw new IllegalArgumentException("role cannot be null");
        }
        Assignment assignment = assignmentRepository.find(professorId, courseId);
        if (assignment == null) {
            assignment = new Assignment();
            assignment.setProfessor(professor);
            assignment.setCourse(course);
            assignment.setRole(role);
            assignmentRepository.save(assignment);
        }
        else if (assignment.getRole() != role) {
            assignment.setRole(role);
        }
    }

    public void remove(Integer professorId, Integer courseId) {
        if (!assignmentRepository.isAssigned(professorId, courseId)) {
            throw new IllegalStateException("professor with id " +  professorId + " is not assigned to course with id " + courseId);
        }
        assignmentRepository.remove(professorId, courseId);
    }
    public void changeRole(Integer professorId, Integer courseId, Role role) {
        Assignment assignment = assignmentRepository.find(professorId, courseId);
        if (assignment == null) {
            throw new IllegalStateException("professor with id " +  professorId + " is not assigned to course with id " + courseId);
        }
        if (role == null) {
            throw new IllegalArgumentException("To set the role, the role cannot be null");
        }
        if (assignment.getRole() != role) {
            return;
        }
        assignment.setRole(role);
    }
}
