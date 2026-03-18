package it.manyiw.digitaluniversity.service;

import it.manyiw.digitaluniversity.domain.Major;
import it.manyiw.digitaluniversity.repository.MajorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MajorService {
    // TODO business logic for majors, eg. createNewMajor, closeMajor, findAllMajor

    private MajorRepository majorRepository;

    public MajorService(@Autowired MajorRepository majorRepository) {
        this.majorRepository = majorRepository;
    }

    public Integer createMajor(String name) {
        Major major = majorRepository.findMajorByName(name);
        if (major != null) {
            throw new IllegalArgumentException("Major with name " + name + " already exists");
        }
        major = new Major();
        major.setName(name);
        return majorRepository.save(major);
    }

    public void deleteMajor(Integer majorId) {
        majorRepository.delete(majorId);
    }

    public List<Major> findAllOpenMajors() {
        return majorRepository.findAllOpenMajors();
    }

    public List<Major> findAllPastMajors() {
        return majorRepository.findAllPastMajors();
    }

    public Integer reopenMajor(Integer majorId) {
        Major major = majorRepository.findPastMajorById(majorId);
        if (major == null) {
            throw new IllegalArgumentException("Major with id " + majorId + " does not exist");
        }
        major.reopen();
        return major.getId();
    }

}
