package ar.edu.utn.frba.dds.models.domain.comunidades.intereses;

import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;
import ar.edu.utn.frba.dds.models.domain.servicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.*;
@Entity
@Table(name = "Intereses")
@Getter
@Setter
public class Interes {


    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "entidad_id", referencedColumnName = "id")
    private Entidad entidad;

    @Transient
    private Set<InteresEnPrestacion> prestacionesDeInteres;
    public Interes(){

        this.prestacionesDeInteres = new HashSet<>();
    }

    public Boolean tieneLocalizacionEspecifica(Localizacion localizacion){
        return localizacion.getMunicipio() != null || localizacion.getDepartamento() != null;
    }
    public Boolean tieneLocalizacionValida(Localizacion localizacion, Establecimiento establecimiento) throws IOException {
        if(tieneLocalizacionEspecifica(localizacion)){
            return localizacion.getMunicipio() == establecimiento.getLocalizacion().getMunicipio()
                    ||
                    localizacion.getDepartamento() == establecimiento.getLocalizacion().getDepartamento();
        }
        return localizacion.getProvincia().equals(this.entidad.getLocalizacion().getProvincia());
    }

    public void actualizarInteres(Localizacion localizacion) throws IOException {
        this.prestacionesDeInteres.clear();
        for(Establecimiento establecimiento : this.entidad.getEstablecimientos()){
            if(tieneLocalizacionValida(localizacion, establecimiento)){
                for(PrestacionDeServicio prestacionDeServicio : establecimiento.getPrestacionesDeServicios()) {
                    if (!prestacionDeServicio.getFunciona()) {
                        this.prestacionesDeInteres.add(new InteresEnPrestacion (prestacionDeServicio));
                    }
                }
            }
        }
    }

    public void actualizarInteresados(Miembro miembro) {
        for(InteresEnPrestacion interesEnPrestacion : this.prestacionesDeInteres ){
            interesEnPrestacion.getPrestacion().getInteresados().add(miembro);
        }
    }
}
