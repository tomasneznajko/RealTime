package ar.edu.utn.frba.dds.models.domain.servicios;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("escalador")
@Setter @Getter
public class Escalador extends Servicio {
  @Enumerated(EnumType.STRING)
  @Column(name = "origen")
  private TipoTraslado origen;
  @Enumerated(EnumType.STRING)
  @Column(name = "destino")
  private TipoTraslado destino;
  @Enumerated(EnumType.STRING)
  @Column(name = "tipoDeEscalador")
  private TipoEscalador tipoEscalador;
}
