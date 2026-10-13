package com.example.session02.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    private Long id;
    private String studentName;
    private Long courseId; //khoa ngoai toi course
}