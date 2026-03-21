package it.manyiw.digitaluniversity.controller;

import it.manyiw.digitaluniversity.domain.Student;
import it.manyiw.digitaluniversity.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(@Autowired StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> findStudents() {
        return studentService.findAllStudents();
    }
    @GetMapping("/enrolled")
    public List<Student> findAllEnrolledStudents() {
        return studentService.findAllEnrolledStudents();
    }
    @GetMapping("/graduate")
    public List<Student> findAllGraduatedStudents() {
        return studentService.findAllGraduatedStudents();
    }
    @GetMapping("/suspended")
    public List<Student> findAllSuspendedStudents() {
        return studentService.findAllSuspendedStudents();
    }
    @GetMapping("/{studentId}")
    public Student getStudentById(@PathVariable Integer studentId) {
        return studentService.findStudent(studentId);
    }
    @GetMapping(params = "name")
    public List<Student> getStudentByName(@RequestParam("name") String studentName) {
        String[] splitName = studentName.split(" ");
        String firstName = splitName[0];
        String lastName = "";
        if (splitName.length > 1) {
            lastName = splitName[1];
        }
        return studentService.findStudentByName(firstName, lastName);
    }
    // TODO update method
//    @PutMapping("/{studentId}")
//    public Integer updateStudent(@PathVariable Integer student) {
//        return studentService.updateStudent(student);
//    }
    @PostMapping
    public Integer createStudent(@Valid @RequestBody Student student) {
        return studentService.createStudent(student.getFirstName(), student.getLastName());
    }

}
