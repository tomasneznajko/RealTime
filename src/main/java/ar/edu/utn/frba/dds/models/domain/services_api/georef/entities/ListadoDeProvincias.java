package ar.edu.utn.frba.dds.models.domain.services_api.georef.entities;

import java.util.List;

public class ListadoDeProvincias {
    public int cantidad;
    public int inicio;
    public int total;
    public Parametro parametros;
    public List<Provincia> provincias;

    private class Parametro{
        List<String> campos;
    }

}
