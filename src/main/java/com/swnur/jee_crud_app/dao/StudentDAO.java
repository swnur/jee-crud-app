package com.swnur.jee_crud_app.dao;

import com.swnur.jee_crud_app.exception.DataAccessException;
import com.swnur.jee_crud_app.model.Student;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {

    boolean addStudent(Student student) throws DataAccessException;

    boolean deleteStudent(int studentId) throws DataAccessException;

    boolean updateStudent(Student student) throws DataAccessException;

    Student getStudent(int studentId) throws DataAccessException;

    List<Student> getAllStudents() throws DataAccessException;


}
