<!DOCTYPE html>
<html>
<head>
    <title>Customer Support</title>
</head>
<body>


<h2>Course #${course.id}</h2>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="courseForm">   
    <form:label path="subject">Subject</form:label><br/>
    <form:input type="text" path="subject" /><br/><br/>
    <form:label path="body">Body</form:label><br/>
    <form:textarea path="body" rows="5" cols="30" /><br/><br/>
    <c:if test="${fn:length(course.attachments) > 0}">
        <b>Attachments:</b><br/>
        <ul>
            <c:forEach items="${course.attachments}" var="attachment">
                <li>
                    <c:out value="${attachment.name}" />
                    [<a href="<c:url
                            value="/course/${course.id}/delete/${attachment.name}"
                            />">Delete</a>]
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <form:label path="attachments">
                Add Attachments
            </form:label><br />
            <input type="file" id="files" 
                   name="attachments" 
                   value="Choose New Files"
                   multiple="multiple" onchange="updateFileList()" />
            <div id="fileList"></div><br/>
    <input type="submit" value="Save"/><br/><br/>
</form:form>
<a href="<c:url value="/course" />">Return to list courses</a>
<c:url var="logoutUrl" value="/cslogout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
    <script>
        const fileList = document.getElementById("fileList");
        const files = document.getElementById("files");
        const updateFileList = () => {
            let fileListHTML = "";
            if (files.files.length > 1) {
                for (let i = 0; i < files.files.length; i++) {
                    if (i > 0) {
                        fileListHTML += "\n ";
                    }
                    fileListHTML += files.files[i].name;
                }
            }
            fileList.innerHTML = fileListHTML;
        }

    </script>
</body>
</html> 