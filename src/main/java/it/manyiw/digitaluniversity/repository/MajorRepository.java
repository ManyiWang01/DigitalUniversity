package it.manyiw.digitaluniversity.repository;

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

    public Integer save(Major major) {
        em.persist(major);
        return major.getId();
    }

    public void delete(Integer majorId) {
        Major major = em.find(Major.class, majorId);
        if (major != null) {
            em.remove(major);
        }
    }

    public Major findMajorById(Integer id) {
        return em.find(Major.class, id);
    }

    public List<Major> findAllOpenMajors() {
        String jpql = "select m from Major m";
        return em.createQuery(jpql, Major.class).getResultList();
    }

    public List<Major> findAllPastMajors() {
        String sql = "select * from majors where deleted = true";
        return em.createNativeQuery(sql, Major.class).getResultList();
    }

    public Major findPastMajorById(Integer majorId) {
        String sql = "select * from majors where major_id = ? and deleted = true";
        return (Major) em.createNativeQuery(sql, Major.class)
                .setParameter(1, majorId)
                .getSingleResult();
    }

    public Major findMajorByName(String name) {
        String  jpql = "select m from Major m where m.name = :name";
        return em.createQuery(jpql, Major.class)
                .setParameter("name", name)
                .getSingleResult();
    }

}
