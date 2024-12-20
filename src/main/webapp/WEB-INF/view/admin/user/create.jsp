<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Create User - SB Admin</title>
        <link href="/css/styles.css" rel="stylesheet" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script>
            $(document).ready(() => {
                const avatarFile = $("#avatarFile");
                avatarFile.change(function (e) {
                    const imgURL = URL.createObjectURL(e.target.files[0]);
                    $("#avatarPreview").attr("src", imgURL);
                    $("#avatarPreview").css({ "display": "block" });
                });
            });
        </script>
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
        <jsp:include page="../layout/header.jsp"/>
        <div id="layoutSidenav">
        <jsp:include page="../layout/sidebar.jsp"/>  
            <div id="layoutSidenav_content">
                <main>
                    <div class="container mt-5">
                        <div class="row ">
                            <div class=" col-md-6 col-12 mx-auto">
                                <h3>Create a user</h3> 
                            <br>
                    <form:form action="/admin/user/create" method="post" modelAttribute="newUser" class="row g-3" 
                    enctype="multipart/form-data">
                    <div class="mb-3 col-12 col-md-6">
                            <c:set var="errorEmail">
                                <form:errors path="email" />
                            </c:set>
                          <label for="exampleInputEmail1" class="form-label">Email </label>
                          <form:input type="email" path="email" class="form-control ${not empty errorEmail ? 'is-invalid' : ''}" id="exampleInputEmail1" aria-describedby="emailHelp"/>
                          <form:errors path="email" cssClass="invalid-feedback" />  
                        </div>
                        <div class="mb-3 col-12 col-md-6">
                            <c:set var="errorPass">
                                <form:errors path="password" />
                            </c:set>
                          <label for="exampleInputPassword1" class="form-label">Password</label>
                          <form:input type="password" class="form-control ${not empty errorPass ? 'is-invalid' : ''}" 
                          path="password"/>
                          <form:errors path="password" cssClass="invalid-feedback"/>  
                          
                        </div>
                        <div class="mb-3 col-12 col-md-6">
                            <label for="phoneNumber" class="form-label">Phone number </label>
                            <form:input type="text" class="form-control" id="phoneNumber" 
                            path="phone"
                            />
                        </div>
                        <div class="mb-3 col-12 col-md-6">
                            <c:set var="errorFullName">
                                <form:errors path="fullName" />
                            </c:set>
                            <label for="fullName" class="form-label">Full name </label>
                            <form:input type="text" class="form-control ${not empty errorFullName ? 'is-invalid' : ''}" id="fullName" 
                            path="fullName"
                            />
                            <form:errors path="fullName" cssClass="invalid-feedback" />  
                        </div>
                        <div class="col-md-12">
                            <label for="Address" class="form-label">Address </label>
                            <form:input type="text" class="form-control" id="Address" 
                            path="Address"
                            />
                        </div>   
                        <div class="mb-3 col-12 col-md-6">
                        <label for="Role" class="form-label">Role </label>
                        <form:select class="form-select" path="role.name" >
                            <form:option value="ADMIN">ADMIN</form:option>
                            <form:option value="USER">USER</form:option>
                          </form:select></div>
                        <div class="mb-3 col-12 col-md-6">
                            <label for="avatarFile" class="form-label">Avatar</label>
                            <input class="form-control" type="file" id="avatarFile"  accept=".png, .jpg, .jpeg" name="hoidanitFile"/>
                        </div>
                        <div class="col-12 mb-3">
                            <img style="max-height: 250px; display: none;" alt="avatar preview" id="avatarPreview">
                        </div>
                        <div class="col-12 mb-5">
                        <button type="submit" class="btn btn-primary">Submit</button></div>
                      </form:form>
                    </div>
                </div>
                </div>
                </main>
                <jsp:include page="../layout/footer.jsp"/>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="/js/scripts.js"></script>

    </body>
</html>


  
  

