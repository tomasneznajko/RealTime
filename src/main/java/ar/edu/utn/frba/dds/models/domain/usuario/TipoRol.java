package ar.edu.utn.frba.dds.models.domain.usuario;

import io.javalin.security.RouteRole;

public enum TipoRol implements RouteRole {
    ADMINISTRADOR,
    NORMAL,
    ENTIDAD
}
