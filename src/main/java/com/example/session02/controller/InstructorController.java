package com.example.session02.controller;

import com.example.session02.dto.ApiResponse;
import com.example.session02.model.Instructor;
import com.example.session02.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Instructor>>> getAll() {
        List<Instructor> list = instructorService.getAllInstructors();
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách giảng viên thành công", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Instructor>> getById(@PathVariable Long id) {
        try {
            Instructor instructor = instructorService.findInstructorById(id);
            return ResponseEntity.ok(ApiResponse.success("Lấy giảng viên thành công", instructor));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Instructor>> create(@RequestBody Instructor instructor) {
        String error = instructorService.validateInstructor(instructor);
        if (error != null) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(error));
        }
        Instructor created = instructorService.createInstructor(instructor);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo giảng viên thành công", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Instructor>> update(@PathVariable Long id,
                                                          @RequestBody Instructor instructor) {
        try {
            instructorService.findInstructorById(id); // không có id thì ném ngoại lệ -> 404

            String error = instructorService.validateInstructor(instructor);
            if (error != null) {
                return ResponseEntity.badRequest().body(ApiResponse.fail(error));
            }
            Instructor updated = instructorService.updateInstructor(id, instructor);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật giảng viên thành công", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Instructor>> delete(@PathVariable Long id) {
        try {
            Instructor deleted = instructorService.deleteInstructorById(id);
            return ResponseEntity.ok(ApiResponse.success("Xóa giảng viên thành công", deleted));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail(e.getMessage()));
        }
    }
}