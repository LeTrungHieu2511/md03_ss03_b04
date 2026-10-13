package com.example.session02.repository;

import com.example.session02.model.Enrollment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EnrollmentRepository {

    private final List<Enrollment> enrollments = new ArrayList<>();

    public EnrollmentRepository() {
        enrollments.add(new Enrollment(1L, "Lê Minh Cường", 1L));
        enrollments.add(new Enrollment(2L, "Phạm Thu Dung", 1L));
        enrollments.add(new Enrollment(3L, "Hoàng Gia Huy", 2L));
    }

    public List<Enrollment> findAll() {
        return enrollments;
    }

    public Optional<Enrollment> findById(Long id) {
        return enrollments.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public Enrollment create(Enrollment enrollment) {
        long newId = enrollments.stream()
                .mapToLong(Enrollment::getId)
                .max()
                .orElse(0L) + 1;
        enrollment.setId(newId);
        enrollments.add(enrollment);
        return enrollment;
    }

    public Enrollment update(Long id, Enrollment updated) {
        Enrollment existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        existing.setStudentName(updated.getStudentName());
        existing.setCourseId(updated.getCourseId());
        return existing;
    }

    public Enrollment deleteById(Long id) {
        Enrollment existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        enrollments.remove(existing);
        return existing;
    }
}