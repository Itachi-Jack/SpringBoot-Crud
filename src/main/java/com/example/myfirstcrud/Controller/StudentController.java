package com.example.myfirstcrud.Controller;

import com.example.myfirstcrud.DTO.StudentDto;
import com.example.myfirstcrud.Services.StudentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {
    private StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;

    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        StudentDto studentDto = studentService.getStudentById(id);
        return ResponseEntity.ok(studentDto);
    }
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> studentDtos = studentService.getAllStudents();
        return ResponseEntity.ok(studentDtos);

    }

    @PostMapping
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto studentDto) {
        StudentDto dto = studentService.addstudent(studentDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStudentById(@PathVariable Long id) {
        boolean gotdeleted =  studentService.deleteStudentById(id);
        if(gotdeleted){
            return new ResponseEntity<>(HttpStatus.OK);

        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudentById(@PathVariable Long id, @RequestBody Map<String, Object> map) {
        return ResponseEntity.ok( studentService.updateStudentPartially(id , map) );

    }

    @PutMapping("/{id}")
    public StudentDto updateStudent(@PathVariable Long id, @RequestBody StudentDto dto) {
        return studentService.updateStudent(id , dto);

    }



}
