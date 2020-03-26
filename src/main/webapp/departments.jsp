<%--
  Author: linet
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Departments</title>
</head>
<body>
<div align="center">
    <h1>Departments</h1>
    <table border="1" cellpadding="5">
        <th>Id</th>
        <th>Department</th>
        <th>Actions</th>
        <c:forEach items="${departmentsList}" var="department">
            <tr>
                <td>${department.id}</td>
                <td>${department.name}</td>
                <td>
                    <button type="button" onclick="window.location='./departments/${department.id}'">Employees</button>
                    <button type="button" onclick="window.location='./departments/${department.id}/update'">Update</button>
                    <button type="button" onclick="window.location='./departments/${department.id}/delete'">Delete</button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <p>
        <button type="button" onclick="window.location='./departments/add'">Add new department</button>
    </p>
</div>
</body>
</html>
