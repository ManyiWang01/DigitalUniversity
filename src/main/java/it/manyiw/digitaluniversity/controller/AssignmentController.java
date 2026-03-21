package it.manyiw.digitaluniversity.controller;

import it.manyiw.digitaluniversity.domain.Assignment;
import it.manyiw.digitaluniversity.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AssignmentController {
    private final AssignmentService assignmentService;

    public AssignmentController(@Autowired AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("professor/{professorId}/assignments")
    public List<Assignment> findAllAssignmentsByProfessor(@PathVariable Integer professorId) {
        return assignmentService.findAllAssignmentsByProfessor(professorId);
    }
    @GetMapping("professor/{professorId}/assignments/{courseId}")
    public Assignment findAssignmentByProfessor(@PathVariable Integer professorId, @PathVariable Integer courseId) {
        return assignmentService.findAssignment(professorId, courseId);
    }


    @GetMapping("courses/{courseId}/assignments")
    public List<Assignment> findAllAssignmentsByCourse(@PathVariable Integer courseId) {
        return assignmentService.findAllAssignmentsByCourse(courseId);
    }
    @GetMapping("course/{courseId}/assignments/{professorId}")
    public Assignment findAssignmentByByCourse(@PathVariable Integer courseId, @PathVariable Integer professorId) {
        return assignmentService.findAssignment(professorId, courseId);
    }
    @PostMapping("courses/{courseId}/assignments")
    public void assignCourse(@PathVariable Integer courseId, @RequestBody Assignment assignment) {
        assignmentService.createAssignment(assignment.getProfessor().getId(), courseId, assignment.getRole());
    }
    @DeleteMapping(value = "courses/{courseId}/assignments", params = "professorId")
    public void unassignProfessor(@PathVariable Integer courseId, @RequestParam("professorId") Integer professorId) {
        assignmentService.remove(professorId, courseId);
    }
    // TODO update method
//    @PatchMapping()
//    public Assignment updateRole(@RequestBody Assignment assignment) {
//        changeRole(professorId, courseId)
//    }

}
