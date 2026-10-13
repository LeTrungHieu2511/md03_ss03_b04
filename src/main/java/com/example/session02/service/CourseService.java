package com.example.session02.service;

import com.example.session02.model.Course;
import com.example.session02.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorService instructorService;

    @Autowired
    public CourseService(CourseRepository courseRepository, InstructorService instructorService) {
        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course createCourse(Course course) {
        return courseRepository.create(course);
    }

    // Dùng cho việc kiểm tra khóa ngoại, trả boolean nên không phải bắt ngoại lệ
    public boolean existsById(Long id) {
        return id != null && courseRepository.findById(id).isPresent();
    }

    public Course updateCourse(Long id, Course course) {
        return courseRepository.update(id, course);
    }

    public Course deleteCourseById(Long id) {
        return courseRepository.deleteById(id);
    }

    //kiem tra khoa ngoai giang vien co ton tai khong
    public boolean isInstructorValid(Long instructorId) {
        return instructorService.existsById(instructorId);
    }

    public String validateCourse(Course course) {
        if (course.getTitle() == null || course.getTitle().isBlank()) {
            return "title không được để trống";
        }
        if (!isInstructorValid(course.getInstructorId())) {
            return "instructorId không tồn tại";
        }
        return null;
    }
}