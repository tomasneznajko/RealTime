package ar.edu.utn.frba.dds.models.excepciones.puntajes;

public class SinPuntajeExcepcion extends RuntimeException{
    public SinPuntajeExcepcion(){
        super("No tiene un puntaje asignado");
    }
}

