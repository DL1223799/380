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
                    (lecture: <c:out value="${course.lectureName}" />)
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
        <h2>Comment</h2>
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
