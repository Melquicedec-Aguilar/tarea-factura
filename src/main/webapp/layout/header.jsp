<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${tittle}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Navbar</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/index.jsp">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/productos">Productos</a>
        </li>
        <li class="nav-item">
           <a class="nav-link" href="${pageContext.request.contextPath}/ver-carro">Ver Carro (${sessionScope.carro.items.size()})</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            ${not empty sessionScope.username ? sessionScope.username : "Cuenta"}
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <c:if test="${empty sessionScope.username}">
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/login">Login</a></li>
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/usuarios/form">Crear cuenta</a></li>
            </c:if>
            <c:if test="${not empty sessionScope.username or sessionScope.username eq 'null'}">
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a></li>
            </c:if>
          </ul>
        </li>

      </ul>

    </div>
  </div>
</nav>
<div class="container">