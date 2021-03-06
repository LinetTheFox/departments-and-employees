<%--
  Author: linet
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit employee</title>
</head>
<body>
<div align="center">
    <h1>Edit an employee</h1>
    <form action="update" method="post">
        <table border="1" cellpadding="5">
            <tr>
                <th>Name:</th>
                <td>
                    <input type="text" name="name" size="45" value="${name}"/>
                </td>
            </tr>
            <tr>
                <th>Email:</th>
                <td>
                    <input type="text" name="email" size="45" value="${email}"/>
                </td>
            </tr>
            <tr>
                <th>Date:</th>
                <td>
                    <input type="date" name="date" size="45" value="${date}"/>
                </td>
            </tr>
            <tr>
                <th>Department:</th>
                <td>
                    <input list="departments" name="departmentName" size="45"/>
                    <datalist id="departments">
                        <c:forEach items="${departmentsList}" var="department">
                            <option value="${department.name}">
                        </c:forEach>
                    </datalist>
                </td>
            </tr>
            <tr>
                <th>Salary:</th>
                <td>
                    <input type="number" name="salary" size="45" value="${salary}">
                </td>
            </tr>

            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save">
                </td>
            </tr>
        </table>
    </form>

    <span style="color: red"><c:out value="${message}"/></span>
</div>
</body>
</html>
