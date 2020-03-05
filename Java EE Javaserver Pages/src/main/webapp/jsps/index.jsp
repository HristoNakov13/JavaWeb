<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>MeTube v1</title>
    <c:import url="templates/head.jsp"/>
</head>
<body>
<div class="container">
    <main>
        <div class="jumbotron">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <h1>Welcome to MeTube!</h1>
                </div>
            </div>
            <hr class="my-4">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
            <p>Cool app in beta version</p>
                </div>
            </div>
            <hr class="my-4">
            <div class="row">
                <div class="col col-md-6 d-flex justify-content-center">
                    <a href="/tubes/create" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Create Tube</a>
                </div>
                <div class="col col-md-6 d-flex justify-content-center">
                    <a href="/tubes/all" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">All Tubes</a>
                </div>
            </div>
        </div>
    </main>
    <footer>
        <c:import url="templates/footer.jsp"/>
    </footer>
</div>

</body>
</html>
