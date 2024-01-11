package ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje;

import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.ComunidadPuntaje;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CalculadorLlamadas {

    @POST("calcularPuntaje")
    Call<ComunidadPuntaje> comunidadPuntaje(@Body RequestComunidadPuntaje requestComunidadPuntaje);
}
