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
        <title>Update - SB Admin</title>
        <link href="/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
        <jsp:include page="../layout/header.jsp"/>
        <div id="layoutSidenav">
        <jsp:include page="../layout/sidebar.jsp"/>  
            <div id="layoutSidenav_content">
                <main>
                    <div class="container mt-5">
                        <div class="row">
                            <div class="col-md-6 col-12 mx-auto">
                                <h3>Update a user</h3>
                                <br>
                    <form:form action="/admin/user/update" method="post" modelAttribute="newUser" >
                        <div class="mb-3" style="display: none;">
                            <label for="exampleInputPassword1" class="form-label">Id</label>
                            <form:input type="text" class="form-control" path="id" display="none"/>
                          </div>
                        <div class="mb-3">
                          <label for="exampleInputEmail1" class="form-label">Email </label>
                          <form:input type="email" path="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" disabled="true"/>
                        </div>
                        
                        <div class="mb-3">
                            <label for="phoneNumber" class="form-label">Phone number </label>
                            <form:input type="text" class="form-control" id="phoneNumber" 
                            path="phone"
                            />
                        </div>
                        <div class="mb-3">
                            <label for="fullName" class="form-label">Full name </label>
                            <form:input type="text" class="form-control" id="fullName" 
                            path="fullName"
                            />
                        </div>
                        <div class="mb-3">
                            <label for="Address" class="form-label">Address </label>
                            <form:input type="text" class="form-control" id="Address" 
                            path="Address"
                            />
                        </div>
                       
                        <button type="submit" class="btn btn-warning">Update</button>
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


  
  

