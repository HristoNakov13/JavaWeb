<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Username
  Date: 5.3.2020 г.
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tube Details</title>
    <c:import url="templates/head.jsp"/>
</head>
<body>
<div class="container">
    <main>
        <div class="jumbotron">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <h1>404 Tube not found.</h1>
                </div>
            </div>
            <hr class="my-4">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <a href="<c:url value="/"/>">Back to Home</a>
                </div>
            </div>
        </div>
        <footer>
            <c:import url="templates/footer.jsp"/>
        </footer>
    </main>
</div>
</body>
</html>
