<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="layout/header.jsp"/>

<h3 style="color:#2c3e50; font-family:Arial, sans-serif;">${title}</h3>

<h2 style="color:#2980b9;">Ejemplo Factura con inyección de dependencia</h2>

<div style="background:#ecf0f1; padding:15px; border-radius:8px; margin-bottom:20px;">
    <p><strong style="color:#27ae60;">Factura: </strong> ${factura.numeroFactura}</p>
    <p><strong style="color:#8e44ad;">${factura.descripcion}</strong></p>
    <p><strong>Cliente: </strong> ${factura.usuario.username}</p>
    <p><strong style="color:#c0392b;">Fecha: </strong> ${factura.fechaFactura}</p>
</div>

<table style="width:100%; border-collapse:collapse; font-family:Arial, sans-serif;">
    <thead style="background:#34495e; color:white;">
        <tr>
            <th style="padding:10px; border:1px solid #ddd;">Producto</th>
            <th style="padding:10px; border:1px solid #ddd;">Precio</th>
            <th style="padding:10px; border:1px solid #ddd;">Cantidad</th>
            <th style="padding:10px; border:1px solid #ddd;">Total</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${factura.lineasFactura}" var="linea">
            <tr style="background:${linea.cantidad % 2 == 0 ? '#f9f9f9' : '#ffffff'};">
                <td style="padding:10px; border:1px solid #ddd;">${linea.producto.nombre}</td>
                <td style="padding:10px; border:1px solid #ddd; color:#27ae60;">${linea.precio}</td>
                <td style="padding:10px; border:1px solid #ddd;">${linea.cantidad}</td>
                <td style="padding:10px; border:1px solid #ddd; font-weight:bold;">${linea.precio * linea.cantidad}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<jsp:include page="layout/footer.jsp"/>