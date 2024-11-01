package com.swnur.jee_crud_app.dao;

import com.swnur.jee_crud_app.exception.DataAccessException;
import com.swnur.jee_crud_app.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDAOImpl.class);
    private final DataSource dataSource;

    public StudentDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean addStudent(Student student) {
        String sqlQuery = "INSERT INTO student(first_name, last_name, email, address) VALUES(?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {


            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(4, student.getAddress());

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("Database error in addStudent() for student: {}", student.getInitials(), e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public boolean deleteStudent(int studentId) {
        String sqlQuery = "DELETE FROM student WHERE id=?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setInt(1, studentId);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("Database error in deleteStudent with id: {}", studentId, e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public boolean updateStudent(Student student) {
        String sqlQuery = "UPDATE student\n" +
                "SET first_name=?,\n" +
                "    last_name=?,\n" +
                "    email=?,\n" +
                "    address=?\n" +
                "WHERE id=?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(4, student.getAddress());
            preparedStatement.setInt(5, student.getId());

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("Database error in updateStudent: {}", student, e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public Student getStudent(int studentId) {
        String sqlQuery = "SELECT * FROM student WHERE id=?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, studentId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                LOGGER.warn("No student found with id: {}", studentId);
                return null;
            }

            Student student = new Student();
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            student.setEmail(resultSet.getString("email"));
            student.setAddress(resultSet.getString("address"));

            return student;
        } catch (SQLException e) {
            LOGGER.error("Database error getStudent with id {}", studentId, e);
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        String sqlQuery = "SELECT * FROM student";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            if (!resultSet.next()) {
                LOGGER.warn("No students found for getAllStudents()");
            } else {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    String address = resultSet.getString("address");

                    Student student = new Student(id, firstName, lastName, email, address);
                    students.add(student);
                }
            }

            return students;
        } catch (SQLException e) {
            LOGGER.error("Database error in getAllStudents", e);
            throw new DataAccessException(e.getMessage());
        }

    }
}
