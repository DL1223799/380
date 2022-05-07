<!DOCTYPE html>
<html>
    <head>
        <title>Customer Support</title>
    </head>
    <body>


        <h2>Create a Course</h2>
        <form:form method="POST" enctype="multipart/form-data"
                   modelAttribute="courseForm">
            <form:label path="subject">Subject</form:label><br />
            <form:input type="text" path="subject" /><br /><br />
            <form:label path="body">Body</form:label><br />
            <form:textarea path="body" rows="5" cols="30" /><br /><br />
            <b>Attachments</b><br />



            <input type="file" name="attachments" multiple="multiple" /><br /><br />
            <link rel="stylesheet" href="style.css">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
            <div class="drag-area">
                <div class="icon"><i class="fas fa-cloud-upload-alt"></i></div>
                <header>Drag & Drop to Upload File</header>
                <br/>
                <input type="file" hidden>
            </div>

            <script src="drag.js"></script>
            <input type="submit" value="Submit"/>
        </form:form>
        <c:url var="logoutUrl" value="/cslogout"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
 <a href="<c:url value="/course" />">Return to list courses</a>       
    </body>
</html>
