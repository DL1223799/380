<%-- 
    Document   : pollHistory
    Created on : Apr 19, 2022, 9:54:51 PM
    Author     : DavidLam
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Vote History</title>
    </head>
    <body>
        <h1>Vote History</h1>
        <c:forEach items="${userPollings}" var="userPolling">
            ${userPolling}<br>
        </c:forEach>
    </body>
</html>