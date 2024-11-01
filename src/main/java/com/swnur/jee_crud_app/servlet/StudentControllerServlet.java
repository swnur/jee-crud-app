package com.swnur.jee_crud_app.servlet;

import com.swnur.jee_crud_app.exception.DataAccessException;
import com.swnur.jee_crud_app.model.Student;
import com.swnur.jee_crud_app.service.StudentService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/student")
public class StudentControllerServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentControllerServlet.class);
    private StudentService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            LOGGER.debug("Initializing StudentControllerServlet and setting up DataSource.");
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/web_student_tracker");

            service = new StudentService(dataSource);
            LOGGER.info("StudentService initialized successfully.");
        } catch (NamingException e) {
            LOGGER.error("Failed to initialize DataSource in StudentControllerServlet.", e);
            throw new ServletException("Failed to retrieve DataSource", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = Optional.ofNullable(req.getParameter("command")).orElse("LIST");
        LOGGER.debug("Processing GET request with command: {}", command);

        try {
            switch (command) {
                case "ADD" -> addStudent(req, resp);
                case "DELETE" -> deleteStudent(req, resp);
                case "UPDATE" -> updateStudent(req, resp);
                case "LIST" -> listStudents(req, resp);
                case "LOAD" -> loadStudent(req, resp);
                default -> listStudents(req, resp);
            }
        } catch (Exception e) {
            LOGGER.error("Exception occurred while processing command: {}", command, e);
            handleException(req, resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.debug("Processing POST request.");
        doGet(req, resp);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Student newStudent = extractStudentFromRequest(request);
        service.addStudent(newStudent);

        redirectToList(response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        service.deleteStudent(studentId);

        redirectToList(response);
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Student updatedStudent = extractStudentFromRequest(request);
        updatedStudent.setId(Integer.parseInt(request.getParameter("studentId")));

        service.updateStudent(updatedStudent);

        redirectToList(response);
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = service.getAllStudents();

        request.setAttribute("studentList", students);

        forwardToPage(request, response, "/list-students.jsp");
    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        Student currentStudent = service.getStudent(studentId);

        request.setAttribute("studentData", currentStudent);
        forwardToPage(request, response, "/WEB-INF/JSP/update-student-form.jsp");
    }

    private Student extractStudentFromRequest(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        return new Student(firstName, lastName, email, address);
    }

    private void redirectToList(HttpServletResponse response) throws IOException {
        LOGGER.debug("Redirecting to student list.");
        response.sendRedirect("student?command=LIST");
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        LOGGER.debug("Forwarding to page: {}", page);
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
        LOGGER.error("An error occurred: {}", e.getMessage(), e);
        request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        forwardToPage(request, response, "/WEB-INF/JSP/error.jsp");
    }
}
