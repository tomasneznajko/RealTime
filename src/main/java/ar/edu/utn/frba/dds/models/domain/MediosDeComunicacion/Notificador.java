package ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion;

import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.incidentes.AperturaIncidente;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;

import java.util.*;

public class Notificador {
    private Set<Miembro> miembrosNotificados;
    public Notificador(){
        this.miembrosNotificados = new HashSet<>();
    }
    public List<Incidente> notificar(Miembro emisor, AperturaIncidente aperturaIncidente){
        List<Incidente> incidentes = new ArrayList<>();
        notificarComunidades(emisor,aperturaIncidente,incidentes);
        notificarInteresados(emisor,aperturaIncidente);
        miembrosNotificados.clear();
        return incidentes;
    }

    public void notificarComunidades(Miembro emisor, AperturaIncidente aperturaIncidente,List<Incidente> incidentes){

        for(Comunidad comunidad : emisor.getComunidades()){
            Incidente incidente = new Incidente();
            incidente.meAbro(emisor,aperturaIncidente);
            incidentes.add(incidente);

            Notificacion notificacion = new Notificacion();
            notificacion.crearNotificacion(incidente);
            filtrarYaNotificados(comunidad.getMiembros(),incidente)
                    .forEach(m->m.getMedioDeNotificacion().evaluarEnvioDeNotificacion(notificacion));
            comunidad.getIncidentes().add(incidente);
        }

    }

    public void notificarInteresados(Miembro emisor, AperturaIncidente aperturaIncidente){
        Incidente incidente = new Incidente();
        incidente.meAbro(emisor,aperturaIncidente);
        Notificacion notificacion = new Notificacion();
        notificacion.crearNotificacion(incidente);
        filtrarYaNotificados(aperturaIncidente.getPrestacionDeServicio().getInteresados(),incidente)
                .forEach(m->m.getMedioDeNotificacion().evaluarEnvioDeNotificacion(notificacion));
    }

    public List<Miembro> filtrarYaNotificados(List<Miembro> notificables,Incidente incidente){
        miembrosNotificados.addAll(notificables);
        notificables.removeIf(miembro -> !Objects.equals(miembro.getUsuario(), incidente.getAbridor().getUsuario()));
        return notificables;
    }

}
