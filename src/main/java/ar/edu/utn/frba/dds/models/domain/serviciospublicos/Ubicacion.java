package ar.edu.utn.frba.dds.models.domain.serviciospublicos;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Setter
@Getter
@Embeddable
public class Ubicacion {
  @Column(name = "latitud")
  private double latitud;

  @Column(name = "longitud")
  private double longitud;
}
