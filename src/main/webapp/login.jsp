<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%--
  login.jsp
  ----------
  Página JSP que presenta el formulario de autenticación (login).

  Requisitos / expectativas:
  - Se incluye el fragmento de cabecera mediante "layout/header.jsp" y pie con "layout/footer.jsp".
  - Esta vista espera que el controlador/servlet establezca el atributo "title" antes del forward
    (por ejemplo: req.setAttribute("title", "Login");).
  - El formulario realiza POST a "${pageContext.request.contextPath}/login", que corresponde al
    servlet que procesa la autenticación (en este proyecto, LogingServlet).
  - Tras autenticación exitosa, el servlet debería crear una sesión y almacenar el username
    (p. ej. session.setAttribute("username", username)) para que la vista de bienvenida muestre el usuario.

  Notas de seguridad y mejoras recomendadas:
  - Asegúrate de servir esta página y procesar el formulario a través de HTTPS para proteger credenciales.
  - Añadir protección CSRF: incluir un token en la sesión y un campo oculto en el formulario que el servidor valide.
  - Las contraseñas deben compararse mediante hashes seguros en el servidor; no loguear ni almacenar la contraseña en texto claro.
  - Considerar añadir validación mínima del lado cliente (atributos HTML5 como 'required', 'minlength') y validación robusta en el servidor.
  - Evitar que el navegador auto-complete campos sensibles si la política de seguridad lo requiere (atributo autocomplete="off" en el form o inputs, según políticas).
--%>

<jsp:include page="layout/header.jsp"/>

<h3>${title}</h3>

<%--
  Formulario de login:
  - action: envía al servlet montado en /login (usa contextPath para que funcione en diferentes contextos).
  - method: POST (correcto para credenciales).
  - Campos esperados por el servlet: "username" y "password".
--%>
<form action="${pageContext.request.contextPath}/login" method="post">
    <div class="row my-2">
        <label for="username" class="form-label">USERNAME</label>
        <div>
            <%--
              Nota sobre name vs id:
              - 'id' se usa para asociación con la etiqueta <label> y para manipulación desde JS/CSS.
              - 'name' es el nombre del parámetro enviado en la petición HTTP (importante para el backend).
              - Aquí ambos están sincronizados: id="username" y name="username".
              - Puede añadirse 'required' para validación HTML5: <input ... required>
            --%>
            <input type="text" name="username" id="username" class="form-control">
        </div>
    </div>
    <div class="row my-2">
        <label for="password" class="form-label">PASSWORD</label>
        <div>
            <%--
              Campo de contraseña:
              - El tipo 'password' oculta el texto en el cliente.
              - No confiar en la validación del cliente para seguridad; siempre validar/limitar en servidor.
              - Considerar atributos adicionales: autocomplete="current-password", minlength o patrón según política.
            --%>
            <input type="password" name="password" id="password" class="form-control">
        </div>
    </div>
    <div class="row my-2">
        <%--
          Botón de envío:
          - Envía username y password al endpoint definido en action.
          - El servlet debe responder con redirección o con error 401 según el resultado.
        --%>
        <input type="submit" value="Login" class= "btn btn-primary">
    </div>

<jsp:include page="layout/footer.jsp"/>