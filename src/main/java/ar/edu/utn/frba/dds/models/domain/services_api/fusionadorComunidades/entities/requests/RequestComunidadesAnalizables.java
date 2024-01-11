package ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities.requests;

import ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities.ComunidadFusionable;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RequestComunidadesAnalizables {

    @SerializedName("comunidades")
    public List<ComunidadFusionable> comunidades;

}
