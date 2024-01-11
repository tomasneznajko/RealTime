package ar.edu.utn.frba.dds.server;
import ar.edu.utn.frba.dds.controllers.comunidadesController.ComunidadesController;
import ar.edu.utn.frba.dds.controllers.FactoryController;

import ar.edu.utn.frba.dds.controllers.comunidadesController.incidentes.IncidentesController;

import ar.edu.utn.frba.dds.controllers.EntidadControladoraController;

import ar.edu.utn.frba.dds.controllers.OrganismoDeControlController;
import ar.edu.utn.frba.dds.controllers.RankingController;
import ar.edu.utn.frba.dds.controllers.UsuariosController;
import ar.edu.utn.frba.dds.models.domain.usuario.TipoRol;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;



public class Router {

    public static void init() {

        Server.app().routes(() -> {

            get("/", (ctx) -> ctx.render("base.hbs"));
            get("/test",((IncidentesController) FactoryController.controller("Incidentes"))::test);
            get("/revisarIncidentes",((IncidentesController) FactoryController.controller("Incidentes"))::revisarIncidentes);
            post("/revisarIncidentes",((IncidentesController) FactoryController.controller("Incidentes"))::setearMapa );
            get("/login", ((UsuariosController) FactoryController.controller("Usuarios"))::login);
            post("/login", ((UsuariosController) FactoryController.controller("Usuarios"))::loginPost);
            get("/register", ((UsuariosController) FactoryController.controller("Usuarios"))::register);
            post("/register", ((UsuariosController) FactoryController.controller("Usuarios"))::registerPost);
            get("/usuarios", ((UsuariosController) FactoryController.controller("Usuarios"))::index, TipoRol.ADMINISTRADOR);
            get("/usuarios/{id}", ((UsuariosController) FactoryController.controller("Usuarios"))::mostrarUsuario);
            get("/cargar/organismosDeControl", ((OrganismoDeControlController) FactoryController.controller("OrganismosDeControl"))::cargar, TipoRol.ENTIDAD, TipoRol.ADMINISTRADOR);
            post("/cargar/organismosDeControl", ((OrganismoDeControlController) FactoryController.controller("OrganismosDeControl"))::cargarPost, TipoRol.ENTIDAD, TipoRol.ADMINISTRADOR);
            get("/cargar/entidadesControladoras", ((EntidadControladoraController) FactoryController.controller("EntidadesControladoras"))::cargar, TipoRol.ENTIDAD, TipoRol.ADMINISTRADOR);
            post("/cargar/entidadesControladoras", ((EntidadControladoraController) FactoryController.controller("EntidadesControladoras"))::cargarPost, TipoRol.ENTIDAD, TipoRol.ADMINISTRADOR);


            get("/ranking/cantIncidentes", ((RankingController) FactoryController.controller("Ranking/cantIncidentes"))::ranking);
            get("/ranking/gradoImpacto", ((RankingController) FactoryController.controller("Ranking/gradoImpacto"))::ranking);
            get("/ranking/promCierre", ((RankingController) FactoryController.controller("Ranking/promCierre"))::ranking);

            get("/usuario",((UsuariosController) FactoryController.controller("Usuarios")) :: show);
            get("/usuario/{id}/editar",((UsuariosController) FactoryController.controller("Usuarios")) :: edit);
            post("/usuario",((UsuariosController) FactoryController.controller("Usuarios")) :: update);
            post("/usuarios/{id}/permiso",((UsuariosController) FactoryController.controller("Usuarios")) :: updateRol);
            get("/usuario/{id}/intereses",((UsuariosController) FactoryController.controller("Usuarios"))::show);
            post("/usuario/{id}/intereses",((UsuariosController) FactoryController.controller("Usuarios"))::show);

            path("comunidades", () -> {
                get(((ComunidadesController) FactoryController.controller("Comunidades"))::index);
                post(((ComunidadesController) FactoryController.controller("Comunidades"))::join);
                get("analizadas", ((ComunidadesController) FactoryController.controller("Comunidades"))::analysis);
                get("{idComunidad}/incidentes",((IncidentesController) FactoryController.controller("Incidentes"))::index);
                get("{idComunidad}/incidentes/{idIncidente}", ((IncidentesController) FactoryController.controller("Incidentes"))::show);
                post("fusionar", ((ComunidadesController) FactoryController.controller("Comunidades"))::fusion);
                post("{idComunidad}/incidentes/{idIncidente}", ((IncidentesController) FactoryController.controller("Incidentes"))::close);

            });
            path("incidentes", () -> {
                get(((IncidentesController) FactoryController.controller("Incidentes"))::create);
                get("revisarIncidentes", ((IncidentesController) FactoryController.controller("Incidentes"))::revisarIncidentes);
                post(((IncidentesController) FactoryController.controller("Incidentes"))::save);
            });

            post("/ubicacion", ctx -> {
                // Obtén el cuerpo de la solicitud como una cadena JSON
                String body = ctx.body();

                // Convierte la cadena JSON en un objeto Java usando una biblioteca como Jackson o Gson
                ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper
                Map<String, String> ubicacionData = objectMapper.readValue(body, new TypeReference<Map<String, String>>() {});

                String latitud = ubicacionData.get("latitud");
                String longitud = ubicacionData.get("longitud");

                // Haz algo con la ubicación (almacenar en una base de datos, procesar, etc.)
                System.out.println("Ubicación recibida - Latitud: " + latitud + ", Longitud: " + longitud);

                // Responde al cliente
                ctx.json(new HashMap<String, String>() {
                    {
                        put("mensaje", "Ubicación recibida correctamente");
                    }});
            });
        });


    }
}
