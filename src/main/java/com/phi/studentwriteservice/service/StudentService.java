package com.phi.studentwriteservice.service;
import com.phi.studentwriteservice.dto.Status;
import com.phi.studentwriteservice.dto.Student;
import com.phi.studentwriteservice.dto.StatusResponseDto;
import com.phi.studentwriteservice.dto.StudentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StudentService {

    @Autowired
    private RestTemplate restTemplate;
    private static final String baseUrl="http://STUDENT-REPO-SERVICE/api/v1/student";

    public StatusResponseDto saveStudent(Student student){
      Status status=  validateStudentData(student);
      if(status.getStatusCode()== HttpStatus.OK.value()) {
          HttpEntity<Student> request = new HttpEntity<>(student);
          return restTemplate.postForObject(baseUrl, request, StatusResponseDto.class);
      }else {
          StatusResponseDto statusResponseDto=new StatusResponseDto();
          statusResponseDto.setStatus(status);
          return statusResponseDto;
      }
    }

    public Student findStudentById(Long studentId){
        StudentResponseDto responseDto=restTemplate.getForObject(baseUrl+"/id/"+studentId, StudentResponseDto.class);
        return responseDto.getStudent();
    }

    public Status updateStudent(Student student){
        Student studentToUpdate=findStudentById(student.getId());
        if(studentToUpdate !=null){
            String studentName=studentToUpdate.getName();
            Long studentNumber=studentToUpdate.getRegNumber();
            studentToUpdate.setName(student.getName()==null||student.getName().isEmpty()?studentName:student.getName());
            studentToUpdate.setRegNumber(student.getRegNumber()==null?studentNumber:student.getRegNumber());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<Student> request = new HttpEntity<>(studentToUpdate,headers);
            restTemplate.put(baseUrl, request, new Object[] {});
            return new Status(200,"Student Updated Successfully");
        }
        return new Status(400,"Student with the id does not exist");
    }


    public Status removeStudent(Long studentId) {
        Student studentToRemove=findStudentById(studentId);
        if(studentToRemove ==null){
            return new Status(404,"The Request Failed. The Student Do Not Exist ");
        }

        restTemplate.delete(baseUrl+"/remove/{id}",Long.toString(studentId));
        return new Status(201,"Student Was Successfully Removed");
    }

    private Status validateStudentData(Student student){
        if(student.getName()==null||student.getName().isEmpty()){
            return new Status(400,"Missing Student's Name ");
        }
        if(student.getRegNumber()==null||student.getRegNumber()<0){
            return new Status(400,"Missing or Bad Student's Registration Number");
        }
        return new Status(200,"Student Saved Successfully");
    }




}
