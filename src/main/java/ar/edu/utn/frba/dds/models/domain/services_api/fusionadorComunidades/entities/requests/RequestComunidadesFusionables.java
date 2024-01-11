package ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities.requests;

import ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities.ComunidadFusionable;
import com.google.gson.annotations.SerializedName;
import lombok.Setter;

@Setter
public class RequestComunidadesFusionables {

    @SerializedName("comunidad1")
    ComunidadFusionable comunidad1;

    @SerializedName("comunidad2")
    ComunidadFusionable comunidad2;
}
