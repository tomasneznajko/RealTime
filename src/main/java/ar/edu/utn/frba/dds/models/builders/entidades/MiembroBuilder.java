package ar.edu.utn.frba.dds.models.builders.entidades;

import ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;
import ar.edu.utn.frba.dds.models.excepciones.SinLocalizacionValida;
import ar.edu.utn.frba.dds.models.excepciones.SinUsuarioExcepcion;


public class MiembroBuilder {
    private Miembro miembro = new Miembro();

    public MiembroBuilder conCuenta(String usuario, String clave){
        this.miembro.setUsuario(usuario);
        return this;
    }
    public MiembroBuilder conLocalizacion(Localizacion localizacion){
        this.miembro.setLocalizacion(localizacion);
        return this;
    }

    public MiembroBuilder conMedioDeNotidicacion(MedioDeNotificacion medioDeNotificacion){
        this.miembro.setMedioDeNotificacion(medioDeNotificacion);
        return this;
    }

    public Miembro construir(){
        if(this.miembro.getUsuario() == null){
            throw new SinUsuarioExcepcion();
        }

        if(this.miembro.getLocalizacion() == null){
            throw new SinLocalizacionValida();
        }
        return this.miembro;
    }
}