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
        <h1>${polling.question}</h1>
        <c:url value="/user/${course.id}/${pollingId}/addOption" var="URL"/>
        <c:if test="${voted>=0}">
            <c:url value="/user/${course.id}/${pollingId}/${voted}/changeOption" var="URL"/>
            <c:out value="Last vote : ${choose}"/>
        </c:if>
            <form:form action="${URL}" method="POST" modelAttribute="newOption">
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
            <a href="<c:url value="/course/view/${courseId}"/>">Return to course</a>
    </body>
</html>
