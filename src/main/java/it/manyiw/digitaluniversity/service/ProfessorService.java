package it.manyiw.digitaluniversity.service;

import it.manyiw.digitaluniversity.domain.Professor;
import it.manyiw.digitaluniversity.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public ProfessorService(@Autowired ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Integer createProfessor(String firstName, String lastName) {
        Professor professor = new Professor();
        professor.setFirstName(firstName);
        professor.setLastName(lastName);
        return professorRepository.save(professor);
    }

    public Professor findProfessor(Integer professorId) {
        return professorRepository.find(professorId);
    }

    private String generateUniqueInstitutionalEmail(String firstName, String lastName) {
        String baseMail = firstName + "." + lastName;
        String domain = "@digitaluniversity.com";
        int count = 1;
        String uniqueMail = baseMail + domain;
        while (professorRepository.existsByInstitutionalMail(uniqueMail)) {
            uniqueMail = baseMail + count + domain;
            count++;
        }
        return uniqueMail;
    }

    public List<Professor> findAllProfessors() {
        return professorRepository.findAll();
    }

    public Professor findProfessorByInstitutionalMail(String institutionalMail) {
        return professorRepository.findByInstitutionalMail(institutionalMail);
    }

    public List<Professor> findProfessorByName(String firstName, String lastName) {
        return professorRepository.findByName(firstName, lastName);
    }

}
