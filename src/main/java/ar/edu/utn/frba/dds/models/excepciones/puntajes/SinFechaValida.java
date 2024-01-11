package ar.edu.utn.frba.dds.models.excepciones.puntajes;

public class SinFechaValida extends RuntimeException{
    public SinFechaValida(){
        super("No tiene una fecha válida asignada");
    }
}
