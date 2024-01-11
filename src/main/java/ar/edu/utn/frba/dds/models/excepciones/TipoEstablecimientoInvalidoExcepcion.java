package ar.edu.utn.frba.dds.models.excepciones;

public class TipoEstablecimientoInvalidoExcepcion extends Exception{
    public TipoEstablecimientoInvalidoExcepcion() {
        super("Al menos un establecimiento tiene un tipo diferente al determinado en la entidad :c");
    }
}
