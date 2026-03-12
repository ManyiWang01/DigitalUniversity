package it.manyiw.digitaluniversity.repository;

import it.manyiw.digitaluniversity.domain.Person;
import it.manyiw.digitaluniversity.domain.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public abstract class PersonRepository<T extends Person> {

    @PersistenceContext
    protected EntityManager em;

    private final Class<T> entityClass;

    public PersonRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(T person) {
        em.persist(person);
    }

    public T merge(T person) {
        return em.merge(person);
    }

    public T findById(int id) {
        return em.find(entityClass, id);
    }

    public List<T> findAll() {
        String jpql = "select p from " +  entityClass.getName() + " p";
        return em.createQuery(jpql, entityClass).getResultList();
    }

    public List<T> findAllByMajor(Integer majorId) {
        String jpql = "select s from " + entityClass.getSimpleName() + " s where s.majorId = :majorId";
        return em.createQuery(jpql, entityClass)
                .setParameter("majorId", majorId)
                .getResultList();
    }

    public List<T> findByFirstName(String firstName) {
        String jpql = "select p from " + entityClass.getSimpleName() + " p where p.firstName = :firstName";
        return em.createQuery(jpql, entityClass)
                .setParameter("firstName", firstName)
                .getResultList();
    }

    public List<T> findByLastName(String lastName) {
        String jpql = "select p from " + entityClass.getSimpleName() + " p where p.lastName = :lastName";
        return em.createQuery(jpql, entityClass)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    public List<T> findByName(String firstName, String lastName) {
        String jpql = "select p from " + entityClass.getSimpleName() + " p where p.firstName = :firstName and p.lastName = :lastName";
        return em.createQuery(jpql, entityClass)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    public T findByInstitutionalMail(String institutionalMail) {
        String jpql = "select p from " + entityClass.getSimpleName() + " p where p.institutionalMail = :institutionalMail";
        return em.createQuery(jpql, entityClass)
                .setParameter("institutionalMail", institutionalMail)
                .getSingleResult();
    }

    public boolean existsByInstitutionalMail(String institutionalMail) {
        String jpql = "select count(p) from " + entityClass.getSimpleName() + " p where p.institutionalMail = :institutionalMail";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("institutionalMail", institutionalMail)
                .getSingleResult();
        return count > 0;
    }

    public List<T> findByAge(Integer age) {
        String jpql = "select p from " + entityClass.getSimpleName() + " p where p.age = :age";
        return em.createQuery(jpql, entityClass)
                .setParameter("age", age)
                .getResultList();
    }

    public List<T> findByPersonalEmail(String email) {
        String jpql = "select p from " + entityClass.getSimpleName() + " p where p.email = :email";
        return em.createQuery(jpql, entityClass)
                .setParameter("email", email)
                .getResultList();
    }
}
