package ar.edu.utn.frba.dds.models.excepciones;

public class SinContraseniaExcepcion extends RuntimeException{
    public SinContraseniaExcepcion(){
        super("No se configuró una contraseña al miembro");
    }
}
