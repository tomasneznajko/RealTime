package ar.edu.utn.frba.dds.models.domain.servicios;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Entity
@Setter @Getter
public class Banio extends Servicio {
  @Enumerated(EnumType.STRING)
  @Column(name = "genero")
  private Genero genero;
  @Column(name = "esParaDiscapacitado")
  private Boolean discapacitado;

}
