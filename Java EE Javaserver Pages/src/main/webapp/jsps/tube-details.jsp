<%@ page import="metube.domain.dtos.view.TubeViewDto" %>
<%@ page import="metube.util.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% TubeViewDto tubeViewDto = (TubeViewDto) request.getAttribute(Constants.TUBE_VIEW_MODEL_ATTRIBUTE);%>
<html>
<head>
    <title>Tube Details</title>
    <c:import url="templates/head.jsp"/>
</head>
<div class="container">
    <main>
        <div class="jumbotron">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <h1><%= tubeViewDto.getName()%>
                    </h1>
                </div>
            </div>
            <hr class="my-4">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <p><%=tubeViewDto.getDescription()%>
                    </p>
                </div>
            </div>
            <hr class="my-4">
            <div class="row">
                <div class="col col-md-6 d-flex justify-content-center">
                    <a href="<%= tubeViewDto.getYoutubeLink()%>">Video Link</a>
                </div>
                <div class="col col-md-6 d-flex justify-content-center">
                    <p><%=tubeViewDto.getUploader()%>
                    </p>
                </div>
            </div>
            <hr class="my-4">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <a href="<c:url value="/"/>">Back to Home</a>
                </div>
            </div>
        </div>
    </main>
    <footer>
        <c:import url="templates/footer.jsp"/>
    </footer>
</div>

<body>

</body>
</html>
