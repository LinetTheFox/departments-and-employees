<%--
  Author: linet
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add department</title>
</head>
<body>
    <div align="center">
        <h1>Add a new department</h1>
        <form action="add" method="post">
            <table border="1" cellpadding="5">
                <tr>
                    <th>Name:</th>
                    <td>
                        <input type="text" name="name" size="45"/>
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
