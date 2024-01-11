package ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities;

import com.google.gson.annotations.SerializedName;

public class MiembroPuntaje {
    @SerializedName("id")
    public int id;
    @SerializedName("puntaje")
    public double puntaje;
}
