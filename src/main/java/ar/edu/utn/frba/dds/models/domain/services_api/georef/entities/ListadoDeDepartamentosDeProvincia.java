package ar.edu.utn.frba.dds.models.domain.services_api.georef.entities;

import java.util.List;

public class ListadoDeDepartamentosDeProvincia {
    public int cantidad;
    public int inicio;
    public int total;
    public List<Departamento> departamentos;

    private class Parametro{
        public List<String> campos;
        public int max;
        public List<String> provincia;
    }


}
