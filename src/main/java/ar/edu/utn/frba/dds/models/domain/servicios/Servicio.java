package ar.edu.utn.frba.dds.models.domain.servicios;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "tipoDeServicio")
@DiscriminatorColumn(name = "tipo")

public abstract class Servicio {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Descripcion", columnDefinition = "TEXT")
    private String descripcion;

}
