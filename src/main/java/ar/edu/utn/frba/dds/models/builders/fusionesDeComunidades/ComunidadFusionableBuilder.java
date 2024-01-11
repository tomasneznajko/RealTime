package ar.edu.utn.frba.dds.models.builders.fusionesDeComunidades;

import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.comunidades.PropuestaFusion;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities.ComunidadFusionable;
import ar.edu.utn.frba.dds.models.domain.servicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;
import ar.edu.utn.frba.dds.models.excepciones.puntajes.SinIdValidoExcepcion;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ComunidadFusionableBuilder {
    private ComunidadFusionable comunidadFusionable = new ComunidadFusionable();

    public ComunidadFusionableBuilder conId(Long id){
        this.comunidadFusionable.id = id;
        return this;
    }

    public ComunidadFusionableBuilder conEstablecimientos(List<Establecimiento> establecimientos){
        this.comunidadFusionable.establecimientos = establecimientos.stream().map(establecimiento -> Math.toIntExact(establecimiento.getId())).collect(Collectors.toList());
        return this;
    }

    public ComunidadFusionableBuilder conServicios(List<PrestacionDeServicio> prestacionDeServicios) {
        this.comunidadFusionable.servicios = prestacionDeServicios.stream().map(servicio -> Math.toIntExact(servicio.getId())).collect(Collectors.toList());
        return this;
    }

    public ComunidadFusionableBuilder conIncidentes(List<Incidente> incidentes) {
        this.comunidadFusionable.incidentes = incidentes.stream().map(Incidente::getIdAmigable).collect(Collectors.toList());
        return this;
    }

    public ComunidadFusionableBuilder conUsuarios(List<Miembro> usuarios) {
        this.comunidadFusionable.usuarios = usuarios.stream().map(usuario -> Math.toIntExact(usuario.getId())).collect(Collectors.toList());
        return this;
    }
    public ComunidadFusionableBuilder conPropuestasAnteriores(List<PropuestaFusion> propuestas) {
        this.comunidadFusionable.propuestasAnteriores = propuestas.stream().map(PropuestaFusion::propuestaAnterior).collect(Collectors.toList());
        return this;
    }

    public ComunidadFusionableBuilder conGradoDeConfianza(Double gradoDeConfianza) {
        this.comunidadFusionable.gradoConfianza = gradoDeConfianza;
        return this;
    }

    public ComunidadFusionable construir(){
        if(this.comunidadFusionable.id == null){
            throw new SinIdValidoExcepcion();
        }

        return this.comunidadFusionable;
    }

}
