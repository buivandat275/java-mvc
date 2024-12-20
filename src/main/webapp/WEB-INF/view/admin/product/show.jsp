<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Dashboard - SB Admin</title>
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
                        <div class="col-12 mx-auto">
                          <div class="d-flex justify-content-between ">
                              <h3>Table User</h3>
                              <a href="/admin/product/create" class="btn btn-primary">Create a product</a>
                          </div>
                          <hr>
                          <table class="table table-bordered table-hover"  >
                            <thead>
                              <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Name</th>
                                <th scope="col">Price</th>
                                <th scope="col">Factory</th>
                                <th scope="col">Action</th>
                              </tr>
                            </thead>
                            <tbody>
                             
                              <c:forEach var="product" items="${product1}">
                                <tr>
                                    <td>${product.id}</td>
                                    <td>${product.name}</td>
                                    <td>${product.price}</td>
                                    <td>${product.factory}</td>
                                    <td><a href="/admin/product/${product.id}" type="button" class="btn btn-success" >View</a>
                                        <a href="/admin/product/update/${product.id}" type="button" class="btn btn-warning" >Update</a>
                                        <a href="/admin/product/delete/${product.id}" type="button" class="btn btn-danger" >Delete</a>
                                    </td>
  
                                    
                                </tr>
                            </c:forEach>
                            </tbody>
                          </table>
                        </div>
                      </div>
                    </div>
                  </main>
                <jsp:include page="../layout/footer.jsp"/>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>

    </body>
</html>
