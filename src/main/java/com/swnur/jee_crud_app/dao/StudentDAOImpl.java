package com.swnur.jee_crud_app.dao;

import com.swnur.jee_crud_app.exception.DataAccessException;
import com.swnur.jee_crud_app.model.Student;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            Student student = new Student();

            while (resultSet.next()) {
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setEmail(resultSet.getString("email"));
                student.setAddress(resultSet.getString("address"));
            }

            return student;
        } catch (SQLException e) {
            e.printStackTrace();
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

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");

                Student student = new Student(id, firstName, lastName, email, address);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e.getMessage());
        }

        return students;
    }
}
