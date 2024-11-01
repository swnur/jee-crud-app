# JEE CRUD Application

This Java EE web application demonstrates CRUD operations (Create, Read, Update, Delete) using Servlets, JSP, and a MySQL database. Built with the Model-View-Controller (MVC) pattern, it manages basic student records and serves as a practical exercise in Java EE fundamentals.

## Features

- **Add a Student**: Create a new student record with fields for first name, last name, email, and address.
- **View All Students**: Display a list of all students in the database.
- **Edit Student Details**: Update the details of an existing student.
- **Delete a Student**: Remove a student from the database.

## Technologies Used

- **Servlets**: For backend processing of HTTP requests
- **JSP**: For rendering front-end views
- **MySQL**: Database used to store student records
- **JDBC**: For connecting to and interacting with the MySQL database
- **SLF4J + Logback**: Logging framework for tracking application events

## Project Structure and MVC Architecture

This project follows the **Model-View-Controller (MVC)** design pattern:

- **Model**: The `Student` class represents the application data (student details). The `DataAccessException` is a custom error class for database issues.

- **View**: The **JSP files** (e.g., `list-students.jsp`, `update-student-form.jsp`) render the user interface, displaying student data and handling form input.

- **Controller**: `StudentControllerServlet` processes user requests (add, update, delete, etc.), calls the service layer to manage data, and selects the view to display.

- **Service and DAO**: `StudentService` manages business logic and interacts with the `StudentDAO` interface and `StudentDAOImpl` class, which handle database operations.

