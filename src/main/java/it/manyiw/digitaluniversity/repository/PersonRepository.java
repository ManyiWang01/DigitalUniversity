package it.manyiw.digitaluniversity.repository;

import it.manyiw.digitaluniversity.domain.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;

public abstract class PersonRepository<T extends Person> {

    @PersistenceContext
    protected EntityManager em;

    private final Class<T> entityClass;

    public PersonRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public int save(T person) {
        em.persist(person);
        return person.getId();
    }

    public T find(int id) {
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

//    public List<T> findByFirstName(String firstName) {
//        String jpql = "select p from " + entityClass.getSimpleName() + " p where p.firstName = :firstName";
//        return em.createQuery(jpql, entityClass)
//                .setParameter("firstName", firstName)
//                .getResultList();
//    }
//
//    public List<T> findByLastName(String lastName) {
//        String jpql = "select p from " + entityClass.getSimpleName() + " p where p.lastName = :lastName";
//        return em.createQuery(jpql, entityClass)
//                .setParameter("lastName", lastName)
//                .getResultList();
//    }

    public List<T> findByName(String firstName, String lastName) {
        String jpql = "select p from " + entityClass.getSimpleName() + " p where 1=1";
        boolean hasLastName = lastName != null && !lastName.isEmpty();
        boolean hasFirstName = firstName != null && !firstName.isEmpty();
        if (!hasFirstName && !hasLastName) {
            return Collections.emptyList();
        }
        else {
            if (hasLastName) {
                jpql += " and p.lastName like :lastName";
            }
            if (hasFirstName) {
                jpql += " and p.firstName like :firstName";
            }
        }
        jpql += " order by p.lastName asc, p.firstName asc";
        TypedQuery<T> query = em.createQuery(jpql, entityClass);
        if (hasLastName) {
            query.setParameter("lastName", "%" + lastName + "%");
        }
        if (hasFirstName) {
            query.setParameter("firstName", "%" + firstName + "%");
        }
        return query.getResultList();
    }

    public T findByInstitutionalMail(String institutionalMail) {
        String jpql = "select p from " + entityClass.getSimpleName() + " p where p.institutionalMail = :institutionalMail";
        return em.createQuery(jpql, entityClass)
                .setParameter("institutionalMail", institutionalMail)
                .getSingleResult();
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

    public boolean existsByInstitutionalMail(String institutionalMail) {
        String jpql = "select count(p) from " + entityClass.getSimpleName() + " p where p.institutionalMail = :institutionalMail";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("institutionalMail", institutionalMail)
                .getSingleResult();
        return count > 0;
    }
    public boolean existsById(Integer id) {
        String jpql = "select count(p) from " + entityClass.getSimpleName() + " p where p.id = :id";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }

}
