package ar.edu.utn.frba.dds.models.excepciones;

public class LocalizacionEstablecimientoInvalidaExcepcion extends RuntimeException {
    public LocalizacionEstablecimientoInvalidaExcepcion(){
        super("Al menos un establecimiento de los que se quiere agregar tiene una localizacion con provincia diferente al de la entidad");
    }
}
