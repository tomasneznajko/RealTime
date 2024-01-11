package ar.edu.utn.frba.dds.models.builders.entidades;

import ar.edu.utn.frba.dds.models.excepciones.SinLocalizacionValida;
import ar.edu.utn.frba.dds.models.excepciones.SinNombreExcepcion;
import ar.edu.utn.frba.dds.models.excepciones.SinTipoExcepcion;
import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.TipoEstablecimiento;

import java.util.HashSet;

public class EstablecimientoBuilder {
    private Establecimiento establecimiento = new Establecimiento();

    public EstablecimientoBuilder conNombre(String nombre){
        this.establecimiento.setNombre(nombre);
        return this;
    }
    public EstablecimientoBuilder conTipo(TipoEstablecimiento tipoEstablecimiento){
        this.establecimiento.setTipoEstablecimiento(tipoEstablecimiento);
        return this;
    }
    public EstablecimientoBuilder conLocalizacion(Localizacion localizacion){
        this.establecimiento.setLocalizacion(localizacion);
        return this;
    }
    public Establecimiento construir(){
        if(this.establecimiento.getNombre() == null){
            throw new SinNombreExcepcion();
        }
        if(this.establecimiento.getTipoEstablecimiento() == null){
            throw new SinTipoExcepcion();
        }
        if(this.establecimiento.getLocalizacion() == null){
            throw new SinLocalizacionValida();
        }
        this.establecimiento.setPrestacionesDeServicios(new HashSet<>());
        return this.establecimiento;
    }


}
