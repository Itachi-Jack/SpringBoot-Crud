package com.example.myfirstcrud.Services;

import com.example.myfirstcrud.DTO.StudentDto;
import com.example.myfirstcrud.Entity.StudentEntity;
import com.example.myfirstcrud.Repository.StudentRepository;
import com.example.myfirstcrud.ResourceNotFoundException;


import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public StudentService(StudentRepository studentRepository,ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    public StudentDto getStudentById(Long id) {
        StudentEntity stu = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
        return modelMapper.map(stu, StudentDto.class);


    }

    public StudentDto addstudent(StudentDto inputstudent) {
        StudentEntity stud = modelMapper.map(inputstudent,StudentEntity.class);
        StudentEntity stu = studentRepository.save(stud);
        return modelMapper.map(stu, StudentDto.class);

    }

    public List<StudentDto> getAllStudents() {
        List<StudentEntity> list = studentRepository.findAll();
        return list.stream().map(s -> modelMapper.map(s,StudentDto.class)).collect(Collectors.toList());
    }

    public boolean deleteStudentById(Long id) {
        Boolean exist = studentRepository.existsById(id);
        if(exist){
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public StudentDto updateStudentPartially(Long id, Map<String, Object> updatemap) {
            boolean exist = studentRepository.existsById(id);
            if(!exist){
                return null;
            }
            StudentEntity stu = studentRepository.findById(id).get();
            updatemap.forEach( (k,v) -> {
                Field field = ReflectionUtils.findField(StudentEntity.class, k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, stu, v);
            });

            return modelMapper.map(studentRepository.save(stu), StudentDto.class);

    }

    public StudentDto updateStudent(Long id, StudentDto dto) {
        StudentEntity existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
        StudentEntity stu = modelMapper.map(dto,StudentEntity.class);
        stu.setId(existing.getId());
        StudentEntity saved = studentRepository.save(stu);
        return modelMapper.map(saved, StudentDto.class);
    }
}

