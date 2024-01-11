package ar.edu.utn.frba.dds.models.builders.puntajes;

import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.IncidentePuntaje;
import ar.edu.utn.frba.dds.models.excepciones.puntajes.SinFechaValida;
import ar.edu.utn.frba.dds.models.excepciones.puntajes.SinIdValidoExcepcion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.UUID;

public class IncidentePuntajeBuilder {
    private IncidentePuntaje incidentePuntaje = new IncidentePuntaje();

    public IncidentePuntajeBuilder conId(Long id){
        this.incidentePuntaje.incidenteId = id;
        return this;
    }

    public IncidentePuntajeBuilder conAbridor(Long id_abridor){
        this.incidentePuntaje.abiertoPorId = id_abridor;
        return this;
    }
    public IncidentePuntajeBuilder conCerrador(Long id_cerrador){
        this.incidentePuntaje.cerradoPorId = id_cerrador;
        return this;
    }
    public IncidentePuntajeBuilder conServicio(Long id_servicio){
        this.incidentePuntaje.codigoServicio = id_servicio;
        return this;
    }
    public IncidentePuntajeBuilder conFechaApertura(LocalDateTime fechaApertura){
        this.incidentePuntaje.fechaApertura = fechaApertura.toString();
        return this;
    }
    public IncidentePuntajeBuilder conFechaCierre(LocalDateTime fechaCierre){
        this.incidentePuntaje.fechaCierre = fechaCierre.toString();
        return this;
    }

    public IncidentePuntaje construir(){
        if(!idValidos(this.incidentePuntaje.incidenteId,this.incidentePuntaje.codigoServicio,this.incidentePuntaje.abiertoPorId,this.incidentePuntaje.cerradoPorId)){ //TODO idValidos this.incidentePuntaje.incidenteId,this.incidentePuntaje.abiertoPorId,this.incidentePuntaje.codigoServicio
            throw new SinIdValidoExcepcion();
        }

        if(fechasValidas(this.incidentePuntaje.fechaApertura,this.incidentePuntaje.fechaCierre)){
            throw new SinFechaValida();
        }
        return this.incidentePuntaje;
    }

    private boolean fechasValidas(String... fechas) {

        return Arrays.stream(fechas).allMatch(fecha->isValidDateTime(fecha, "yyyy-MM-dd'T'HH:mm:ss.SSS"));
    }

    private boolean isValidDateTime(String fecha, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime fechaYHora = LocalDateTime.parse(fecha, formatter);
            // Si se ha realizado la conversión con éxito, la fecha es válida
            return true;
        } catch (DateTimeParseException e) {
            // Si se produce una excepción, la fecha no es válida
            return false;
        }
    }

    private boolean idValidos(Long... ids) {
        return Arrays.stream(ids).allMatch(id->id!=0);
    }
}
