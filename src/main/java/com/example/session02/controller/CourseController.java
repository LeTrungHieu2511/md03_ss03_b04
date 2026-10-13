package com.example.session02.controller;

import com.example.session02.dto.ApiResponse;
import com.example.session02.model.Course;
import com.example.session02.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getById(@PathVariable Long id) {
        try {
            Course course = courseService.findCourseById(id);
            return ResponseEntity.ok(ApiResponse.success("Lấy khóa học thành công", course));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> update(@PathVariable Long id,
                                                      @RequestBody Course course) {
        try {
            courseService.findCourseById(id); // không có id thì ném ngoại lệ -> 404

            String error = courseService.validateCourse(course);
            if (error != null) {
                return ResponseEntity.badRequest().body(ApiResponse.fail(error));
            }
            Course updated = courseService.updateCourse(id, course);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật khóa học thành công", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> delete(@PathVariable Long id) {
        try {
            Course deleted = courseService.deleteCourseById(id);
            return ResponseEntity.ok(ApiResponse.success("Xóa khóa học thành công", deleted));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }
}