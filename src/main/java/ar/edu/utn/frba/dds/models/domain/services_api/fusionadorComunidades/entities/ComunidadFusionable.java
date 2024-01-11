package ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities;

import com.google.gson.annotations.SerializedName;

import javax.persistence.Column;
import java.util.List;
import java.util.UUID;

public class ComunidadFusionable {
    @SerializedName("id")
    public Long id;
    @SerializedName("establecimientos")
    public List<Integer> establecimientos;

    @SerializedName("servicios")
    public List<Integer> servicios;

    @SerializedName("usuarios")
    public List<Integer> usuarios;

    @SerializedName("incidentes")
    public List<Long> incidentes;

    @SerializedName("propuestasAnteriores")
    public List<PropuestaAnterior> propuestasAnteriores;

    @Column(name = "Nivel_del_grado")
    public double gradoConfianza;
}
