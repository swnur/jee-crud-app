package com.swnur.jee_crud_app.servlet;

import com.swnur.jee_crud_app.model.Student;
import com.swnur.jee_crud_app.service.StudentService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/student")
public class StudentControllerServlet extends HttpServlet {
    private StudentService service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/web_student_tracker");

            service = new StudentService(dataSource);
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ServletException("Failed to retrieve DataSource", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = Optional.ofNullable(req.getParameter("command")).orElse("LIST");

        try {
            switch(command) {
                case "ADD" -> addStudent(req, resp);
                case "DELETE" -> deleteStudent(req, resp);
                case "UPDATE" -> updateStudent(req, resp);
                case "LIST" -> listStudents(req, resp);
                case "LOAD" -> loadStudent(req, resp);
                default -> listStudents(req, resp);
            }
        } catch (Exception e) {
            handleException(req, resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Student newStudent = extractStudentFromRequest(request);
        boolean result = service.addStudent(newStudent);

        // TODO: Log the status
        redirectToList(response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        boolean result = service.deleteStudent(studentId);

        System.out.println("Delete student result -> " + result);
        redirectToList(response);
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        Student updatedStudent = extractStudentFromRequest(request);
        updatedStudent.setId(Integer.parseInt(request.getParameter("studentId")));

        System.out.println("updateStudent() function, student data from request\n" + updatedStudent);

        boolean result = service.updateStudent(updatedStudent);
        System.out.println("Update student result -> " + result);

        redirectToList(response);
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Student> students = service.getAllStudents();

        request.setAttribute("studentList", students);

        forwardToPage(request, response, "/list-students.jsp");
    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
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
        response.sendRedirect("student?command=LIST");
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
        e.printStackTrace();
        request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        forwardToPage(request, response, "/WEB-INF/error.jsp");
    }
}
