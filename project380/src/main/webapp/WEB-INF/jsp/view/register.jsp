<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up</title>
    </head>
    <body>
        <div style="float: left;">
            <button onclick="javascript:history.go(-1)">Go Back</button>
            <a href="<c:url value='/'/>" style="text-decoration: none;"><button>home</button></a>
        </div><br><br>
        <c:if test="${!empty status}">
            ${status.message}<br/>
        </c:if>
        <h1>Sign up</h1>
        <form:form action="register" method="POST" modelAttribute="courseuser">
            <form:label path="username">Username:</form:label></br>
            <form:input path="username" /><br/><Br/>
            <form:label path="password">Password:</form:label></br>
            <form:password path="password" /><br/><br/>
            <form:label path="fullName">Full Name:</form:label></br>
            <form:input path="fullName" /><br/><br/>
            <form:label path="phoneNumber">Phone Number:</form:label></br>
            <form:input path="phoneNumber" /><br/><br/>
            <form:label path="deliveryAddress">Delivery Address:</form:label></br>
            <form:input path="deliveryAddress" /><br/><br/>
            <input type="submit" value="Sign up" />            
        </form:form>
        <br/>
    </body>
</html>