<!DOCTYPE html>
<html>
<head><title>Customer Support User Management</title></head>
<body>
<br /><br />
<a href="<c:url value="/course" />">Return to list courses</a>
<h2>Users</h2>
<a href="<c:url value="/user/create" />">Create a User</a><br /><br />
<c:choose>
    <c:when test="${fn:length(courseUsers) == 0}">
        <i>There are no users in the system.</i>
    </c:when>
    <c:otherwise>
    <table>
        <tr>
            <th>Username</th><th>Password</th><th>Full name</th><th>Phone</th><th>Email</th><th>Roles</th><th>Action</th>
        </tr>
        <c:forEach items="${courseUsers}" var="user">
<<<<<<< Updated upstream:project380/src/main/webapp/WEB-INF/jsp/view/listUser.jsp
            <td>${user.username}</td><td>${user.password}</td><td>${user.fullName}</td><td>${user.phoneNumber}</td>
            <td>${user.deliveryAddress}</td>
=======
        <tr>
            <td>${user.username}</td><td>${user.password}</td>
>>>>>>> Stashed changes:project 380/src/main/webapp/WEB-INF/jsp/view/listUser.jsp
            <td>
                <c:forEach items="${user.roles}" var="role" varStatus="status">
                    <c:if test="${!status.first}">, </c:if>
                    ${role.role}
                </c:forEach>
            </td>
            <td>
            [<a href="<c:url value="/user/delete/${user.username}" />">Delete</a>]
            </td>
        </tr>
        </c:forEach>
    </table>
    </c:otherwise>
</c:choose>
        <c:url var="logoutUrl" value="/cslogout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
