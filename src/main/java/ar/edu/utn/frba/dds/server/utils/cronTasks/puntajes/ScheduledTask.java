package ar.edu.utn.frba.dds.server.utils.cronTasks.puntajes;

import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.RequestComunidadPuntaje;
import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.ServicioCalculador;
import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.ComunidadPuntaje;
import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.IncidentePuntaje;
import ar.edu.utn.frba.dds.repositories.RepoDeComunidades;
import ar.edu.utn.frba.dds.repositories.RepoDeIncidentes;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ScheduledTask implements Runnable {
    private final RepoDeComunidades repoDeComunidades;

    public ScheduledTask(RepoDeComunidades repoDeComunidades) {
        this.repoDeComunidades = repoDeComunidades;
    }

    @Override
    public void run() {
        List<Comunidad> comunidades = repoDeComunidades.buscarTodos();
        System.out.println(comunidades.stream().map(Comunidad::getMiembros).toList());


        System.out.println(comunidades.stream().map(Comunidad::getIncidentes).toList());

        try {
            actualizarComunidades(obtenerRequests(comunidades), comunidades);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        comunidades.forEach(repoDeComunidades::agregar);

        System.out.println("Agregamos a la base de datos");
    }
    public void actualizarComunidades(@NotNull List<RequestComunidadPuntaje> requestComunidadesPuntajes, List<Comunidad> comunidades) throws IOException {
        for(RequestComunidadPuntaje requestComunidadPuntaje: requestComunidadesPuntajes){
            ComunidadPuntaje comunidadPuntajeActualizada = ServicioCalculador.getInstance().comunidadPuntaje(requestComunidadPuntaje);
            System.out.println("Comunidad "+ comunidadPuntajeActualizada.id+" con " + comunidadPuntajeActualizada.puntaje);

            comunidades.stream().filter(comunidad -> comunidad.getIdAmigable() == comunidadPuntajeActualizada.id).findFirst().ifPresent(
                    comunidad -> comunidad.actualizarPuntajes(comunidadPuntajeActualizada)
            );
        }
    }
/*    public void actualizarComunidades2(List<RequestComunidadPuntaje> requestComunidadesPuntajes, List<Comunidad> comunidades){
        List<CompletableFuture<Void>> futures = requestComunidadesPuntajes.stream()
                .map(request -> CompletableFuture.runAsync(() -> {
                    try {
                        ComunidadPuntaje comunidadPuntaje = ServicioCalculador.getInstance().comunidadPuntaje(request);
                        actualizarPuntaje(comunidadPuntaje, comunidades);
                        System.out.println("Estamos llamando a la API");

                    } catch (IOException e) {
                        // Manejar la excepción si es necesario
                        e.printStackTrace();
                    }
                }))
                .toList();
        // Esperar a que todas las operaciones asíncronas se completen
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allOf.join();
    }

    public void actualizarPuntaje(ComunidadPuntaje comunidadPuntajeActualizada, List<Comunidad> comunidades) {
        comunidades.stream().filter(comunidad -> comunidad.getId() == comunidadPuntajeActualizada.id).findFirst().ifPresent(
                comunidad -> comunidad.actualizarPuntajes(comunidadPuntajeActualizada)
        );

    }*/

    public List<RequestComunidadPuntaje> obtenerRequests(List<Comunidad> comunidades) {
        System.out.println(comunidades);

        List<RequestComunidadPuntaje> requestComunidadPuntajes = new ArrayList<>();

        for(Comunidad comunidad : comunidades){
            RequestComunidadPuntaje requestComunidadPuntaje = new RequestComunidadPuntaje();

            requestComunidadPuntaje.setComunidadPuntaje(comunidad.comunidadPuntaje());
            requestComunidadPuntaje.setIncidentesPuntaje(comunidad.getIncidentes().stream().filter(incidente -> !incidente.getAbierto()).map(Incidente::incidentePuntaje).toList());
            requestComunidadPuntajes.add(requestComunidadPuntaje);

        }

        return requestComunidadPuntajes;

    }
}
