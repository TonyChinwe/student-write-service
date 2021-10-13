package com.phi.studentwriteservice.controller;


import com.phi.studentwriteservice.dto.Status;
import com.phi.studentwriteservice.dto.StatusResponseDto;
import com.phi.studentwriteservice.dto.Student;
import com.phi.studentwriteservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/write/api/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<Status> addStudent(@RequestBody Student student) {
        StatusResponseDto statusResponseDto=studentService.saveStudent(student);
        if(statusResponseDto.getStatus().getStatusCode()==400){
            return new ResponseEntity<>(statusResponseDto.getStatus(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(statusResponseDto.getStatus(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Status> updateStudent(@RequestBody Student student) {
        Status status=studentService.updateStudent(student);
        if(status.getStatusCode()==400){
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{studentId}")
    public ResponseEntity<Status> removeStudent(@PathVariable Long studentId) {
        Status status=studentService.removeStudent(studentId);
        if(status.getStatusCode()==404){
            return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }


}
