package com.example.session02.repository;

import com.example.session02.model.Course;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {

    private final List<Course> courses = new ArrayList<>();

    public CourseRepository() {
        courses.add(new Course(1L, "Lập trình Java cơ bản", "open", 1L));
        courses.add(new Course(2L, "Spring Boot nhập môn", "open", 1L));
        courses.add(new Course(3L, "Cơ sở dữ liệu", "closed", 2L));
    }

    public List<Course> findAll() {
        return courses;
    }

    public Optional<Course> findById(Long id) {
        return courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public Course create(Course course) {
        long newId = courses.stream()
                .mapToLong(Course::getId)
                .max()
                .orElse(0L) + 1;
        course.setId(newId);
        courses.add(course);
        return course;
    }

    public Course update(Long id, Course updated) {
        Course existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        existing.setTitle(updated.getTitle());
        existing.setStatus(updated.getStatus());
        existing.setInstructorId(updated.getInstructorId());
        return existing;
    }

    public Course deleteById(Long id) {
        Course existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        courses.remove(existing);
        return existing;
    }
}