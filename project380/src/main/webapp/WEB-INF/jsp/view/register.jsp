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
        
        <br/>
    </body>
</html>