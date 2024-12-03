
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete user by id ${id}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
    <div class="container">
    <h1>Delete the user by id= ${id}</h1>
    <hr>
    <div class="alert alert-danger" role="alert">
        Are you sure to delete this user ?
    </div>
   
    <form:form method="post" action="/admin/user/delete"  modelAttribute="newUser">
        <div class="mb-3" style="display: none;">
            <label for="exampleInputPassword1" class="form-label">Id</label>
            <form:input type="text" value="${id}" class="form-control" path="id" display="none"/>
          </div>
        <button class="btn btn-danger">Confirn</button>

    </form:form>
</div>
</body>
</html>