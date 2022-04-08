<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>polling</title>
    </head>
    <body>
        <h2>polling</h2>
        <p>course:${course.id}<p>
        <c:url value="/user/${course.id}/addPolling" var="addPollingURL"/>
            <form:form action="${addPollingURL}" method="POST" modelAttribute="newPolling">
            <form:label path="question">Question:</form:label>
            <form:input path="question" /><br/><br/>
            <form:label path="a">A:</form:label>
            <form:input path="a" /><br/><br/>
            <form:label path="b">B:</form:label>
            <form:input path="b" /><br/><br/>
            <form:label path="c">C:</form:label>
            <form:input path="c" /><br/><br/>
            <form:label path="d">D:</form:label>
            <form:input path="d" /><br/><br/>
            <input type="submit" value="Add polling" />            
        </form:form>
        
        </body>
    </html>
