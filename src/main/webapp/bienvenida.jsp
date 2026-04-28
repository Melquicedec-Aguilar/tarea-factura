<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  bienvenida.jsp
  ---------------
  Página de bienvenida que se muestra tras un login exitoso.

  Propósito:
  - Mostrar un saludo al usuario autenticado (atributo "username" en el request).
  - Mostrar cuántos productos hay actualmente en su carrito (objeto "carro" almacenado en la sesión).

  Includes:
  - Se incluye "layout/header.jsp" y "layout/footer.jsp" para mantener la estructura común de la aplicación.

  Atributos esperados:
  - request attribute "username": nombre del usuario a mostrar en la cabecera.
    Por ejemplo: req.setAttribute("username", username);
  - session attribute "carro": objeto que representa el carrito del usuario almacenado en la sesión.
    Se espera que tenga la propiedad "items" (una colección) para poder leer su tamaño.

  Seguridad y buenas prácticas:
  - Servir esta página sobre HTTPS para proteger las credenciales en etapas previas (login).
  - Evitar exponer información sensible en la vista o en logs.
  - Considerar validar/verificar la existencia de los atributos antes de acceder a ellos para evitar errores.
    En JSP/EL, si el atributo es null, la expresión devuelve vacío, pero acceder a propiedades anidadas puede provocar problemas
    en determinadas configuraciones; usar JSTL (<c:if>) para comprobaciones explícitas ofrece mayor robustez.

  Nota sobre la expresión "${sessionScope.carro.items.size()}":
  - Asume que en la sesión existe el atributo "carro", que no es null y que "items" es una colección con el método size().
  - Si existe la posibilidad de que "carro" sea null, sería más seguro comprobar primero:
      <c:if test="${not empty sessionScope.carro}">
          ${sessionScope.carro.items.size()}
      </c:if>
    Pero NO se ha modificado la lógica original aquí, sólo se documenta la expectativa y la mejora posible.
--%>

<jsp:include page="layout/header.jsp"/>

<div class="container mt-4">
    <h2>¡Bienvenid@, ${username}!</h2>
    <p>Has iniciado sesión correctamente.</p>

    <%--
      Muestra el número de productos en el carrito.
      - Ahora usamos JSTL para comprobar existencia del carrito y de su lista de items.
      - Si no existe, mostramos 0 en lugar de causar problemas o mostrar vacío.
    --%>
    <p>Tienes actualmente <strong>
        <c:choose>
            <c:when test="${not empty sessionScope.carro and not empty sessionScope.carro.items}">
                ${sessionScope.carro.items.size()}
            </c:when>
            <c:otherwise>0</c:otherwise>
        </c:choose>
    </strong> productos en tu carrito. </p>

<jsp:include page="layout/footer.jsp"/>