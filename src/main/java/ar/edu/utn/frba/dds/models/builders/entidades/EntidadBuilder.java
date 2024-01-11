package ar.edu.utn.frba.dds.models.builders.entidades;

import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.TipoEstablecimiento;
import ar.edu.utn.frba.dds.models.excepciones.SinLocalizacionValida;
import ar.edu.utn.frba.dds.models.excepciones.SinNombreExcepcion;

import java.util.ArrayList;


public class EntidadBuilder {
    private Entidad entidad = new Entidad();

    public EntidadBuilder conNombre(String nombre){
        this.entidad.setNombre(nombre);
        return this;
    }

    public EntidadBuilder conLocalizacion(Localizacion localizacion){
        this.entidad.setLocalizacion(localizacion);
        return this;
    }

    public EntidadBuilder conTipo(TipoEstablecimiento tipoEstablecimiento) {
        this.entidad.setTipoEstablecimientos(tipoEstablecimiento);
        return this;
    }
    public Entidad construir(){
        if(this.entidad.getNombre() == null){
            throw new SinNombreExcepcion();
        }

        if(this.entidad.getLocalizacion() == null){
            throw new SinLocalizacionValida();
        }
        this.entidad.setEstablecimientos(new ArrayList<>());
        return this.entidad;
    }


}
