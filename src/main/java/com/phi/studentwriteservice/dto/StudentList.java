package com.phi.studentwriteservice.dto;

import java.util.List;

public class StudentList {

    private List<Student>studentList;

    public StudentList() {
    }

    public StudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
