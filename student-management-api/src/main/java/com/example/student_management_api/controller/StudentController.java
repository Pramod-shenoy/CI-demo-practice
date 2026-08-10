package com.example.student_management_api.controller;

import com.example.student_management_api.exception.StudentNotFoundException;
import com.example.student_management_api.model.Student;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    public StudentController() {

        students.add(
                new Student(1, "abc", 21, "student1@gmail.com")
        );

        students.add(
                new Student(2, "bcb", 23, "student2@gmail.com")
        );

        students.add(
                new Student(3, "ffe", 22, "student3@gmail.com")
        );
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Student> createStudent(
            @Valid @RequestBody Student student) {

        students.add(student);

        return ResponseEntity
                .status(201)
                .body(student);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {

        return ResponseEntity.ok(students);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(
            @PathVariable int id) {

        Student student = findStudentById(id);

        return ResponseEntity.ok(student);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable int id,
            @Valid @RequestBody Student updatedStudent) {

        Student student = findStudentById(id);

        student.setName(updatedStudent.getName());
        student.setAge(updatedStudent.getAge());
        student.setEmail(updatedStudent.getEmail());

        return ResponseEntity.ok(student);
    }

    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(
            @PathVariable int id,
            @RequestBody Student updatedStudent) {

        Student student = findStudentById(id);

        if (updatedStudent.getName() != null) {
            student.setName(updatedStudent.getName());
        }

        if (updatedStudent.getAge() != 0) {
            student.setAge(updatedStudent.getAge());
        }

        if (updatedStudent.getEmail() != null) {
            student.setEmail(updatedStudent.getEmail());
        }

        return ResponseEntity.ok(student);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable int id) {

        Student student = findStudentById(id);

        students.remove(student);

        return ResponseEntity.noContent().build();
    }

    // FILTER BY AGE
    @GetMapping(params = "age")
    public ResponseEntity<List<Student>> filterByAge(
            @RequestParam int age) {

        List<Student> result = students.stream()
                .filter(student -> student.getAge() == age)
                .toList();

        return ResponseEntity.ok(result);
    }

    // SEARCH BY NAME
    @GetMapping(params = "name")
    public ResponseEntity<List<Student>> searchByName(
            @RequestParam String name) {

        List<Student> result = students.stream()
                .filter(student ->
                        student.getName()
                                .toLowerCase()
                                .contains(name.toLowerCase())
                )
                .toList();

        return ResponseEntity.ok(result);
    }

    // SORT
    @GetMapping(params = "sort")
    public ResponseEntity<List<Student>> sortStudents(
            @RequestParam String sort) {

        List<Student> result = new ArrayList<>(students);

        if (sort.equalsIgnoreCase("name")) {

            result.sort(
                    Comparator.comparing(Student::getName)
            );

        } else if (sort.equalsIgnoreCase("age")) {

            result.sort(
                    Comparator.comparing(Student::getAge)
            );
        }

        return ResponseEntity.ok(result);
    }

    // COMMON METHOD
    private Student findStudentById(int id) {

        return students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student with id " + id + " not found"
                        )
                );
    }
}