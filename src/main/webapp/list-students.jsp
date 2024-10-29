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
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <h2>University Students List</h2>
            </div>
        </div>

        <div id="container">
            <div id="content">

                <input type="button" value="Add Student"
                       onclick="window.location.href='add-student-form.jsp'; return false;"
                       class="add-student-button" />

                <table>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Address</th>
                    </tr>

                    <c:forEach var="student" items="${studentList}">
                        <tr>
                            <td>${student.firstName}</td>
                            <td>${student.lastName}</td>
                            <td>${student.email}</td>
                            <td>${student.address}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>
</html>
