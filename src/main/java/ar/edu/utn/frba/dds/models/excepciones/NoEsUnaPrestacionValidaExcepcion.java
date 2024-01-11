package ar.edu.utn.frba.dds.models.excepciones;

public class NoEsUnaPrestacionValidaExcepcion extends RuntimeException{
    public NoEsUnaPrestacionValidaExcepcion(){
        super("No es una prestacion del establecimiento dado por parametro");
    }
}
