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
        <title>Update Product - SB Admin</title>
        <link href="/css/styles.css" rel="stylesheet" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script>
            $(document).ready(() => {
                const avatarFile = $("#avatarFile");
                const orgImage = "${product.image}";

                if(orgImage){
                    const urlImage = "/images/product/" +orgImage;
                    $("#avatarPreview").attr("src", urlImage);
                    $("#avatarPreview").css({ "display": "block" });
                }
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
                        <div class="row">
                            <div class="col-md-6 col-12 mx-auto">
                                <h3>Update a product by id = ${id}</h3>
                                <br>
                    <form:form action="/admin/product/update" method="post" modelAttribute="product" enctype="multipart/form-data">
                        <div class="mb-3" style="display: none;">
                            <label for="exampleInputPassword1" class="form-label">Id</label>
                            <form:input type="text" class="form-control" path="id" display="none"/>
                          </div>
                          <div class="mb-3 col-12 col-md-12">
                            <label for="name" class="form-label">Name </label>
                            <form:input type="name" path="name" class="form-control" id="name" />
                          </div>
                          <div class="mb-3 col-12 col-md-12">
                            <label for="Price" class="form-label">Price</label>
                            <form:input type="number" class="form-control" id="price"
                            path="price"
                            />
                          </div>
                          <div class="mb-3 col-12 col-md-12">
                              <label for="detailDepcription" class="form-label">Detail description </label>
                              <form:input type="text" class="form-control" id="detailDescription" 
                              path="detailDesc"
                              />
                          </div>
                          <div class="mb-3 col-12 col-md-12">
                              <label for="shortDescription" class="form-label">Short description </label>
                              <form:input type="text" class="form-control" id="shortDescription" 
                              path="shortDesc"
                              />
                          </div>
                          <div class="col-md-12">
                              <label for="Quantity" class="form-label">Quantity </label>
                              <form:input type="number" class="form-control" id="Quantity" 
                              path="quantity"
                              />
                          </div>

                          <div class="mb-3 col-12 col-md-12">
                            <label for="avatarFile" class="form-label">Image</label>
                            <input class="form-control" type="file" id="avatarFile"  accept=".png, .jpg, .jpeg" name="hoidanitFile"/>
                        </div>

                        <div class="col-12 mb-3">
                            <img style="max-height: 250px; display: none;" alt="avatar preview" id="avatarPreview" path="image">
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


  
  

