package com.example.session02.service;

import com.example.session02.model.Instructor;
import com.example.session02.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor findInstructorById(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
    }

    public boolean existsById(Long id) {
        return id != null && instructorRepository.findById(id).isPresent();
    }

    public Instructor createInstructor(Instructor instructor) {
        return instructorRepository.create(instructor);
    }

    public Instructor updateInstructor(Long id, Instructor instructor) {
        return instructorRepository.update(id, instructor);
    }

    public Instructor deleteInstructorById(Long id) {
        return instructorRepository.deleteById(id);
    }

    public String validateInstructor(Instructor instructor) {
        if (instructor.getName() == null || instructor.getName().isBlank()) {
            return "name không được để trống";
        }
        if (instructor.getEmail() == null || !instructor.getEmail().contains("@")) {
            return "email không hợp lệ";
        }
        return null;
    }
}