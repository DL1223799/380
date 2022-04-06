<!DOCTYPE html>
<html>
<head>
    <title>Customer Support</title>
</head>
<body>


<h2>Course #${course.id}: <c:out value="${course.subject}" /></h2>
<security:authorize access="hasRole('ADMIN') or principal.username=='${course.lectureName}'">
    [<a href="<c:url value="/course/edit/${course.id}" />">Edit</a>]
</security:authorize>
<security:authorize access="hasRole('ADMIN')">            
    [<a href="<c:url value="/course/delete/${course.id}" />">Delete</a>]
</security:authorize>
<br /><br />
<i>Customer Name - <c:out value="${course.lectureName}" /></i><br /><br />
<c:out value="${course.body}" /><br /><br />
<c:if test="${fn:length(course.attachments) > 0}">
    Attachments:
    <c:forEach items="${course.attachments}" var="attachment" varStatus="status">
        <c:if test="${!status.first}">, </c:if>
        <a href="<c:url value="/course/${course.id}/attachment/${attachment.name}" />">
            <c:out value="${attachment.name}" /></a>
    </c:forEach><br /><br />
</c:if>
<a href="<c:url value="/course" />">Return to list courses</a>
<c:url var="logoutUrl" value="/cslogout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
</html>