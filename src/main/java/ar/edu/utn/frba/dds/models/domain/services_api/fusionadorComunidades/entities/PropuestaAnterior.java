package ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class PropuestaAnterior {

    @SerializedName("idComunidad")
    public Long idComunidad;

    @SerializedName("fecha")
    public String fecha;
}
