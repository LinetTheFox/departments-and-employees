<%--
  Author: linet
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:if test="${receivedById}">
        <title>Employee</title>
    </c:if>
    <c:if test="${receivedById == null}">
        <title>Employees</title>
    </c:if>
</head>
<body>
<div align="center">
    <c:if test="${receivedById}">
        <h1>Employee</h1>
    </c:if>
    <c:if test="${receivedById == null}">
        <h1>Employees of department</h1>
        <h1>${departmentName}</h1>
    </c:if>

    <table border="1" cellpadding="5">
        <th>Id</th>
        <th>Name</th>
        <th>Email</th>
        <c:if test="${receivedById || listAll}">
            <th>Department</th>
        </c:if>
        <th>Salary</th>
        <th>Hire date</th>
        <th>Actions</th>
        <c:forEach items="${employeesList}" var="employee">
            <tr>
                <td>${employee.id}</td>
                <td>${employee.name}</td>
                <td>${employee.email}</td>
                <c:if test="${receivedById || listAll}">
                    <td>${employee.departmentName}</td>
                </c:if>
                <td>${employee.salary}</td>
                <td>${employee.date}</td>
                <td>
                    <button type="button" onclick="window.location='../employees/${employee.id}/update'">Update</button>
                    <button type="button" onclick="window.location='../employees/${employee.id}/delete'">Delete</button>
                </td>
            </tr>
        </c:forEach>
        <p>
            <c:if test="${obtainedFromAllEmployees}">
                <button type="button" onclick="window.location='./employees/add?departmentId=${departmentId}'">Add new employee</button>
            </c:if>
            <c:if test="${obtainedFromAllEmployees == null}">
                <button type="button" onclick="window.location='../employees/add?departmentId=${departmentId}'">Add new employee</button>
            </c:if>
        </p>
    </table>
</div>
</body>
</html>
