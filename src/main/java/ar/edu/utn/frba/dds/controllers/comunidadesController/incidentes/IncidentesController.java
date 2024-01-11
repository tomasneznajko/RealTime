package ar.edu.utn.frba.dds.controllers.comunidadesController.incidentes;

import ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion.Notificador;
import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.incidentes.AperturaIncidente;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.domain.incidentes.IncidenteResumido;
import ar.edu.utn.frba.dds.models.domain.servicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;
import ar.edu.utn.frba.dds.repositories.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class IncidentesController {
    private RepoDeIncidentes repoDeIncidentes;
    private RepoDeComunidades repoDeComunidades;
    private RepoDeMiembros repoDeMiembros;
    private RepoDePrestacionDeServicio repoDePrestacionDeServicio;
    private RepoDeEstablecimientos repoDeEstablecimientos;

    public IncidentesController(RepoDeComunidades repoDeComunidades,RepoDeIncidentes repoDeIncidentes,RepoDePrestacionDeServicio repoDePrestacionDeServicio,RepoDeEstablecimientos repoDeEstablecimientos, RepoDeMiembros repoDeMiembros) {
        this.repoDeIncidentes = repoDeIncidentes;
        this.repoDeComunidades = repoDeComunidades;
        this.repoDePrestacionDeServicio = repoDePrestacionDeServicio;
        this.repoDeEstablecimientos = repoDeEstablecimientos;
        this.repoDeMiembros = repoDeMiembros;
    }


    public void index(Context context) {
        Comunidad comunidad = this.repoDeComunidades.buscarPorId(UUID.fromString(context.pathParam("idComunidad")));
        Map<String, Object> model = new HashMap<>();
        model.put("comunidad", comunidad);
        model.put("incidentes", comunidad.getIncidentes());
        context.render("comunidades/incidentes/incidentes.hbs", model);
    }




    public void show(Context context) {
        Comunidad comunidad = this.repoDeComunidades.buscarPorId(UUID.fromString(context.pathParam("idComunidad")));
        Incidente incidente = this.repoDeIncidentes.buscarPorId(UUID.fromString(context.pathParam("idIncidente")));
        Map<String, Object> model = new HashMap<>();
        model.put("comunidad",comunidad);
        model.put("incidente", incidente);
        context.render("comunidades/incidentes/incidente.hbs", model);
    }

    public void revisarIncidentes(Context context) {

        List<Incidente> incidentes = this.repoDeIncidentes.buscarTodos();
        Map<String, Object> model = new HashMap<>();
        model.put("incidente", incidentes);
        context.render("incidentes/revisionDeIncidentes.hbs", model);
    }

    

    public void setearMapa(Context ctx) throws JsonProcessingException {

        // Obtén el cuerpo de la solicitud como una cadena JSON
        String body = ctx.body();

        // Convierte la cadena JSON en un objeto Java usando una biblioteca como Jackson o Gson
        ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper
        Map<String, String> ubicacionData = objectMapper.readValue(body, new TypeReference<Map<String, String>>() {});

        String latitud = ubicacionData.get("latitud");
        String longitud = ubicacionData.get("longitud");

        // Haz algo con la ubicación (almacenar en una base de datos, procesar, etc.)
        System.out.println("Ubicación recibida - Latitud: " + latitud + ", Longitud: " + longitud);

        Double latitudRecibida = Double.parseDouble(latitud);
        Double longitudRecibida = Double.parseDouble(longitud);
        System.out.println("Codigo postal calculado:" + latitudRecibida + "y "+longitudRecibida);


        LocalizacionLiviana localizacionRecibida = obtenerLocalizacion(latitudRecibida, longitudRecibida   );


        System.out.println("Localizacion recibida: -Localidad: " + localizacionRecibida.getLocalidad() + " -Provincia: " + localizacionRecibida.getProvincia());

        List<IncidenteResumido> incidentesResumidos = this.repoDeIncidentes.obtenerIncidentesPorLocalidadYProvincia(
                        localizacionRecibida.localidad,localizacionRecibida.provincia);
        /*Test*/

        IncidenteResumido incidenteResumido = new IncidenteResumido();

        incidenteResumido.setObservaciones("No funciona, nunca vas a programar en tu vida");
        List<Comunidad> comunidades = new ArrayList<>();
       // comunidades.add(this.repoDeComunidades.buscarPorId(1L));
      //  comunidades.add(this.repoDeComunidades.buscarPorId(2L));
        incidenteResumido.setComunidades(comunidades);
        incidenteResumido.setEstablecimiento(this.repoDeEstablecimientos.buscarPorId(21L));

        System.out.println(incidenteResumido);

        incidentesResumidos.add(incidenteResumido);



        //--------------------------



        // Responde al cliente
        ctx.json(new HashMap<String, String>() {
            {
                put("mensaje", "Ubicación recibida correctamente");
            }});



    }

    private LocalizacionLiviana obtenerLocalizacion(Double latitudRecibida, Double longitudRecibida) {

        try {
            // Coordenadas de interés
            double latitud = latitudRecibida;
            double longitud = longitudRecibida;

            // Definir la URL de la API de Geocodificación de Google Maps
            String apiKey = "AIzaSyChSOMgn1_ilWUd06Y1i_PYrsO5T4sRHOs";
            String urlString = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
                    latitud + "," + longitud +
                    "&key=" + apiKey;

            // Crear un objeto URL y abrir la conexión
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud y obtener la respuesta
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            // Leer la respuesta de la API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            System.out.println("Respuesta de la API: " + response.toString());

            // Analizar la respuesta JSON para obtener el código postal
            // Analizar la respuesta JSON para obtener la localidad y la provincia
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray results = jsonResponse.getJSONArray("results");

            LocalizacionLiviana localizacion = new LocalizacionLiviana();

            String localidad = null;
            String provincia = null;

            Boolean localidadSeteada = false;
            // Iterar sobre los resultados y buscar la localidad y la provincia
            for (int i = 0; i < results.length(); i++) {
                JSONObject resultado = results.getJSONObject(i);
                JSONArray addressComponents = resultado.getJSONArray("address_components");



            for (int j = 0; j < addressComponents.length(); j++) {
                JSONObject component = addressComponents.getJSONObject(j);
                JSONArray componentTypes = component.getJSONArray("types");


                if (componentTypes.toString().contains("sublocality_level_1") && !localidadSeteada) {
                    localidad = component.getString("long_name");
                    localidadSeteada = true;
                    System.out.println("Localidad 1: " + localidad);
                } else if (componentTypes.toString().contains("sublocality")&& !localidadSeteada) {
                    localidad = component.getString("long_name");
                    localidadSeteada = true;
                    System.out.println("Localidad 2: " + localidad);
                } else if(componentTypes.toString().contains("locality") && !localidadSeteada){
                    localidad = component.getString("long_name");
                    localidadSeteada = true;
                    System.out.println("Localidad 3: " + localidad);
                }
                if (componentTypes.toString().contains("administrative_area_level_1")) {
                    provincia = component.getString("short_name");
                }

            }

                // Si se encontraron tanto la localidad como la provincia, salir del bucle
                if (localidad != null && provincia != null) {
                    localizacion.setLocalidad(localidad);
                    localizacion.setProvincia(provincia);
                    break;
                }
            }

// Verificar si se encontraron la localidad y la provincia
            if (localizacion.getLocalidad() != null && localizacion.getProvincia() != null) {
                // Hacer algo con localizacion.getLocalidad() y localizacion.getProvincia()
                return localizacion;
            } else {
                // Manejar el caso en el que no se encontraron la localidad y la provincia
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    public void create(Context context) {
/*        Usuario usuarioLogueado = super.usuarioLogueado(context);

        if(usuarioLogueado == null || !usuarioLogueado.getRol().tenesPermiso("crear_servicios")) {
            throw new AccessDeniedException();
        }*/
        List<PrestacionDeServicio> prestacionesDeServicio = this.repoDePrestacionDeServicio.buscarPorFuncionamiento(false);
        List<Establecimiento> establecimientos = this.repoDeEstablecimientos.buscarTodos();

        Map<String, Object> model = new HashMap<>();
        model.put("prestaciones", prestacionesDeServicio);
        model.put("establecimientos",establecimientos);
        context.render("aperturaIncidentes/openIncidentes.hbs", model);
    }


    public void save(Context context) {
        AperturaIncidente aperturaIncidente = new AperturaIncidente();
        this.asignarParametros(aperturaIncidente, context);

        Miembro miembroActual = this.repoDeMiembros.buscarPorId(context.sessionAttribute("usuario_id"));
        List<Incidente> incidentes = miembroActual.abrirIncidente(aperturaIncidente,new Notificador());


        for(Incidente incidente : incidentes){
            this.repoDeIncidentes.agregar(incidente);
        }

        context.status(HttpStatus.CREATED);
        context.redirect("/comunidades");
    }

    public void close(Context context){
        Incidente incidente = this.repoDeIncidentes.buscarPorId(UUID.fromString(context.pathParam("idIncidente")));
        Comunidad comunidad = this.repoDeComunidades.buscarPorId(UUID.fromString(context.pathParam("idComunidad")));
        comunidad.cerrarIncidente(comunidad.getMiembros().get(0),incidente);
        this.repoDeIncidentes.modificar(incidente);

        context.status(HttpStatus.CREATED);
        context.redirect("/comunidades/"+comunidad.getId()+"/incidentes");
    }


    private void asignarParametros(AperturaIncidente aperturaIncidente, Context context) {
        if(!Objects.equals(context.formParam("prestacion"), "")) {
            PrestacionDeServicio prestacionDeServicio = this.repoDePrestacionDeServicio.buscarPorId(Long.valueOf(context.formParam("prestacion")));
            aperturaIncidente.setPrestacionDeServicio(prestacionDeServicio);
            System.out.println(prestacionDeServicio);
        }

        if(!Objects.equals(context.formParam("establecimiento"), "")) {
            Establecimiento establecimiento = this.repoDeEstablecimientos.buscarPorId(Long.valueOf(context.formParam("establecimiento")));
            aperturaIncidente.setEstablecimiento(establecimiento);
            System.out.println(establecimiento);
        }

        if(!Objects.equals(context.formParam("observaciones"), "")){
            aperturaIncidente.setObservaciones(context.formParam("observaciones"));
        }


    }

    public void test(Context context) {
        List<IncidenteResumido> incidentesResumidos = this.repoDeIncidentes.obtenerIncidentesPorLocalidadYProvincia(
                "Floresta","CABA");
        /*Test*/

        IncidenteResumido incidenteResumido = new IncidenteResumido();

        incidenteResumido.setObservaciones("No funciona, nunca vas a programar en tu vida");
        List<Comunidad> comunidades = new ArrayList<>();
        //comunidades.add(this.repoDeComunidades.buscarPorId(1L));
        //comunidades.add(this.repoDeComunidades.buscarPorId(2L));
        incidenteResumido.setComunidades(comunidades);
        incidenteResumido.setEstablecimiento(this.repoDeEstablecimientos.buscarPorId(21L));

        System.out.println(incidenteResumido);

        incidentesResumidos.add(incidenteResumido);

        Map<String, Object> model = new HashMap<>();
        model.put("incidentes", incidentesResumidos);

        System.out.println(incidentesResumidos);
        context.render("incidentes/pruebaIncidenteResumido.hbs", model);

    }
}
