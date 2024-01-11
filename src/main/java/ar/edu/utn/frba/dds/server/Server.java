package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.models.domain.usuario.Rol;
import ar.edu.utn.frba.dds.models.domain.usuario.TipoRol;
import ar.edu.utn.frba.dds.repositories.RepoDeRoles;
import ar.edu.utn.frba.dds.server.handlers.AppHandlers;
//import ar.edu.utn.frba.dds.server.middlewares.AuthMiddleware;
import ar.edu.utn.frba.dds.server.middlewares.AuthMiddleware;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;

import java.io.IOException;
import java.util.function.Consumer;

public class Server {
    private static Javalin app = null;

    public static Javalin app() {
        if(app == null)
            throw new RuntimeException("App no inicializada");
        return app;
    }

    public static void init() {
        if(app == null) {
            Integer port = Integer.parseInt(System.getProperty("port", "7777"));
            app = Javalin.create(config()).start(port);
            initTemplateEngine();
            initRoles();
            AppHandlers.applyHandlers(app);
            Router.init();
        }
    }

    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/public";
            });
            AuthMiddleware.apply(config);
        };
    }


    private static void initTemplateEngine() {
        JavalinRenderer.register(
                (path, model, context) -> { // Función que renderiza el template
                    Handlebars handlebars = new Handlebars();
                    handlebars.registerHelper("inc", new Helper<Integer>() {
                       public Integer apply(Integer value, Options options) {
                           return value + 1;
                       }
                    });
                    Template template = null;
                    try {
                        template = handlebars.compile(
                                "templates/" + path.replace(".hbs",""));
                        return template.apply(model);
                    } catch (IOException e) {
                        e.printStackTrace();
                        context.status(HttpStatus.NOT_FOUND);
                        return "No se encuentra la página indicada...";
                    }
                }, ".hbs" // Extensión del archivo de template
        );
    }

    private static void initRoles(){
        RepoDeRoles repo = new RepoDeRoles();
        Rol rolAdministrador = repo.buscarPorTipoRol(TipoRol.ADMINISTRADOR);
        if(rolAdministrador == null){
            rolAdministrador = new Rol();
            rolAdministrador.setNombre(TipoRol.ADMINISTRADOR.toString());
            rolAdministrador.setTipo(TipoRol.ADMINISTRADOR);
            repo.agregar(rolAdministrador);

            Rol rolNormal = new Rol();
            rolNormal.setNombre(TipoRol.NORMAL.toString());
            rolNormal.setTipo(TipoRol.NORMAL);
            repo.agregar(rolNormal);

            Rol rolEntidad = new Rol();
            rolEntidad.setNombre(TipoRol.ENTIDAD.toString());
            rolEntidad.setTipo(TipoRol.ENTIDAD);
            repo.agregar(rolEntidad);
        }
    }
}
