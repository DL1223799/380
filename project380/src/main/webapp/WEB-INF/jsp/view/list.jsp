<!DOCTYPE html>
<html>
<head>
    <title>Customer Support</title>
</head>
<body>
<c:url var="logoutUrl" value="/cslogout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

<h2>Courses</h2>
<security:authorize access="hasRole('ADMIN')">    
    <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />
</security:authorize>
<a href="<c:url value="/course/create" />">Create a Course</a><br /><br />
<c:choose>
    <c:when test="${fn:length(courseDatabase) == 0}">
        <i>There are no courses in the system.</i>
    </c:when>
    <c:otherwise>
        <c:forEach items="${courseDatabase}" var="course">
            Course ${course.id}:
            <a href="<c:url value="/course/view/${course.id}" />">
                <c:out value="${course.subject}" /></a>
            (customer: <c:out value="${course.customerName}" />)
            <security:authorize access="hasRole('ADMIN') or
                                principal.username=='${course.customerName}'">
                [<a href="<c:url value="/course/edit/${course.id}" />">Edit</a>]
            </security:authorize>
            <security:authorize access="hasRole('ADMIN')">            
                [<a href="<c:url value="/course/delete/${course.id}" />">Delete</a>]
            </security:authorize>
            <br /><br />
        </c:forEach>
    </c:otherwise>
</c:choose>
</body>
