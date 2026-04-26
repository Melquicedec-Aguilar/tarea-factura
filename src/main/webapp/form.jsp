<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="layout/header.jsp"/>

<h3>${tittle}</h3>

<c:choose>
    <c:when test="${tipo eq 'productos'}">
    <form action="${pageContext.request.contextPath}/productos/form" method="post">
        <div class="row mb-2">
            <label for="nombre" class="col-form-label col-sm-2">Nombre</label>
            <div class="col-sm-4">
                <input type="text" name="nombre" id="nombre" value="${producto.nombre}" class="form-control">
            </div>
        </div>

        <div class="row mb-2">
            <label for="precio" class="col-form-label col-sm-2">Precio</label>
            <div class="col-sm-4">
                <input type="number" name="precio" id="precio" value="${producto.precio}" class="form-control">
            </div>
        </div>

        <div class="row mb-2">
            <label for="sku" class="col-form-label col-sm-2">SKU</label>
            <div class="col-sm-4">
                <input type="text" name="sku" id="sku" value="${producto.sku}" class="form-control">
            </div>
        </div>

        <div class="row mb-2">
            <label for="fecha_registro" class="col-form-label col-sm-2">Fecha Registro</label>
            <div class="col-sm-4">
                <input type="text" name="fecha_registro" id="fecha_registro" value="${producto.fechaRegistro}" class="form-control">
            </div>
        </div>

        <div class="row mb-2">
            <label for="categoria" class="col-form-label col-sm-2">Categoria</label>
            <div class="col-sm-4">
                <select name="categoria" id="categoria" class="form-select">
                    <option value="">---- Seleccionar ----</option>

                    <c:forEach items="${categorias}" var="c">
                        <option value="${c.id}" ${c.id.equals(producto.categoria.id) ? "selected" : ""}>${c.nombre}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="row mb-2">
            <div>
                <input class="btn btn-primary" type="submit" value="${producto.id != null && producto.id > 0 ? 'Editar' : 'Crear'}"
            </div>
        </div>

        <input type="hidden" name="id" value="${producto.id}">
    </form>
    </c:when>
</c:choose>

<jsp:include page="layout/footer.jsp"/>