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
            <form:form action="${addOptionURL}" method="POST" modelAttribute="newOption">
        <SELECT id="option" name="option" >
            <option value="${polling.a}" >${polling.a}</option>
            <option value="${polling.b}" >${polling.b}</option>
            <option value="${polling.c}" >${polling.c}</option>
            <option value="${polling.d}" >${polling.d}</option>
        </select>
</form:form>
        </c:forEach>
    </body>
</html>
