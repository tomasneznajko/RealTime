package ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje;

import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.ComunidadPuntaje;
import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.IncidentePuntaje;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class RequestComunidadPuntaje {
    @SerializedName("incidentes")
    List<IncidentePuntaje> incidentesPuntaje;

    @SerializedName("comunidad")
    ComunidadPuntaje comunidadPuntaje;

    public RequestComunidadPuntaje(){
        this.incidentesPuntaje = new ArrayList<>();
    }
}
