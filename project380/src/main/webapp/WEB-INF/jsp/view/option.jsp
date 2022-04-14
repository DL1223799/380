<%-- 
    Document   : option
    Created on : Apr 9, 2022, 4:26:56 PM
    Author     : DavidLam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:forEach items="${pollings}" var="polling">
            <h1>${polling.question}</h1>
        </c:forEach>
    </body>
</html>
