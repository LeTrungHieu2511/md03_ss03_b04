package com.example.session02.controller;

import com.example.session02.dto.ApiResponse;
import com.example.session02.model.Enrollment;
import com.example.session02.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Enrollment>>> getAll() {
        List<Enrollment> list = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách đăng ký thành công", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Enrollment>> getById(@PathVariable Long id) {
        try {
            Enrollment enrollment = enrollmentService.findEnrollmentById(id);
            return ResponseEntity.ok(ApiResponse.success("Lấy lượt đăng ký thành công", enrollment));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Enrollment>> create(@RequestBody Enrollment enrollment) {
        String error = enrollmentService.validateEnrollment(enrollment);
        if (error != null) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(error));
        }
        Enrollment created = enrollmentService.createEnrollment(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo lượt đăng ký thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Enrollment>> update(@PathVariable Long id,
                                                          @RequestBody Enrollment enrollment) {
        try {
            enrollmentService.findEnrollmentById(id); // không có id thì ném ngoại lệ -> 404

            String error = enrollmentService.validateEnrollment(enrollment);
            if (error != null) {
                return ResponseEntity.badRequest().body(ApiResponse.fail(error));
            }
            Enrollment updated = enrollmentService.updateEnrollment(id, enrollment);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật lượt đăng ký thành công", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Enrollment>> delete(@PathVariable Long id) {
        try {
            Enrollment deleted = enrollmentService.deleteEnrollmentById(id);
            return ResponseEntity.ok(ApiResponse.success("Xóa lượt đăng ký thành công", deleted));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }
}