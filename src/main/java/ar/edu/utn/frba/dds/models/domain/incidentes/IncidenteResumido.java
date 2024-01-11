package ar.edu.utn.frba.dds.models.domain.incidentes;

import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IncidenteResumido {
    private Establecimiento establecimiento;

    private String observaciones;

    private List<Comunidad> comunidades;


}
