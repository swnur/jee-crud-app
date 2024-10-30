<%--
  Created by IntelliJ IDEA.
  User: initnur
  Date: 29/10/2024
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Student Tracker App</title>

        <link type="text/css" rel="stylesheet" href="static/css/style.css">
        <link type="text/css" rel="stylesheet" href="static/css/modal.css">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <h2>University Students List</h2>
            </div>
        </div>

        <div id="container">
            <div id="content">

                <table>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Address</th>
                        <th>Action</th>
                    </tr>

                    <c:forEach var="student" items="${studentList}">

                        <!-- set up a link for each student -->
                        <c:url var="tempLink" value="student">
                            <c:param name="command" value="LOAD" />
                            <c:param name="studentId" value="${student.id}" />
                        </c:url>

                        <!-- set up a link to delete a student -->
                        <c:url var="deleteLink" value="student">
                            <c:param name="command" value="DELETE" />
                            <c:param name="studentId" value="${student.id}" />
                        </c:url>

                        <tr>
                            <td>${student.firstName}</td>
                            <td>${student.lastName}</td>
                            <td>${student.email}</td>
                            <td>${student.address}</td>
                            <td>
                                <a href="${tempLink}">Update</a>
                                |
                                <a href="${deleteLink}" class="delete-link"
                                   onclick="openDeleteModal(event, '${deleteLink}')">
                                    Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <input type="button" value="Add Student"
                       onclick="window.location.href='add-student-form.jsp'; return false;"
                       class="add-student-button" />
            </div>
        </div>

        <div id="deleteModal" class="modal">
            <div class="modal-content">
                <p>Are you sure you want to delete this student?</p>
                <button onclick="confirmDelete()">Yes, delete</button>
                <button onclick="closeDeleteModal()">Cancel</button>
            </div>
        </div>

        <script src="static/js/student-actions.js"></script>
    </body>
</html>

