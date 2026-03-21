package it.manyiw.digitaluniversity.controller;

import it.manyiw.digitaluniversity.domain.Major;
import it.manyiw.digitaluniversity.service.MajorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/majors")
public class MajorController {
    private final MajorService majorService;

    public MajorController(@Autowired MajorService majorService) {
        this.majorService = majorService;
    }

    @GetMapping(value = {"", "/open"})
    public List<Major> getAllOpenedMajor() {
        return majorService.findAllOpenMajors();
    }
    @GetMapping("/all")
    public List<Major> getAllMajor() {
        return majorService.findAllMajors();
    }
    @GetMapping("/{majorId}")
    public Major getMajorById(@PathVariable Integer majorId) {
        return majorService.findMajorById(majorId);
    }
    @GetMapping("/past")
    public List<Major> getAllClosedMajor() {
        return majorService.findAllClosedMajors();
    }
    @GetMapping(params = "name")
    public Major getMajorByName(@RequestParam("name") String majorName) {
        return majorService.findMajorByName(majorName);
    }

    @DeleteMapping("/{majorId}")
    public void deleteMajor(@PathVariable Integer majorId) {
        majorService.deleteMajor(majorId);
    }
    // TODO update method
//    @PutMapping("/{majorId}")
//    public Integer updateMajor(@PathVariable Integer majorId) {
//        return majorService.updateMajor(majorId);
//    }
    // TODO change to input DTO and return a responseEntity
    @PostMapping
    public Integer createMajor(@Valid @RequestBody Major major) {
        return majorService.createMajor(major.getName());
    }



}
