package ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades;

import ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities.requests.RequestComunidadesAnalizables;
import ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities.requests.RequestComunidadesFusionables;
import ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities.responses.ResponseComunidadFusionada;
import ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities.responses.ResponseComunidadesAnalizables;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioFusionador{
    private static ServicioFusionador instancia = null;
    private static final String urlAPI = "https://api-fusionador-comunidades.onrender.com/api/";
    /* Mejor usar archivo de config para esta URL absoluta*/
    private Retrofit retrofit;

    private ServicioFusionador(){
        this.retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static ServicioFusionador getInstance(){
        if(instancia==null){
            instancia = new ServicioFusionador();
        }
        return instancia;
    }

    public ResponseComunidadesAnalizables responseComunidadesAnalizables(RequestComunidadesAnalizables requestComunidadesAnalizables) throws IOException {
        FusionadorLlamadas fusionadorLlamadas = this.retrofit.create(FusionadorLlamadas.class);
        Call<ResponseComunidadesAnalizables> llamadaComunidadesAnalizables = fusionadorLlamadas.comunidadesAnalizables(requestComunidadesAnalizables);
        Response<ResponseComunidadesAnalizables> comunidadesAnalizablesResponse = llamadaComunidadesAnalizables.execute();
        return comunidadesAnalizablesResponse.body();
    }

    public ResponseComunidadFusionada responseComunidadesFusionadas(RequestComunidadesFusionables requestComunidadesFusionables) throws IOException {
        FusionadorLlamadas fusionadorLlamadas = this.retrofit.create(FusionadorLlamadas.class);
        Call<ResponseComunidadFusionada> llamadaComunidadesFusionables = fusionadorLlamadas.comunidadesFusionadas(requestComunidadesFusionables);
        Response<ResponseComunidadFusionada> comunidadesAnalizablesResponse = llamadaComunidadesFusionables.execute();
        return comunidadesAnalizablesResponse.body();
    }


}
