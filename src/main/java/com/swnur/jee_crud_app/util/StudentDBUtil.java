package com.swnur.jee_crud_app.util;

import com.swnur.jee_crud_app.model.Student;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDBUtil {
    private final DataSource dataSource;

    public StudentDBUtil(DataSource theDataSource) {
        dataSource = theDataSource;
    }

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    public boolean addStudent(Student student) {
        String sqlQuery = "INSERT INTO student(first_name, last_name, email, address) VALUES(?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {


            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getEmail());
            preparedStatement.setString(4, student.getAddress());

            return preparedStatement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Student getStudentById(int id) {
        String sqlQuery = "SELECT * FROM student WHERE id=?;";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Student student = new Student();

            while (resultSet.next()) {
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setEmail(resultSet.getString("email"));
                student.setAddress(resultSet.getString("address"));
            }

            return student;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
            return false;
        }
    }

    public boolean deleteStudent(int studentId) {
        String sqlQuery = "DELETE FROM student WHERE id=?;";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setInt(1, studentId);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
