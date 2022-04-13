<!DOCTYPE html>
<html>
    <head>
        <title>Course list</title>
    </head>
    <body>
        <security:authorize access="hasRole('ADMIN')">
            <h2>Admin Right</h2>
            <a href="<c:url value="/user" />">Manage User Accounts</a><br /><br />
            <a href="<c:url value="/course/create" />">Create a Course</a><br /><br />
        </security:authorize>
        <h2>Courses</h2>


        <c:choose>
            <c:when test="${empty courseDatabase}">
                <i>There are no courses in the system.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${courseDatabase}" var="course">
                    Course ${course.id}:
                    <a href="<c:url value="/course/view/${course.id}" />">
                        <c:out value="${course.subject}" /></a>
                    (lecturer: <c:out value="${course.lectureName}" />)
                    <security:authorize access="hasRole('ADMIN')">
                        [<a href="<c:url value="/course/edit/${course.id}" />">Edit</a>]
                    </security:authorize>
                    <security:authorize access="hasRole('ADMIN')">            
                        [<a href="<c:url value="/course/delete/${course.id}" />">Delete</a>]
                    </security:authorize>
                    <br /><br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <h2>Polling</h2>
        <c:choose>
            <c:when test="${empty pollings}">
                <i>There are no polling in the system.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${pollings}" var="poll">
                    Poll <a href="<c:url value="course/view/${poll.id}" />"><c:out value="${poll.question}"/></a> from course:
                    
                        <c:out value="${poll.courseId}" />
                    
                    <security:authorize access="hasRole('ADMIN')">
                        [<a href="<c:url value="/edit/${poll.courseId}/Polling/${poll.id}" />">Edit</a>]
                    </security:authorize>
                    <security:authorize access="hasRole('ADMIN')">            
                        [<a href="<c:url value="/delete/${poll.courseId}/Polling/${poll.id}" />">Delete</a>]
                    </security:authorize>
                    <br /><br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <h2>Comment</h2>
        <c:choose>
            <c:when test="${empty comments}">
                <i>There are no comment in the system.</i>
            </c:when>
            <c:otherwise>
                <c:forEach items="${comments}" var="comment">
                    Comment <a href="<c:url value="/course/view/${comment.id}" />"><c:out value="${comment.comment}"/></a> from course:
                    
                        <c:out value="${comment.courseId}" />
                    
                    <security:authorize access="hasRole('ADMIN')">
                        [<a href="<c:url value="/edit/${comment.courseId}/Comment/${comment.id}" />">Edit</a>]
                    </security:authorize>
                    <security:authorize access="hasRole('ADMIN')">            
                        [<a href="<c:url value="/delete/${comment.courseId}/Comment/${comment.id}" />">Delete</a>]
                    </security:authorize>
                    <br /><br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <security:authorize access="hasAnyRole('ADMIN','USER')">
            <c:url var="logoutUrl" value="/cslogout"/>
            <form action="${logoutUrl}" method="post">
                <input type="submit" value="Log out" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </security:authorize>
        <security:authorize access="!hasAnyRole('ADMIN','USER')">
            <a href="<c:url value='/cslogin'/>"><button>Login</button></a>
            <a href="<c:url value='/register'/>"><button>Sign up</button></a>
        </security:authorize>
    </body>
