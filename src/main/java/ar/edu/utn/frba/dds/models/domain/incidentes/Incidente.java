package ar.edu.utn.frba.dds.models.domain.incidentes;

import ar.edu.utn.frba.dds.models.builders.puntajes.IncidentePuntajeBuilder;
import ar.edu.utn.frba.dds.models.converts.LocalDateTimeAttributeConverter;
import ar.edu.utn.frba.dds.models.converts.UUIDAttributeConverter;
import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.IncidentePuntaje;
import ar.edu.utn.frba.dds.models.domain.servicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "incidentes")

@Setter @Getter
public class Incidente {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;


    @Column(name = "id_amigable")
    private Long idAmigable;


    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "fechayHoraApertura",columnDefinition = "TIMESTAMP")
    private LocalDateTime fachaYHoraApertura;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "fechayHoraCierre",columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaYHoraCierre;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_servicio", referencedColumnName = "id")
    private PrestacionDeServicio prestacionDeServicio;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_establecimiento", referencedColumnName = "id")
    private Establecimiento establecimiento;

    @Column(name = "observaciones",columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "estaAbierto")
    private Boolean abierto;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_abridor")
    private Miembro abridor;

    @OneToOne
    @JoinColumn(name = "id_cerrador")
    private Miembro cerrador;

    @ManyToOne
    @JoinColumn(name = "comunidad_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Comunidad comunidad;

    @PrePersist
    public void antesDePersistir(){
        this.idAmigable = UUIDAttributeConverter.convertUuidToLong(UUID.randomUUID());
    }
    public void meAbro(Miembro abridor,AperturaIncidente aperturaIncidente){
        setAbridor(abridor);
        setObservaciones(aperturaIncidente.getObservaciones());
        setEstablecimiento(aperturaIncidente.getEstablecimiento());
        setPrestacionDeServicio(aperturaIncidente.getPrestacionDeServicio());
        setFachaYHoraApertura(aperturaIncidente.getFechaYHoraApertura());
        setAbierto(true);
    }
    public void meCierro(Miembro cerrador){
        this.setFechaYHoraCierre(LocalDateTime.now());
        this.setCerrador(cerrador);
        this.setAbierto(false);
    }

     @Override
    public String toString() {
        return "Incidente{" +
            "fachaYHoraApertura=" + fachaYHoraApertura +
            ", fechaYHoraCierre=" + fechaYHoraCierre +
            ", prestacionDeServicio=" + prestacionDeServicio +
            ", establecimiento=" + establecimiento +
            ", observaciones='" + observaciones + '\'' +
            ", abierto=" + abierto +
            ", abridor=" + abridor +
           ", cerrador=" + cerrador +
            '}';
    }

    public IncidentePuntaje incidentePuntaje(){
        return new IncidentePuntajeBuilder().conId(this.getIdAmigable()).conAbridor(this.getAbridor().getId()).
                conCerrador(this.getCerrador().getId()).conServicio(this.getPrestacionDeServicio().getId()).
                conFechaApertura(this.getFachaYHoraApertura()).conFechaCierre(this.fechaYHoraCierre).construir();
    }


}
