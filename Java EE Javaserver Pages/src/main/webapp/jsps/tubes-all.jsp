<%@ page import="java.util.List" %>
<%@ page import="metube.util.Constants" %>
<%@ page import="metube.domain.dtos.view.AllTubesViewDto" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% List<AllTubesViewDto> allTubes = (List<AllTubesViewDto>) request.getAttribute(Constants.ALL_TUBES_VIEW_MODEL_ATTRIBUTE);%>
<html>
<head>
    <title>All Tubes</title>
    <c:import url="templates/head.jsp"/>
</head>
<body>
<div class="container">
    <main>
        <div class="jumbotron">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <h1>All Tubes</h1>
                </div>
            </div>
            <hr class="my-4">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <p>Check our tubes below</p>
                </div>
            </div>
            <hr class="my-4">
            <div class="row">
                <div class="col col-md-6 d-flex justify-content-center">
                    <% if (!allTubes.isEmpty()) {%>
                    <ul>
                        <% for (AllTubesViewDto tube : allTubes) { %>
                        <li><a href="/tubes/details?id=<%=tube.getId()%>"><%=tube.getName()%>
                        </a></li>
                        <%}%>
                    </ul>
                    <%} else {%>
                    <a href="<c:url value="/tubes/create"/>">Create Some</a>
                    <%}%>
                </div>
            </div>
            <hr class="my-4">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <a href="<c:url value="/"/>">Back to Home</a>
                </div>
            </div>
            <footer>
                <c:import url="templates/footer.jsp"/>
            </footer>
        </div>
    </main>
</div>
</body>
</html>