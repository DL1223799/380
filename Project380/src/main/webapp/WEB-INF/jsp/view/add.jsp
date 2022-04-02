<%-- 
    Document   : add
    Created on : Apr 1, 2022, 9:10:29 PM
    Author     : DavidLam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>
        <h2>Create a Ticket</h2>
       <c:url var="logoutUrl" value="/cslogout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="ticketForm">

            <form:label path="subject">Subject</form:label><br />
            <form:input type="text" path="subject" /><br /><br />
            <form:label path="body">Body</form:label><br />
            <form:textarea path="body" rows="5" cols="30" /><br /><br />
            <b>Attachments</b><br />
            <input type="file" name="attachments" multiple="multiple" /><br /><br />
            <input type="submit" value="Submit"/>
        </form:form>
    </body>
</html>

