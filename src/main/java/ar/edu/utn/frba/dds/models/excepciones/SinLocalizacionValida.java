package ar.edu.utn.frba.dds.models.excepciones;

public class SinLocalizacionValida extends RuntimeException{
    public SinLocalizacionValida() {
        super("No se ha configurado una localizacion valida en el objeto a construir");
    }
}
