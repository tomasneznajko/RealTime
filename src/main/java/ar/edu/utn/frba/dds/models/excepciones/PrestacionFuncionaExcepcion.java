package ar.edu.utn.frba.dds.models.excepciones;

public class PrestacionFuncionaExcepcion extends RuntimeException{
    public PrestacionFuncionaExcepcion(){
        super("La prestacion notificada funciona correctamente");
    }
}
