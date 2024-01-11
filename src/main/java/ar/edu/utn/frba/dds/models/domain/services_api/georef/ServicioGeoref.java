package ar.edu.utn.frba.dds.models.domain.services_api.georef;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.Departamento;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.ListadoDeDepartamentosDeProvincia;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.ListadoDeMunicipiosDeProvincia;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.ListadoDeProvincias;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.Municipio;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.Provincia;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioGeoref {
    private static ServicioGeoref instancia = null;
    private static final String urlAPI = "https://apis.datos.gob.ar/georef/api/";
    /* Mejor usar archivo de config para esta URL absoluta*/
    private Retrofit retrofit;

    private ServicioGeoref(){
        this.retrofit = new Retrofit.Builder().baseUrl(urlAPI).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static ServicioGeoref getInstance(){
        if(instancia==null){
            instancia = new ServicioGeoref();
        }
        return instancia;
    }

    public ListadoDeProvincias listadoDeProvincias() throws IOException {
        GeorefLlamadas georefLlamadas = this.retrofit.create(GeorefLlamadas.class);
        Call<ListadoDeProvincias> requestProvincias = georefLlamadas.provincias("id,nombre");

        Response<ListadoDeProvincias> responseProvincias = requestProvincias.execute(); //Ejecuta la solicitud

        return responseProvincias.body(); //Me devuelve lo que responde
    }
    public ListadoDeMunicipiosDeProvincia listadoDeMunicipios(int id, int max) throws IOException {
        GeorefLlamadas georefLlamadas = this.retrofit.create(GeorefLlamadas.class);
        Call<ListadoDeMunicipiosDeProvincia> requestMunicipios = georefLlamadas.municipios("id,nombre", id, max);

        Response<ListadoDeMunicipiosDeProvincia> responseMunicipios = requestMunicipios.execute(); //Ejecuta la solicitud

        return responseMunicipios.body(); //Me devuelve lo que responde
    }

    public ListadoDeDepartamentosDeProvincia listadoDeDepartamentos(int id, int max) throws IOException {
        GeorefLlamadas georefLlamadas = this.retrofit.create(GeorefLlamadas.class);
        Call<ListadoDeDepartamentosDeProvincia> requestDepartamentos = georefLlamadas.departamentos("id,nombre", id, max);

        Response<ListadoDeDepartamentosDeProvincia> departamentosResponse = requestDepartamentos.execute(); //Ejecuta la solicitud

        return departamentosResponse.body(); //Me devuelve lo que responde
    }

    public Provincia buscarProvincia(String unaProvincia) throws IOException {
        for(Provincia provincia : this.listadoDeProvincias().provincias){
            if(provincia.nombre.equals(unaProvincia)){
                return provincia;
            }
        }
        return null;
    }

    public Municipio buscarMunicipio(String unMunicipio, int provincia, int max) throws IOException {
        for(Municipio municipio : this.listadoDeMunicipios(provincia,max).municipios){
            if(municipio.nombre.equals(unMunicipio)){
                return municipio;
            }
        }
        return null;
    }
    public Departamento buscarDepartamento(String unDepartamento, int provincia, int max) throws IOException {
        for(Departamento departamento : this.listadoDeDepartamentos(provincia,max).departamentos){
            if(departamento.nombre.equals(unDepartamento)){
                return departamento;
            }
        }
        return null;
    }

}
