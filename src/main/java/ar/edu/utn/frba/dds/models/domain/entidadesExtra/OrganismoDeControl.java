package ar.edu.utn.frba.dds.models.domain.entidadesExtra;

import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="organismosDeControl")

public class OrganismoDeControl {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "nombre")
    @Setter  private String nombre;
    @Column(name = "descripcion",columnDefinition = "TEXT")
    @Setter String descripcion;
    @Transient
    @Setter
    Localizacion localizacion;
    @OneToMany
    @JoinColumn(name = "id_organismoDeControl",referencedColumnName = "id")
    @Getter List<Entidad> entidades;
    public OrganismoDeControl(){
        this.entidades = new ArrayList<>();
    }
}
