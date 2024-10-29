<%--
  Created by IntelliJ IDEA.
  User: initnur
  Date: 29/10/2024
  Time: 18:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>Add Student</title>

        <link type="text/css" rel="stylesheet" href="static/css/style.css">
        <link type="text/css" rel="stylesheet" href="static/css/add-student-style.css">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <h2>University Student List</h2>
            </div>
        </div>

        <div id="container">
            <h3>Add Student</h3>

            <form action="student" method="POST">
                <input type="hidden" name="command" value="ADD" />

                <table>
                    <tbody>
                        <tr>
                            <td><label>First name:</label></td>
                            <td><input type="text" name="firstName" /></td>
                        </tr>

                        <tr>
                            <td><label>Last name:</label></td>
                            <td><input type="text" name="lastName" /></td>
                        </tr>

                        <tr>
                            <td><label>Email</label></td>
                            <td><input type="text" name="email" /></td>
                        </tr>

                        <tr>
                            <td><label>Address:</label></td>
                            <td><input type="text" name="address" /></td>
                        </tr>

                        <tr>
                            <td><input type="submit" value="Save" class="save"/></td>
                        </tr>
                    </tbody>
                </table>
            </form>

            <div style="clear: both;"></div>

            <p>
                <a href="student">Back to List</a>
            </p>
        </div>
    </body>
</html>
