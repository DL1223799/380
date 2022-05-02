<!DOCTYPE html>
<html>
    <head><title>Customer Support</title>
        <style>
            .error {
                color: red;
                font-weight: bold;
                display: block;
            }
        </style>
    </head>
    <body>
        <h2>Create a User</h2>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="courseUser">
            <form:label path="username">Username</form:label><br/>
            <form:errors path="username" cssClass="error" />
            <form:input type="text" path="username" /><br/><br/>
            <form:label path="password">Password</form:label><br/>
            <form:errors path="username" cssClass="error" />
            <form:input type="text" path="password" /><br/><br/>
            <form:label path="fullName">Full Name</form:label><br/>
             <form:errors path="fullName" cssClass="error" />
            <form:input type="text" path="fullName" /><br/><br/>
            <form:label path="phoneNumber">Phone Number</form:label><br/>
            <form:errors path="phoneNumber" cssClass="error" />
            <form:input type="text" path="phoneNumber" /><br/><br/>
            <form:label path="deliveryAddress">Delivery Address</form:label><br/>
             <form:errors path="deliveryAddress" cssClass="error" />
            <form:textarea path="deliveryAddress" rows="5" cols="30" /><br/><br/>
            <form:label path="roles">Roles</form:label><br/>
            <form:errors path="roles" cssClass="error" />
            <form:checkbox path="roles" value="ROLE_USER" />ROLE_USER
            <form:checkbox path="roles" value="ROLE_ADMIN" />ROLE_ADMIN
            <br /><br />
            <input type="submit" value="Add User"/>
        </form:form>
        <c:url var="logoutUrl" value="/cslogout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

    </body>
</html>
