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
    [<a href="<c:url value="/course/polling/${course.id}" />">Create Polling of this course</a>]
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
    <c:choose>
            <c:when test="${!empty course.comments}" >
                <h3 style="margin-bottom:1px;">Comment</h3>
                <div style="border-style:solid; margin-top: 1px;">
                    <c:forEach items="${course.comments}" var="comment">
                        ${comment.username}: ${comment.comment} 
                        <security:authorize access="hasRole('ADMIN')">
                            <a href="<c:url value='/user/delete/${course.id}/Comment/${comment.id}' />" >[Delete]</a>
                        </security:authorize>
                        <br/>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <h3>No Comment Yet</h3>
            </c:otherwise>
        </c:choose>

        <security:authorize access="hasAnyRole('ADMIN','USER')">
            <c:url value="/user/${course.id}/addComment" var="addCommentURL"/>
            <form:form action="${addCommentURL}" method="POST" modelAttribute="newComment">
                 <form:label path="comment">Add Comment: </form:label><br/>
                <form:textarea path="comment"/><br/>
                <input type="submit"/><br/><br/><br/>
            </form:form>
        </security:authorize> 
<a href="<c:url value="/course" />">Return to list courses</a>
<c:url var="logoutUrl" value="/cslogout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
    
</body>
</html>