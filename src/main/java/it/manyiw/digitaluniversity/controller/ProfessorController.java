package it.manyiw.digitaluniversity.controller;

import it.manyiw.digitaluniversity.domain.Professor;
import it.manyiw.digitaluniversity.domain.Professor;
import it.manyiw.digitaluniversity.repository.ProfessorRepository;
import it.manyiw.digitaluniversity.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private final ProfessorService professorService;
    private final ProfessorRepository professorRepository;

    public ProfessorController(@Autowired ProfessorService professorService, ProfessorRepository professorRepository) {
        this.professorService = professorService;
        this.professorRepository = professorRepository;
    }

    @GetMapping
    public List<Professor> findProfessors() {
        return professorService.findAllProfessors();
    }
    @GetMapping("/{professorId}")
    public Professor getProfessorById(@PathVariable Integer professorId) {
        return professorRepository.find(professorId);
    }
    @GetMapping(params = "name")
    public List<Professor> getProfessorByName(@RequestParam("name") String professorName) {
        String[] splitName = professorName.split(" ");
        String firstName = splitName[0];
        String lastName = "";
        if (splitName.length > 1) {
            lastName = splitName[1];
        }
        return professorService.findProfessorByName(firstName, lastName);
    }
    // TODO update method
    @PostMapping
    public Integer createProfessor(@Valid @RequestBody Professor professor) {
        return professorService.createProfessor(professor.getFirstName(), professor.getLastName());
    }
}
