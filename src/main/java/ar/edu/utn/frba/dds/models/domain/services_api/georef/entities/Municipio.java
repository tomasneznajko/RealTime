package ar.edu.utn.frba.dds.models.domain.services_api.georef.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Municipio {
    @Id
    public int id;
    @Column(name = "nombre")
    public String nombre;
}
