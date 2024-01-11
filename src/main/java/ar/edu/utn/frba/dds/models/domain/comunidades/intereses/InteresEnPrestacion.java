package ar.edu.utn.frba.dds.models.domain.comunidades.intereses;

import ar.edu.utn.frba.dds.models.domain.servicios.PrestacionDeServicio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InteresEnPrestacion 
{
    private PrestacionDeServicio prestacion;
    private TipoDeInteres tipoDeInteres;

    public InteresEnPrestacion (PrestacionDeServicio prestacionParaAgregar) 
    {
        this.prestacion = prestacionParaAgregar;
        this.tipoDeInteres = TipoDeInteres.SIN_MARCAR;
    }

    public void actualizarTipoDeInteres (TipoDeInteres interesNuevo) {
        this.tipoDeInteres = interesNuevo;
    }
}
