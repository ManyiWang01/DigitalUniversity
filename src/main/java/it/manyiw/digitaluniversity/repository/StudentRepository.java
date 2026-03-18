package it.manyiw.digitaluniversity.repository;

import it.manyiw.digitaluniversity.domain.Course;
import it.manyiw.digitaluniversity.domain.Student;
import it.manyiw.digitaluniversity.domain.enums.StudentStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository extends PersonRepository<Student> {

    public StudentRepository() {
        super(Student.class);
    }

    private List<Student> filterByStatus(StudentStatus status) {
        String jpql = "select s from Student s where s.status = :status";
        return em.createQuery(jpql, Student.class)
                .setParameter("status", status)
                .getResultList();
    }

    public List<Student> findAllEnrolled() {
        return  filterByStatus(StudentStatus.ENROLLED);
    }

    public List<Student> findAllGraduated() {
        return  filterByStatus(StudentStatus.GRADUATED);
    }

    public List<Student> findAllSuspended() {
        return  filterByStatus(StudentStatus.SUSPENDED);
    }

//    public List<Course> findAllAttendedCourses(Integer studentId) {
//        String jpql = "select c from Student s join s.attendedCourses c where s.id = :id";
//        return em.createQuery(jpql, Course.class)
//                .setParameter("id", studentId)
//                .getResultList();
//    }

    public List<Student> findAllStudentByGrade(Integer grade) {
        String jpql = "select s from Student s where s.grade = :grade";
        return em.createQuery(jpql, Student.class)
                .setParameter("grade", grade)
                .getResultList();
    }

    public void upgradeAllEligibleStudent() {
        String jpql = "update Student s set s.grade = s.grade + 1 " +
                "where s.grade < 3 and s.status = :status";
        em.createQuery(jpql)
                .setParameter("status", StudentStatus.ENROLLED)
                .executeUpdate();
    }

    public void upgradeEligibleStudent(Integer studentId) {
        String jpql = "update Student s set s.grade = s.grade + 1 " +
                "where s.grade < 3 and s.status = :status and s.id = :studentId";
        em.createQuery(jpql)
                .setParameter("status", StudentStatus.ENROLLED)
                .setParameter("studentId", studentId)
                .executeUpdate();
    }

}
