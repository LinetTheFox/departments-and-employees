<%--
  Author: linet
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add employee</title>
</head>
<body>
<div align="center">
    <c:if test="${departmentName != null}">
        <h1>Add a new employee to department: ${departmentName}</h1>
    </c:if>
    <c:if test="${departmentList != null}">
        <h1>Add a new employee</h1>
    </c:if>
    <form action="add" method="post">
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
                    <c:if test="${departmentList != null}">
                        <input list="departments" name="departmentName" size="45"/>
                        <datalist id="departments">
                            <c:forEach items="${departmentList}" var="department">
                                <option value="${department.name}">
                            </c:forEach>
                        </datalist>
                    </c:if>
                    <c:if test="${departmentName != null}">
                        <input type="text" name="departmentName" size="45" value="${departmentName}" readonly/>
                    </c:if>
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
