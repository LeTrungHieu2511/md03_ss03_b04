package com.example.session02.repository;

import com.example.session02.model.Instructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InstructorRepository {

    private final List<Instructor> instructors = new ArrayList<>();

    public InstructorRepository() {
        instructors.add(new Instructor(1L, "Nguyễn Văn An", "an@example.com"));
        instructors.add(new Instructor(2L, "Trần Thị Bình", "binh@example.com"));
    }

    public List<Instructor> findAll() {
        return instructors;
    }

    public Optional<Instructor> findById(Long id) {
        return instructors.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    public Instructor create(Instructor instructor) {
        long newId = instructors.stream()
                .mapToLong(Instructor::getId)
                .max()
                .orElse(0L) + 1;
        instructor.setId(newId);
        instructors.add(instructor);
        return instructor;
    }

    public Instructor update(Long id, Instructor updated) {
        Instructor existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        return existing;
    }

    public Instructor deleteById(Long id) {
        Instructor existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
        instructors.remove(existing);
        return existing;
    }
}