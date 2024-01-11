package ar.edu.utn.frba.dds.models.domain.comunidades;

import ar.edu.utn.frba.dds.models.builders.fusionesDeComunidades.PropuestaAnteriorBuilder;
import ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities.PropuestaAnterior;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "propuestas_fusiones")

@Setter
public class PropuestaFusion {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_comunidad_propuesta", referencedColumnName = "id")
    private Comunidad comunidad;

    @Column(name = "fecha_solicitada")
    private LocalDate fechaSolicitada;

    public PropuestaAnterior propuestaAnterior(){
        return new PropuestaAnteriorBuilder().conId(this.comunidad.getIdAmigable()).conFecha(this.fechaSolicitada).construir();
    }

}
