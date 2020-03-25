<%--
  Author: linet
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<c:set var="errorCode" value="${requestScope['javax.servlet.error.status_code']}"/>
<c:if test="${errorCode == 500}">
    <div>
        <h1>500: Internal server error</h1>
        Please try again later or feel free to contact us if the problem persists. Sorreh...
    </div>
</c:if>
<c:if test="${errorCode == 404}">
    <div>
        <h1>404: Not found</h1>
        We can't seem to find the page you're looking for.
    </div>
</c:if>
</body>
</html>
