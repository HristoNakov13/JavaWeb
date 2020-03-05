<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Your Tube</title>
    <c:import url="templates/head.jsp"/>
</head>
<body>
<div class="container">
    <main>
        <div class="jumbotron">
            <div class="row">
                <div class="col col-md-12 d-flex justify-content-center">
                    <h1>Create Tube!</h1>
                </div>
            </div>
            <hr class="my-4">
            <form action="<c:url value="/tubes/create"/>" method="post">
                <div class="row">
                    <div class="col col-md-12">
                        <div class="row d-flex justify-content-center">
                            <label for="title">Title:</label>
                        </div>
                        <div class="row d-flex justify-content-center">
                            <input id="title" type="text" name="title">
                        </div>
                    </div>
                </div>
                <hr class="my-4">
                <div class="row">
                    <div class="col col-md-12">
                        <div class="row d-flex justify-content-center">
                            <label for="description">Description:</label>
                        </div>
                        <div class="row d-flex justify-content-center">
                            <textarea id="description" name="description"></textarea>
                        </div>
                    </div>
                </div>
                <hr class="my-4">
                <div class="row">
                    <div class="col col-md-12">
                        <div class="row d-flex justify-content-center">
                            <label for="youtubeLink">YoutubeLink:</label>
                        </div>
                        <div class="row d-flex justify-content-center">
                            <input id="youtubeLink" type="url" name="youtubeLink">
                        </div>
                    </div>
                </div>
                <hr class="my-4">
                <div class="row">
                    <div class="col col-md-12">
                        <div class="row d-flex justify-content-center">
                            <label for="uploader">Uploader:</label>
                        </div>
                        <div class="row d-flex justify-content-center">
                            <input id="uploader" type="text" name="uploader">
                        </div>
                    </div>
                </div>
                <hr class="my-4">
                <div class="row">
                    <div class="col col-md-12 d-flex justify-content-center">
                        <input class="btn btn-primary" type="submit" value="Create Tube">
                    </div>
                </div>
                <hr class="my-4">
                <div class="row">
                    <div class="col col-md-12 d-flex justify-content-center">
                        <a href="<c:url value="/"/>">Back to Home</a>
                    </div>
                </div>
            </form>
        </div>
    </main>
    <footer>
        <c:import url="templates/footer.jsp"/>
    </footer>
</div>

</body>
</html>
