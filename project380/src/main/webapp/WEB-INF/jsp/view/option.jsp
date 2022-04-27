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
        <c:url value="/user/${course.id}/${pollingId}/addOption" var="addOptionURL"/>
            
            <h1>${polling.question}</h1>
            <form:form action="${addOptionURL}" method="POST" modelAttribute="newOption">
        ${polling.a}<form:radiobutton path="option" value="${polling.a}"/><br>
        ${polling.b}<form:radiobutton path="option" value="${polling.b}"/><br>
        <c:if test = "${!empty polling.c}">
         ${polling.c}<form:radiobutton path="option" value="${polling.c}"/><br>
      </c:if>
        <c:if test = "${!empty polling.d}">
         ${polling.d}<form:radiobutton path="option" value="${polling.d}"/><br>
      </c:if>
            
            <input type="submit"/><br/><br/><br/>
</form:form>
        
    </body>
</html>
