<%-- 
    Document   : commentHistory
    Created on : Apr 19, 2022, 9:58:24 PM
    Author     : DavidLam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comment History</title>
    </head>
    <body>
        <h1>Comment History</h1>
        <c:forEach items="${userComments}" var="userComment">
            ${userComment}<br>
        </c:forEach>
    </body>
</html>
