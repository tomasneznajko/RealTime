
package ar.edu.utn.frba.dds.server.middlewares;

import ar.edu.utn.frba.dds.models.domain.usuario.TipoRol;
import ar.edu.utn.frba.dds.server.exceptions.AccessDeniedException;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;


public class AuthMiddleware {

    private static final String[] rutasPermitidas = {"/","/login", "/register","/usuario/\\d+","/usuario/\\d+/editar"};


    public static void apply(JavalinConfig config) {
        config.accessManager(((handler, context, routeRoles) -> {

            if(context.sessionAttribute("usuario_id") == null && !rutaNoRequiereAutenticacion(context.path())){
                context.redirect("/login");
                return;
            }

            TipoRol userRole = getUserRoleType(context);

            if(routeRoles.size() == 0 || routeRoles.contains(userRole)) {
                handler.handle(context);
            }
            else {
                throw new AccessDeniedException();
            }
        }));
    }

    private static TipoRol getUserRoleType(Context context) {
        String tipoRolValue = context.sessionAttribute("tipo_rol");

        return tipoRolValue != null ? TipoRol.valueOf(tipoRolValue) : null;
    }

    private static boolean rutaNoRequiereAutenticacion(String path) {
        for (String ruta : rutasPermitidas) {
            if (path.matches(ruta)) {
                return true;
            }
        }
        return false;
    }


}

