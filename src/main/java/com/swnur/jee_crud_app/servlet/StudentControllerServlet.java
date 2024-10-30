package com.swnur.jee_crud_app.servlet;

import com.swnur.jee_crud_app.model.Student;
import com.swnur.jee_crud_app.util.StudentDBUtil;
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
import java.util.List;

@WebServlet("/student")
public class StudentControllerServlet extends HttpServlet {
    private StudentDBUtil studentDBUtil;
    private DataSource dataSource;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/web_student_tracker");

            studentDBUtil = new StudentDBUtil(dataSource);
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ServletException("Failed to retrieve DataSource", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String command = req.getParameter("command");

            if (command == null) {
                command = "LIST";
            }

            switch(command) {
                case "ADD":
                    addStudent(req, resp);
                    break;
                case "DELETE":
                    break;
                case "UPDATE":
                    updateStudent(req, resp);
                    break;
                case "LIST":
                    listStudents(req, resp);
                    break;
                case "LOAD":
                    loadStudent(req, resp);
                    break;
                default:
                    listStudents(req, resp);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = studentDBUtil.getAllStudents();

        request.setAttribute("studentList", students);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/list-students.jsp");
        requestDispatcher.forward(request, response);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        Student newStudent = new Student(firstName, lastName, email, address);

        boolean result = studentDBUtil.addStudent(newStudent);

        System.out.println("Add student result -> " + result);
        listStudents(request, response);
    }

    private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. Read student id from form data
        int studentId = Integer.parseInt(request.getParameter("studentId"));

        // 2. get student from database (db util)
        Student currentStudent = studentDBUtil.getStudentById(studentId);

        // 3. place student in the request attribute
        request.setAttribute("studentData", currentStudent);
        // 4. send to jsp page: update-student-form.jsp
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("update-student-form.jsp");
        requestDispatcher.forward(request, response);
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("studentId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        Student tempStudent = new Student(id, firstName, lastName, email, address);
        System.out.println("updateStudent() function, student data from request\n" + tempStudent);

        boolean result = studentDBUtil.updateStudent(tempStudent);

        System.out.println("Update student result -> " + result);
        listStudents(request, response);
    }
}
