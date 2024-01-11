package ar.edu.utn.frba.dds.models.domain.incidentes;

import ar.edu.utn.frba.dds.models.domain.servicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class AperturaIncidente {
    private String observaciones;
    private Establecimiento establecimiento;
    private PrestacionDeServicio prestacionDeServicio;
    private LocalDateTime fechaYHoraApertura;
    public AperturaIncidente(){
        this.fechaYHoraApertura = LocalDateTime.now();
    }
}
