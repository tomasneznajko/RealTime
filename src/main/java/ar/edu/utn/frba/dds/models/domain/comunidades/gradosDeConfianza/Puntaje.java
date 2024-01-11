package ar.edu.utn.frba.dds.models.domain.comunidades.gradosDeConfianza;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter

@Embeddable
public class Puntaje {

    @Column(name = "puntaje")
    private Double valor;

    @Embedded
    private GradoDeConfianza gradoDeConfianza;

    public Puntaje(){
        this.gradoDeConfianza = new GradoDeConfianza();
    }



    public void setValor(double puntajeNuevo){
        this.valor = puntajeNuevo;
        calcularGrado(puntajeNuevo);
    }

    private void calcularGrado(double puntajeNuevo) {
        if(puntajeNuevo < 2){
            this.gradoDeConfianza.setNombre(TipoDeGrado.NO_CONFIABLE);
        } else if (puntajeNuevo>=2 && puntajeNuevo<=3){
            this.gradoDeConfianza.setNombre(TipoDeGrado.CON_RESERVAS);
        } else {
            this.gradoDeConfianza.setNombre(TipoDeGrado.CONFIABLE);
            this.gradoDeConfianza.calcularNivel(puntajeNuevo);
        }
    }




}
