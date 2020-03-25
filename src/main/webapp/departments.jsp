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
    <table style="width:100%">
        <th>Department</th>
        <c:forEach items="${departmentsList}" var="department">
            <tr>
                <td>${department.name}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
