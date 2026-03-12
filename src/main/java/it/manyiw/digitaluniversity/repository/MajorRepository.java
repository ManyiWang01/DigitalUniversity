package it.manyiw.digitaluniversity.repository;

import it.manyiw.digitaluniversity.domain.Course;
import it.manyiw.digitaluniversity.domain.Major;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MajorRepository {
    @PersistenceContext
    private EntityManager em;

    public MajorRepository() {}

    public void save(Major major) {
        em.persist(major);
    }

    public Major merge(Major major) {
        return em.merge(major);
    }

    public void deleteById(Integer majorId) {
        String jpql = "update Major m set m.status = false where m.id = :majorId";
        em.createQuery(jpql)
                .setParameter("majorId", majorId)
                .executeUpdate();
    }

    public Major findMajorById(Integer id) {
        return em.find(Major.class, id);
    }

    public List<Major> findAllMajors() {
        String jpql = "select m from Major m";
        return em.createQuery(jpql, Major.class).getResultList();
    }

    public List<Major> findAllActivateMajors() {
        String jpql = "select m from Major m where m.status = true";
        return em.createQuery(jpql, Major.class).getResultList();
    }

    public List<Major> findAllPastMajors() {
        String jpql = "select m from Major m where m.status = false";
        return em.createQuery(jpql, Major.class).getResultList();
    }

    public Major findMajorByName(String name) {
        String  jpql = "select m from Major m where m.name = :name and m.status = true";
        return em.createQuery(jpql, Major.class)
                .setParameter("name", name)
                .getSingleResult();
    }

}
