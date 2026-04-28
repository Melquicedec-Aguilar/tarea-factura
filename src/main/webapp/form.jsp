<!--
  src/main/webapp/form.jsp

  Propósito:
  - Vista JSP que muestra formularios para crear/editar entidades según el atributo request 'tipo':
    * 'productos' -> formulario de productos
    * 'usuarios'  -> formulario de usuarios

  Variables esperadas en request:
  - tipo: String ('productos' | 'usuarios')
  - producto: objeto Producto con campos (id, nombre, precio, sku, fechaRegistro, categoria)
  - usuario:  objeto Usuario con campos (id, username, email, password)
  - categorias: List<Categoria> (para el select de productos)
  - errores: Map<String, String> con mensajes de validación por campo

  Notas importantes:
  - Se usa JSTL (`c:choose`, `c:when`, `c:if`, `c:forEach`).
  - Revisar los errores tipográficos dentro del JSP (por ejemplo `tittle` vs `title`, `constainsKey`, `values` en input, test atributo sin `=`) que pueden provocar fallos en la evaluación de expresiones.
  - Validaciones del lado servidor deben poblar `errores` para mostrar mensajes junto a cada campo.
-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="layout/header.jsp"/>

<%-- Título: variable en request. Atención: se está usando `${tittle}` (posible typo, normalmente `title`). --%>
<h3>${tittle}</h3>

<c:choose>
    <%-- Sección: Productos --%>
    <c:when test="${tipo eq 'productos'}">
    <form action="${pageContext.request.contextPath}/productos/form" method="post">
        <%-- Campo: nombre
             - name/id: nombre
             - Mostrar error si existe clave 'nombre' en el mapa errores --%>
        <div class="row mb-2">
            <label for="nombre" class="col-form-label col-sm-2">Nombre</label>
            <div class="col-sm-4">
                <input type="text" name="nombre" id="nombre" value="${producto.nombre}" class="form-control">
            </div>
            <c:if test="${errores != null && errores.containsKey('nombre')}">
                <div style="color:red">${errores.nombre}</div>
            </c:if>
        </div>

        <%-- Campo: precio
             - name/id: precio
             - Mostrar error si existe clave 'precio' en errores --%>
        <div class="row mb-2">
            <label for="precio" class="col-form-label col-sm-2">Precio</label>
            <div class="col-sm-4">
                <input type="number" name="precio" id="precio" value="${producto.precio}" class="form-control">
            </div>
            <c:if test="${errores != null && !empty errores.precio}">
                <div style="color:red">${errores.precio}</div>
            </c:if>
        </div>

        <%-- Campo: sku
             - name/id: sku
             - Mostrar error si existe clave 'sku' --%>
        <div class="row mb-2">
            <label for="sku" class="col-form-label col-sm-2">SKU</label>
            <div class="col-sm-4">
                <input type="text" name="sku" id="sku" value="${producto.sku}" class="form-control">
            </div>
            <c:if test="${errores != null && errores.containsKey('sku')}">
                <div style="color:red">${errores.sku}</div>
            </c:if>
        </div>

        <%-- Campo: fecha_registro
             - name/id: fecha_registro
             - Se espera una fecha en el formato que el servidor pueda parsear --%>
        <div class="row mb-2">
            <label for="fecha_registro" class="col-form-label col-sm-2">Fecha Registro</label>
            <div class="col-sm-4">
                <input type="text" name="fecha_registro" id="fecha_registro" value="${producto.fechaRegistro}" class="form-control">
            </div>
            <c:if test="${errores != null && errores.containsKey('fecha_registro')}">
                <div style="color:red">${errores.fecha_registro}</div>
            </c:if>
        </div>

        <%-- Campo: categoria (select)
             - name/id: categoria
             - Se recorre la lista 'categorias' para poblar opciones
             - Atención: comparación `c.id.equals(producto.categoria.id)` asume que producto.categoria no es null. --%>
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
            <c:if test="${errores != null && errores.constainsKey('categoria')}">
                <div style="color:red">${errores.categoria}</div>
            </c:if>
        </div>

        <%-- Botón submit
             - Mostrar 'Editar' o 'Crear' según exista id en producto
             - NOTA: el input de submit en el código original está sin cerrar (faltante '>'), revisar para evitar romper el DOM. --%>
        <div class="row mb-2">
            <div>
                <input class="btn btn-primary" type="submit" value="${producto.id != null && producto.id > 0 ? 'Editar' : 'Crear'}"
            </div>
        </div>

        <%-- Campo oculto id para edición (si aplica) --%>
        <input type="hidden" name="id" value="${producto.id}">
    </form>
    </c:when>

    <%-- Sección: Usuarios --%>
    <c:when test="${tipo eq 'usuarios'}">
    <form action="${pageContext.request.contextPath}/usuarios/form" method="post">
        <%-- Campo: username (nombre completo)
             - name/id: username
             - Mostrar error asociado a 'username' --%>
        <div class="row mb-2">
            <label for="username" class="col-form-label col-sm-2">Nombre Completo</label>
            <div class="col-sm-4">
                <input type="text" name="username" id="username" value="${usuario.username}" class="form-control">
            </div>

            <c:if test="${errores != null && errores.containsKey('username')}">
                <div style="color:red">${errores.username}</div>
            </c:if>
        </div>

        <%-- Campo: email
             - name/id: email
             - Atención: en el original se usa `values="${usuario.email}"` (typo), debe ser `value` --%>
        <div class="row mb-2">
            <label for="email" class="col-form-label col-sm-2">Email</label>
            <div class="col-sm-4">
                <input type="email" name="email" id="email" values="${usuario.email}" class="form-control">
            </div>

            <c:if test="${errores != null && errores.containsKey('email')}">
                <div style="color:red">${errores.email}</div>
            </c:if>
        </div>

        <%-- Campo: password
             - name/id: password --%>
        <div class="row mb-2">
            <label for="password" class="col-form-label col-sm-2">Password</label>
            <div class="col-sm-4">
                <input type="password" name="password" id="password" value="${usuario.password}" class="form-control">
            </div>

            <c:if test="${errores != null && errores.containsKey('password')}">
                <div style="color:red">${errores.password}</div>
            </c:if>
        </div>

        <%-- Botón submit para usuarios --%>
        <div class="row mb-2">
            <div>
                <input class="btn btn-primary" type="submit" value="Crear">
            </div>
        </div>

        <%-- Campo oculto id para edición --%>
        <input type="hidden" name="id" value="${usuario.id}">

    </form>
    </c:when>
</c:choose>

<jsp:include page="layout/footer.jsp"/>