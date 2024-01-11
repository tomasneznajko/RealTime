package ar.edu.utn.frba.dds.models.builders.puntajes;

import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.MiembroPuntaje;
import ar.edu.utn.frba.dds.models.excepciones.puntajes.SinIdValidoExcepcion;

public class MiembroPuntajeBuilder {
    private MiembroPuntaje miembroPuntaje = new MiembroPuntaje();

    public MiembroPuntajeBuilder conId(Long id){
        this.miembroPuntaje.id = Math.toIntExact(id);
        return this;
    }

    public MiembroPuntajeBuilder conPuntaje(double puntaje){
        this.miembroPuntaje.puntaje = puntaje;
        return this;
    }

    public MiembroPuntaje construir(){
        if(this.miembroPuntaje.id < 1){
            throw new SinIdValidoExcepcion();
        }

        return this.miembroPuntaje;
    }
}
