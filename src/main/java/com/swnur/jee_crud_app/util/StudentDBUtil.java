package com.swnur.jee_crud_app.util;

import com.swnur.jee_crud_app.model.Student;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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

            int statementResult = preparedStatement.executeUpdate();
            return statementResult == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
