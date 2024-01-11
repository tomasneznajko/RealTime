package ar.edu.utn.frba.dds.models.builders.puntajes;

import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.ComunidadPuntaje;
import ar.edu.utn.frba.dds.models.excepciones.puntajes.SinIdValidoExcepcion;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ComunidadPuntajeBuilder {
    private ComunidadPuntaje comunidadPuntaje = new ComunidadPuntaje();

    public ComunidadPuntajeBuilder conId(Long id){
        this.comunidadPuntaje.id = id;
        return this;
    }

    public ComunidadPuntajeBuilder conPuntaje(double puntaje){
        this.comunidadPuntaje.puntaje = puntaje;
        return this;
    }

    public ComunidadPuntajeBuilder conMiembros(List<Miembro> miembros) {
       this.comunidadPuntaje.miembros = miembros.stream().map(Miembro::miembroPuntaje).collect(Collectors.toList());
       return this;
    }

    public ComunidadPuntaje construir(){
        if(this.comunidadPuntaje.id == 0){
            throw new SinIdValidoExcepcion();
        }

        return this.comunidadPuntaje;
    }


}
