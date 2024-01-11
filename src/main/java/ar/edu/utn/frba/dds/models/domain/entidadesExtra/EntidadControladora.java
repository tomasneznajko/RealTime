package ar.edu.utn.frba.dds.models.domain.entidadesExtra;

import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name ="EntidadControladora")
public class EntidadControladora {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "nombre")
    @Setter private String nombre;

    @Column(name = "descripcion",columnDefinition = "TEXT")
    @Setter String descripcion;

    @Column(name = "direccion")
    @Setter String direccion;

    @Transient
    @Setter
    Localizacion localizacion;

    @OneToMany
    @JoinColumn(name = "id_entidadControladora", referencedColumnName = "id")
    @Getter List<Entidad> entidades;
    public EntidadControladora(){

        this.entidades = new ArrayList<>();
    }
    public void agregarEntidades(Entidad ... entidades ){
        this.entidades.addAll(Arrays.asList(entidades));
    }

    public void eliminarEntidades(Entidad ... entidades){
        for (Entidad entidad : entidades) {
            this.entidades.remove(entidad);
        }
    }
}
