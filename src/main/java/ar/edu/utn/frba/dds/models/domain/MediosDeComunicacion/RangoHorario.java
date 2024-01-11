package ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion;

import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Rangos_horarios")
@Getter
@Setter
public class RangoHorario {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "hora_inicio")
    public LocalTime horaInicio;
    @Column(name = "hora_fin")
    public LocalTime horaFin;


  public RangoHorario() {

    }


    public boolean contiene(LocalTime hora) {
      return !hora.isBefore(horaInicio) && !hora.isAfter(horaFin);
    }

}
