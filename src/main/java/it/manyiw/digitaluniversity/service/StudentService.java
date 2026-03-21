package it.manyiw.digitaluniversity.service;

import it.manyiw.digitaluniversity.domain.Student;
import it.manyiw.digitaluniversity.domain.enums.StudentStatus;
import it.manyiw.digitaluniversity.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional // all class public methods are now a transaction
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(@Autowired StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Integer createStudent(String firstName, String lastName) {
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setInstitutionalMail(generateUniqueInstitutionalEmail(firstName, lastName));
        return studentRepository.save(student);
    }

    public Student findStudent(Integer studentId) {
        return studentRepository.find(studentId);
    }

    private String generateUniqueInstitutionalEmail(String firstName, String lastName) {
        String baseMail = firstName + "." + lastName;
        String domain = "@mail.digitaluniversity.com";
        int count = 1;
        String uniqueMail = baseMail + domain;
        while (studentRepository.existsByInstitutionalMail(uniqueMail)) {
            uniqueMail = baseMail + count + domain;
            count++;
        }
        return uniqueMail;
    }

    public void enrollStudent(Integer studentId) {
        Student student = studentRepository.find(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student with id " + studentId + " does not exist");
        }
        else if (!student.getStatus().equals(StudentStatus.SUSPENDED)) {
            throw new IllegalArgumentException("You can't enroll student with status " + student.getStatus());
        }
        student.setStatus(StudentStatus.ENROLLED);
    }
    public void suspendStudent(Integer studentId) {
        Student student = studentRepository.find(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student with id " + studentId + " does not exist");
        }
        else if (!student.getStatus().equals(StudentStatus.ENROLLED)) {
            throw new IllegalArgumentException("You can't suspend student with status " + student.getStatus());
        }
        student.setStatus(StudentStatus.SUSPENDED);
    }
    public void graduateStudent(Integer studentId) {
        Student student = studentRepository.find(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student with id " + studentId + " does not exist");
        }
        else if (!student.getStatus().equals(StudentStatus.ENROLLED)) {
            throw new IllegalArgumentException("You can't graduate student with status " + student.getStatus());
        }
        student.setStatus(StudentStatus.GRADUATED);
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> findAllEnrolledStudents() {
        return studentRepository.findAllEnrolled();
    }
    public List<Student> findAllSuspendedStudents() {
        return studentRepository.findAllSuspended();
    }
    public List<Student> findAllGraduatedStudents() {
        return studentRepository.findAllGraduated();
    }

    public List<Student> findAllStudentsByGrade(Integer grade) {
        if (grade < 1 || grade > 3) {
            throw new IllegalArgumentException("Grade must be between 1 and 3");
        }
        return studentRepository.findAllStudentByGrade(grade);
    }

    public void upgradeStudent(Integer studentId) {
        Student student = studentRepository.find(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student with id " + studentId + " does not exist");
        }
        else if (!student.getStatus().equals(StudentStatus.ENROLLED)) {
            throw new IllegalArgumentException("You can't upgrade student with status " + student.getStatus());
        }
        studentRepository.upgradeEligibleStudent(studentId);
    }
    public void upgradeAllEligibleStudents() {
        // Use this method only at end of each academic year
        studentRepository.upgradeAllEligibleStudent();
    }

    public Student findStudentByInstitutionalMail(String institutionalMail) {
        return studentRepository.findByInstitutionalMail(institutionalMail);
    }

    public List<Student> findStudentByName(String firstName, String lastName) {
        return studentRepository.findByName(firstName, lastName);
    }

}
