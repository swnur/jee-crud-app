package com.swnur.jee_crud_app.service;

import com.swnur.jee_crud_app.dao.StudentDAO;
import com.swnur.jee_crud_app.dao.StudentDAOImpl;
import com.swnur.jee_crud_app.exception.DataAccessException;
import com.swnur.jee_crud_app.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;

public class StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);
    private final StudentDAO studentDAO;

    public StudentService(DataSource dataSource) {
        this.studentDAO = new StudentDAOImpl(dataSource);
    }

    public void addStudent(Student student) {
        LOGGER.debug("Starting addStudent with student: {}", student.getInitials());
        validateStudent(student);

        try {
            boolean result = studentDAO.addStudent(student);

            if (result) {
                LOGGER.info("Successfully added student: {}", student.getInitials());
            } else {
                LOGGER.warn("No student was added to database for student: {}", student.getInitials());
            }
        } catch (DataAccessException e) {
            LOGGER.error("Error in addStudent()", e);
            throw e;
        }
    }

    public void deleteStudent(int studentId) {
        LOGGER.debug("Starting deleteStudent with studentId {}", studentId);

        try {
            boolean result = studentDAO.deleteStudent(studentId);

            if (result) {
                LOGGER.info("Successfully deleted student with id: {}", studentId);
            } else {
                LOGGER.warn("No student with id {} was deleted from database", studentId);
            }
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException was caught in deleteStudent().", e);
            throw e;
        }
    }

    public void updateStudent(Student student) {
        LOGGER.debug("Starting updateStudent with student {}", student);
        validateStudent(student);

        try {
            boolean result = studentDAO.updateStudent(student);

            if (result) {
                LOGGER.info("Successfully updated student with id: {}", student.getId());
            } else {
                LOGGER.warn("No student was updated with id: {}", student.getId());
            }
        } catch (DataAccessException e) {
            LOGGER.error("Exception was caught in updateStudent()", e);
            throw e;
        }
    }

    public Student getStudent(int studentId) {
        LOGGER.debug("Starting getStudent with studentId {}", studentId);

        try {
            Student student = studentDAO.getStudent(studentId);

            if (student != null) {
                LOGGER.info("Successfully retrieved student with id: {}", studentId);
            } else {
                LOGGER.warn("No student was found in database with id: {}", studentId);
            }

            return student;
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException was caught in getStudent()");
            return null;
        }
    }

    public List<Student> getAllStudents() {
        LOGGER.debug("Starting getAllStudents()");

        try {
            List<Student> students = studentDAO.getAllStudents();

            if (students.isEmpty()) {
                LOGGER.warn("Students list is empty");
            } else {
                LOGGER.info("getAllStudents() retrieved with list size of {}", students.size());
            }

            return students;
        } catch (DataAccessException e) {
            LOGGER.error("DataAccessException was caught in getAllStudents()", e);
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
