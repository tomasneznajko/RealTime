/*
package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.comunidades.gradosDeConfianza.Puntaje;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.RequestComunidadPuntaje;
import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.ServicioCalculador;
import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.ComunidadPuntaje;
import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.IncidentePuntaje;
import ar.edu.utn.frba.dds.models.domain.servicios.*;
import ar.edu.utn.frba.dds.repositories.RepoDeComunidades;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class servicio_calculadorPuntaje {
    private Miembro miembro1;
    private Miembro miembro2;
    private Servicio servicio;
    private PrestacionDeServicio prestacionDeServicio;
    private Incidente incidente;
    private IncidentePuntaje incidentePuntaje;
    private Comunidad comunidad;
    private ComunidadPuntaje comunidadPuntaje;
    private RequestComunidadPuntaje requestComunidadPuntaje;
    private RepoDeComunidades repoDeComunidades;
    @BeforeEach
    public void init() throws IOException {
        this.servicio = new Banio();
        ((Banio)this.servicio).setGenero(Genero.HOMBRE);
        ((Banio)this.servicio).setDiscapacitado(true);
        this.prestacionDeServicio = new PrestacionDeServicio();
        this.prestacionDeServicio.setId(1L);
        this.prestacionDeServicio.setServicio(this.servicio);
        this.prestacionDeServicio.setCantidad(3);
        this.prestacionDeServicio.setFunciona(true);

        this.miembro1 = new Miembro();
        this.miembro1.setId(1L);
        Puntaje puntaje = new Puntaje();
        puntaje.setValor(3.00);
        this.miembro1.setPuntaje(puntaje);

        this.miembro2 = new Miembro();
        this.miembro2.setId(2L);
        Puntaje puntaje2 = new Puntaje();
        puntaje2.setValor(2.00);
        this.miembro2.setPuntaje(puntaje2);

        this.incidente = new Incidente();
        this.incidente.setIdAmigable(1L);
        this.incidente.setAbridor(miembro1);
        this.incidente.setCerrador(miembro2);
        this.incidente.setPrestacionDeServicio(prestacionDeServicio);
        this.incidente.setFachaYHoraApertura(LocalDateTime.now());
        this.incidente.setFechaYHoraCierre(LocalDateTime.of(2023,10,20,12,10));
        this.incidentePuntaje = this.incidente.incidentePuntaje();

        this.comunidad = new Comunidad();
        this.comunidad.setIdAmigable(1L);
        Puntaje puntajeComunidad = new Puntaje();
        puntajeComunidad.setValor(3.00);
        this.comunidad.setPuntaje(puntajeComunidad);
        this.comunidad.agregarUsuarios(miembro1,miembro2);
        this.comunidadPuntaje = this.comunidad.comunidadPuntaje();

        this.requestComunidadPuntaje = new RequestComunidadPuntaje();
        List<IncidentePuntaje> incidentesPuntaje = new ArrayList<>();
        incidentesPuntaje.add(incidentePuntaje);


        this.requestComunidadPuntaje.setComunidadPuntaje(this.comunidad.comunidadPuntaje());
        this.requestComunidadPuntaje.setIncidentesPuntaje(this.comunidad.getIncidentes().stream().map(Incidente::incidentePuntaje).toList());

    }
    @Test
    @DisplayName("Generar Request")
    public void generarRequest() throws IOException {
        ComunidadPuntaje comunidadPuntajeResponse = ServicioCalculador.getInstance().comunidadPuntaje(this.requestComunidadPuntaje);
        this.comunidad.actualizarPuntajes(comunidadPuntajeResponse);
        Assert.assertEquals(comunidad.getPuntaje().getValor(),2.0);
    }

}
*/
