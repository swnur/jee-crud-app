package com.swnur.jee_crud_app.service;

import com.swnur.jee_crud_app.dao.StudentDAO;
import com.swnur.jee_crud_app.dao.StudentDAOImpl;
import com.swnur.jee_crud_app.exception.DataAccessException;
import com.swnur.jee_crud_app.model.Student;

import javax.sql.DataSource;
import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO;

    public StudentService(DataSource dataSource) {
        this.studentDAO = new StudentDAOImpl(dataSource);
    }

    public boolean addStudent(Student student) {
        validateStudent(student);

        try {
            return studentDAO.addStudent(student);
        } catch (DataAccessException e) {
            // Log exception or handle accordingly, e.g., rethrow or return a default response
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStudent(Student student) {
        validateStudent(student);

        try {
            return studentDAO.updateStudent(student);
        } catch (DataAccessException e) {
            // Log or handle exception
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudent(int studentId) {
        try {
            return studentDAO.deleteStudent(studentId);
        } catch (DataAccessException e) {
            // Log or handle exception
            e.printStackTrace();
            return false;
        }
    }

    public Student getStudent(int studentId) {
        try {
            return studentDAO.getStudent(studentId);
        } catch (DataAccessException e) {
            // Log or handle exception
            e.printStackTrace();
            return null;  // Or handle null values appropriately
        }
    }

    public List<Student> getAllStudents() {
        try {
            return studentDAO.getAllStudents();
        } catch (DataAccessException e) {
            // Log or handle exception
            e.printStackTrace();
            return List.of();
        }
    }

    private void validateStudent(Student student) {
        if (student.getFirstName() == null || student.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (student.getLastName() == null || student.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (student.getAddress() == null || student.getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
    }

}
