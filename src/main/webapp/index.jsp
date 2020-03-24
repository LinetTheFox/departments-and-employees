<%@ page import="database.DbManager" %>
<%@ page import="exception.DbInitializationException" %>
<%@ page import="java.util.List" %>
<%--
  Author: linet
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <h1>Departments and employees</h1>
<%--    <%--%>
<%--        List<String> departments;--%>
<%--        try {--%>
<%--            DbManager dbManager = DbManager.getInstance();--%>
<%--            departments = dbManager.listDepartments();--%>
<%--            if (departments == null) {--%>
<%--                response.sendError(500, "Oops! Something went wrong :(");--%>
<%--            } else {--%>
<%--                out.println("")--%>
<%--                for (String department : departments) {--%>
<%--                    out.println(--%>
<%--                            ""--%>
<%--                    );--%>
<%--                }--%>
<%--            }--%>
<%--        } catch (DbInitializationException e) {--%>
<%--            response.sendError(500, "Oops! Something went wrong :(");--%>
<%--        }--%>

<%--    %>--%>
</body>
</html>
