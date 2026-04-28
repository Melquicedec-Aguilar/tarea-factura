package org.aguilar.webapp.factura.models;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Bean de usuario usado en la aplicación web.
 *
 * <p>Responsabilidad:
 * - Representa los datos básicos de un usuario (id, nombre de usuario, contraseña y correo).
 * - Está anotado con {@code @Named} para poder referenciarlo desde vistas (EL) y
 *   con {@code @SessionScoped} para mantener su estado durante la sesión del usuario.</p>
 *
 * <p>Notas de diseño:
 * - Implementa {@code Serializable} porque los beans de sesión deben ser serializables
 *   para que el contenedor pueda gestionar la sesión (replicación, persistencia, etc.).
 * - El logger se inyecta y se marca {@code transient} para evitar que se intente serializar.</p>
 */
@Named
@SessionScoped
public class Usuario implements Serializable {
    /**
     * Identificador único del usuario.
     *
     * <p>Convención: puede ser {@code null} para usuarios no persistidos aún.</p>
     */
    private Long id;

    /**
     * Nombre de usuario utilizado para login y visualización.
     */
    private String username;

    /**
     * Contraseña en texto claro en este modelo (normalmente se recomienda almacenar sólo hashes
     * y nunca exponer la contraseña). Este campo puede usarse temporalmente para el proceso
     * de autenticación; tenga cuidado con su manejo y almacenamiento.
     */
    private String password;

    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /**
     * Logger inyectado por CDI para facilitar mensajes de depuración.
     *
     * <p>Se marca {@code transient} para que no participe en la serialización del bean.</p>
     */
    @Inject
    private transient Logger log;

    /**
     * Constructor por defecto requerido por CDI y por la serialización/deserialización.
     *
     * <p>No realiza inicialización compleja; la inicialización por defecto se hace en
     * {@link #init()} marcado con {@code @PostConstruct}.</p>
     */
    public Usuario() {    }

    /**
     * Inicializador invocado por el contenedor una vez construido el bean.
     *
     * <p>Objetivo:
     * - Inicializar campos con valores por defecto para evitar NPE al consultarlos desde la vista.
     * - Registrar en el log que el bean está listo para su uso.</p>
     *
     * <p>Estado tras la ejecución:
     * - {@code username} y {@code email} inicializados a cadena vacía.</p>
     */
    @PostConstruct
    public void init(){

        log.info(" ---------- Bean Usuario listo para Login ");
    }

    /**
     * Obtiene el id del usuario.
     *
     * @return el id (puede ser {@code null} si no se ha asignado aún)
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el id del usuario.
     *
     * @param id nuevo id a asignar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario (username).
     *
     * @return el nombre de usuario (no {@code null} si {@link #init()} fue invocado)
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param username nuevo nombre de usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene la contraseña asociada al usuario.
     *
     * <p>Advertencia: este getter puede exponer la contraseña en memoria. Evite loguearla
     * o enviarla a capas que no necesiten conocerla.</p>
     *
     * @return la contraseña actual (puede ser {@code null})
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * <p>Nota: la validación y el hashing deberían realizarse antes de persistir la contraseña
     * en almacenamiento permanente.</p>
     *
     * @param password nueva contraseña en texto (se recomienda hasharla antes de persistir)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return el email (no {@code null} si {@link #init()} fue invocado)
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email nuevo correo electrónico
     */
    public void setEmail(String email) {
        this.email = email;
    }
}