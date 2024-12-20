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
        <title>Create Product - SB Admin</title>
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
                                <h3>Create a product</h3> 
                            <br>
                    <form:form action="/admin/product/create" method="post" modelAttribute="newProduct" class="row g-3" 
                    enctype="multipart/form-data">
                    <div class="mb-3 col-12 col-md-6">
                        <c:set var="errorName">
                                <form:errors path="name" />
                            </c:set>
                          <label for="name" class="form-label">Name </label>
                          <form:input type="name" path="name" class="form-control ${not empty errorName ? 'is-invalid' : ''}" id="name" />
                          <form:errors path="name" cssClass="invalid-feedback"/>  
                        </div>
                        <div class="mb-3 col-12 col-md-6">
                            <c:set var="errorPrice">
                                <form:errors path="price" />
                            </c:set>
                          <label for="Price" class="form-label">Price</label>
                          <form:input type="number" class="form-control ${not empty errorPrice ? 'is-invalid' : ''}" id="price"
                          path="price"
                          />
                          <form:errors path="price" cssClass="invalid-feedback"/>  
                        </div>
                        <div class="mb-3 col-12 col-md-6">
                            <c:set var="errorDetailDes">
                                <form:errors path="detailDesc" />
                            </c:set>
                            <label for="detailDepcription" class="form-label">Detail description </label>
                            <form:input type="text" class="form-control ${not empty errorDetailDes ? 'is-invalid' : ''}" id="detailDescription" 
                            path="detailDesc"
                            />
                            <form:errors path="detailDesc" cssClass="invalid-feedback"/>  
                        </div>
                        <div class="mb-3 col-12 col-md-6">
                            <c:set var="errorShortDes">
                                <form:errors path="shortDesc" />
                            </c:set>
                            <label for="shortDescription" class="form-label">Short description </label>
                            <form:input type="text" class="form-control ${not empty errorShortDes ? 'is-invalid' : ''}" id="shortDescription" 
                            path="shortDesc"
                            />
                            <form:errors path="shortDesc" cssClass="invalid-feedback"/>  
                        </div>
                        <div class="col-md-12">
                            <c:set var="errorQuantity">
                                <form:errors path="quantity" />
                            </c:set>
                            <label for="Quantity" class="form-label">Quantity </label>
                            <form:input type="number" class="form-control ${not empty errorQuantity ? 'is-invalid' : ''}" id="Quantity" 
                            path="quantity"
                            />
                            <form:errors path="quantity" cssClass="invalid-feedback"/>  
                        </div>
                        <div class="mb-3 col-12 col-md-6">
                        <label for="Factory" class="form-label">Factory </label>
                        <form:select class="form-select"  path="factory" >
                            <form:option value="Apple">Apple</form:option>
                            <form:option value="Asus">Asus</form:option>
                            <form:option value="Lenovo">Lenovo</form:option>
                            <form:option value="Dell">Dell</form:option>
                            <form:option value="Lg">Lg</form:option>
                            <form:option value="Acer">Acer</form:option>
                          </form:select></div>
                          <div class="mb-3 col-12 col-md-6">
                            <label for="Target" class="form-label">Target </label>
                            <form:select class="form-select"  path="target" >
                                <form:option value="Gaming">Gaming</form:option>
                                <form:option value="Sinh-Vien-Van-Phong">Sinh Vien-Van Phong</form:option>
                                <form:option value="Thiet-ke-do-hoa">Thiet ke do hoa</form:option>
                                <form:option value="Mong-nhe">Mong nhe</form:option>
                                <form:option value="Doanh-nhan">Doanh nhan</form:option>
                              </form:select></div>
                        <div class="mb-3 col-12 col-md-6">
                            <label for="avatarFile" class="form-label">Image</label>
                            <input class="form-control" type="file" id="avatarFile"  accept=".png, .jpg, .jpeg" name="hoidanitFile"/>
                        </div>
                        <div class="col-12 mb-3">
                            <img style="max-height: 250px; display: none;" alt="avatar preview" id="avatarPreview">
                        </div>
                        <div class="col-12 mb-5">
                        <button type="submit" class="btn btn-primary">Create</button></div>
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


  
  

