package ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion;

import javax.persistence.criteria.CriteriaBuilder;
import lombok.Getter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import javax.persistence.*;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "Medio_de_notificacion")
@DiscriminatorColumn(name = "tipo")
public abstract class MedioDeNotificacion {
  @Id
  @GeneratedValue
  @Getter
  @Setter
  private Long id;

  private int codHarc = 1;



  private String name;

  @OneToMany
  List<Notificacion> notificacionesRecientes;

  @OneToMany
  @JoinColumn(name = "medioDeNotificacion_id", referencedColumnName = "id")
  List<RangoHorario> rangosHorariosElegidos;

  public MedioDeNotificacion(){
    this.notificacionesRecientes = new ArrayList<>();
    this.rangosHorariosElegidos = new ArrayList<>();
  }

  public List<Notificacion> getNotificacionesRecientes() {
    return notificacionesRecientes;
  }

  public void setRangosHorariosElegidos(List<RangoHorario> rangosHorariosElegidos) {
    this.rangosHorariosElegidos = rangosHorariosElegidos;
  }

  public void evaluarEnvioDeNotificacion(Notificacion notificacion) {
      System.out.println("Holaaaa");
      if (rangosHorariosElegidos.isEmpty()) {//CUANDO SUCEDEN
        this.notificacionesRecientes.add(notificacion);
        this.enviarNotificacion();
      } else if (notificacionesRecientes.isEmpty()) {
        this.notificacionesRecientes.add(notificacion);
        LocalTime horaActual = LocalTime.now();
        Optional<LocalTime> horaInicioProxima;
        long diferenciaEnSegundos;
        if (rangosHorariosElegidos.stream().anyMatch(rangoHorario -> rangoHorario.contiene(horaActual)))/*chequeo que se encuentre dentro del horario*/ {
            System.out.println("Se ha enviadsfrgvergber ");
          this.enviarNotificacion();

        } else {
                  List<RangoHorario> rangosHorariosProximos = rangosHorariosElegidos.stream()
                                                              .filter(rangoHorario -> rangoHorario.getHoraInicio().isAfter(horaActual)).toList();

                  if (rangosHorariosProximos.isEmpty()) { //Quiere decir que el horario actual esta despues que los rangos horarios
                    horaInicioProxima = rangosHorariosElegidos.stream()
                                                              .map(RangoHorario::getHoraInicio)
                                                              .min(Comparator.naturalOrder());
                    diferenciaEnSegundos =  24 * 3600 - horaInicioProxima.orElse(LocalTime.of(0, 0)).until(horaActual, ChronoUnit.SECONDS);

                  }else{//Quiere decir que el horario actual esta antes que algun rango horario
                    horaInicioProxima = rangosHorariosProximos.stream()
                                                              .map(RangoHorario::getHoraInicio)
                                                              .min(Comparator.naturalOrder());
                    diferenciaEnSegundos = horaActual.until(horaInicioProxima.orElse(LocalTime.of(0, 0)), ChronoUnit.SECONDS);
                  }

                  TimerTask tareaCalendarizada = new TimerTask() {
                    public void run() {
                      enviarNotificacion();
                    }
                  };
                  Timer timer = new Timer();
                  timer.schedule(tareaCalendarizada, diferenciaEnSegundos * 1000); // 1000 ms = 1 segundo
        }


      } else { //Si ya hay notificaciones recientes simplemente la agrego a la lista porque la cron task ya fue creada
        this.notificacionesRecientes.add(notificacion);
      }

  }
  public String notificacionesToString(){

    StringBuilder contenido = new StringBuilder();
    contenido.append("Resumen de incidentes:\n");

    for (Notificacion notificacion : this.notificacionesRecientes) {
        contenido.append("Incidente: ").append(notificacion.getAbierto()).append("\n");
      contenido.append("Fecha y hora de apertura: ").append(notificacion.getFachaYHoraApertura()).append("\n");
      contenido.append("Prestación de servicio: ").append(notificacion.getPrestacionDeServicio()).append("\n");
      contenido.append("Establecimiento: ").append(notificacion.getPrestacionDeServicio()).append("\n");
      contenido.append("Observaciones: ").append(notificacion.getObservaciones()).append("\n");
      contenido.append("--------------------\n");
    }
    return contenido.toString();

  }
  protected abstract void enviarNotificacion();


}
