package ar.edu.utn.frba.dds.models.domain.geoLocalizadores;

import ar.edu.utn.frba.dds.models.domain.services_api.georef.ServicioGeoref;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Ubicacion;

public class GeoLocalizador {

    private static GeoLocalizador instancia = null;

    public static GeoLocalizador getInstance(){
        if(instancia==null){
            instancia = new GeoLocalizador();
        }
        return instancia;
    }

    public Boolean sonCoincidentes(Ubicacion ubicacion1, Ubicacion ubicacion2){



        return null;
    }
}
