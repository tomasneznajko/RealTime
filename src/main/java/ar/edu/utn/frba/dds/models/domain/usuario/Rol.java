package ar.edu.utn.frba.dds.models.domain.usuario;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rol")
@Setter
@Getter
public class Rol {
    @Id
    @GeneratedValue
    private long id;

    @Column (name = "nombre")
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoRol tipo;


}
