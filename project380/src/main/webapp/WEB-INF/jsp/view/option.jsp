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
       
            <h1>${pollings[pollingId-1].question}</h1>
            <form:form action="${addOptionURL}" method="POST" modelAttribute="newOption">
        ${pollings[pollingId-1].a}<form:radiobutton path="option" value="${pollings[pollingId-1].a}"/><br>
        ${pollings[pollingId-1].b}<form:radiobutton path="option" value="${pollings[pollingId-1].b}"/><br>
        <c:if test = "${!empty pollings[pollingId-1].c}">
         ${pollings[pollingId-1].c}<form:radiobutton path="option" value="${pollings[pollingId-1].c}"/><br>
      </c:if>
        <c:if test = "${!empty pollings[pollingId-1].d}">
         ${pollings[pollingId-1].d}<form:radiobutton path="option" value="${pollings[pollingId-1].d}"/><br>
      </c:if>
            
       
            <input type="submit"/><br/><br/><br/>
</form:form>
        
    </body>
</html>
