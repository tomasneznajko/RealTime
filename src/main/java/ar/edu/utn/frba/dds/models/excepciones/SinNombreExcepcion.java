package ar.edu.utn.frba.dds.models.excepciones;

public class SinNombreExcepcion extends RuntimeException{
    public SinNombreExcepcion(){
        super("El objeto a contruir no tiene nombre configurado");
    }

}
