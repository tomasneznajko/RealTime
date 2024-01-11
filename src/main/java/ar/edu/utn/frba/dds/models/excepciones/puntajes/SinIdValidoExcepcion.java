package ar.edu.utn.frba.dds.models.excepciones.puntajes;

public class SinIdValidoExcepcion extends RuntimeException{
    public SinIdValidoExcepcion(){
        super("No tiene un id válido asignado");
    }
}
