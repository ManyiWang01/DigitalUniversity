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
    private final MajorRepository majorRepository;

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

    public Major findMajorByName(String name) {
        return majorRepository.findMajorByName(name);
    }
    public Major findMajorById(Integer majorId) {
        return majorRepository.findMajorById(majorId);
    }

    public void deleteMajor(Integer majorId) {
        majorRepository.delete(majorId);
    }

    public List<Major> findAllOpenMajors() {
        return majorRepository.findAllOpenMajors();
    }
    public List<Major> findAllMajors() {
        return majorRepository.findAllMajors();
    }
    public List<Major> findAllClosedMajors() {
        return majorRepository.findAllClosedMajors();
    }

    // TODO update method
//    public Integer updateMajor(Major major) {
//
//    }

}
