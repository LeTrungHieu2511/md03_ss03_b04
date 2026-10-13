package com.example.session02.service;

import com.example.session02.model.Enrollment;
import com.example.session02.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseService = courseService;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment findEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.create(enrollment);
    }

    public Enrollment updateEnrollment(Long id, Enrollment enrollment) {
        return enrollmentRepository.update(id, enrollment);
    }

    public Enrollment deleteEnrollmentById(Long id) {
        return enrollmentRepository.deleteById(id);
    }

    // Kiểm tra khóa ngoại: khóa học có tồn tại không
    public boolean isCourseValid(Long courseId) {
        return courseService.existsById(courseId);
    }

    public String validateEnrollment(Enrollment enrollment) {
        if (enrollment.getStudentName() == null || enrollment.getStudentName().isBlank()) {
            return "studentName không được để trống";
        }
        if (!isCourseValid(enrollment.getCourseId())) {
            return "courseId không tồn tại";
        }
        return null;
    }
}