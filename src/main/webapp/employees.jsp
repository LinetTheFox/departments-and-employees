<%--
  Created by IntelliJ IDEA.
  User: linet
  Date: 3/26/20
  Time: 4:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Employees</title>
</head>
<body>
<div align="center">
    <h1>Employees of department</h1>
    <h1>${departmentName}</h1>

    <table border="1" cellpadding="5">
        <th>Id</th>
        <th>Name</th>
        <th>Email</th>
        <th>Salary</th>
        <th>Hire date</th>
        <th>Actions</th>
        <c:forEach items="${employeesList}" var="employee">
            <tr>
                <td>${employee.id}</td>
                <td>${employee.name}</td>
                <td>${employee.email}</td>
                <td>${employee.salary}</td>
                <td>${employee.date}</td>
                <td>
                    <button type="button" onclick="window.location='./employees/${department.id}'">Employees</button>
                    <button type="button" onclick="window.location='./employees/${department.id}/update'">Update</button>
                    <button type="button" onclick="window.location='./employees/${department.id}/delete'">Delete</button>
                </td>
            </tr>
        </c:forEach>
        <p>
            <button type="button" onclick="window.location='./departments/add'">Add new department</button>
        </p>
    </table>
</div>
</body>
</html>
