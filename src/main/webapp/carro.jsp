<%@page contentType="text/html" pageEncoding="UTF-8" import="org.aguilar.webapp.headers.models.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="layout/header.jsp"/>

<h3>${title}</h3>

<form name="formcarro" action="${pageContext.request.contextPath}/actualizar-carro" method="post">

<c:choose>
    <c:when test="${carro == null || carro.items.isEmpty()} ">
     <div class="alert alert-warning">Lo sentimos no hay productos en el carro de compras!</div>
    </c:when>

<c:otherwise>
<table class="table table-hover table-striped">
    <tr>
        <td>id</td>
        <td>nombre</td>
        <td>precio</td>
        <td>cantidad</td>
        <td>Total</td>
        <td>Borrar</td>
    </tr>

    <c:forEach items="${carro.items}" var="item">

    <tr>
        <td>${item.producto.id}</td>
        <td>${item.producto.nombre}</td>
        <td>${item.producto.precio}</td>
        <td><input type="text" size="4" name="cant_${item.producto.id}" value="${item.cantidad}"/></td>
        <td>${item.importe}</td>
        <td><input type="checkbox" value="${item.producto.id}" name="deleteProductos"/></td>
    </tr>

    </c:forEach>

    <tr>
        <td colspan="5" style="text-align: right">Total:</td>
        <td>${carro.total}</td>
    </tr>

</table>

<a class="btn btn-sm btn-primary" href="javascript:document.formcarro.submit();">Actualizar</a>

</c:otherwise>
</c:choose>
</form>

<div class="mt-3">
<a class="btn btn-secondary" href="${pageContext.request.contextPath}/index.jsp">Volver</a>
<a class="btn btn-success" href="${pageContext.request.contextPath}/productos">Seguir comprando</a>
<form action="${pageContext.request.contextPath}/factura" method="post">
    <button type="submit">Generar Factura</button>
</form>
</div>

<jsp:include page="layout/footer.jsp"/>