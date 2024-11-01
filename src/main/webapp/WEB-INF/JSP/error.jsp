<%--
  Created by IntelliJ IDEA.
  User: initnur
  Date: 01/11/2024
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Oops! Something Went Wrong</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            background-color: #f8f9fa;
            color: #333;
        }
        .error-container {
            text-align: center;
            max-width: 600px;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            font-size: 2.5rem;
            color: #dc3545;
        }
        p {
            font-size: 1.2rem;
        }
        .error-details {
            margin-top: 10px;
            font-size: 1rem;
            color: #555;
        }
        .button-container {
            margin-top: 20px;
        }
        .button-container a {
            display: inline-block;
            padding: 10px 20px;
            margin: 5px;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        .home-button {
            background-color: #007bff;
        }
        .home-button:hover {
            background-color: #0056b3;
        }
        .retry-button {
            background-color: #28a745;
        }
        .retry-button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<div class="error-container">
    <h1>Oops! Something Went Wrong</h1>
    <p>We're sorry, but an error has occurred while processing your request.</p>
    <div class="error-details">
        ${errorMessage != null ? errorMessage : "Unexpected error."}
    </div>
    <div class="button-container">
        <a href="<%= request.getContextPath() %>/student?command=LIST" class="home-button">Go to Home</a>
        <a href="javascript:history.back()" class="retry-button">Try Again</a>
    </div>
</div>
</body>
</html>