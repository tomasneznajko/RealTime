package ar.edu.utn.frba.dds.models.domain.comunidades;

import ar.edu.utn.frba.dds.models.builders.fusionesDeComunidades.ComunidadFusionableBuilder;
import ar.edu.utn.frba.dds.models.builders.puntajes.ComunidadPuntajeBuilder;
import ar.edu.utn.frba.dds.models.converts.UUIDAttributeConverter;
import ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion.Notificacion;
import ar.edu.utn.frba.dds.models.domain.comunidades.gradosDeConfianza.Puntaje;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.models.domain.incidentes.TipoFiltrado;

import javax.persistence.*;

import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.ComunidadPuntaje;
import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.MiembroPuntaje;
import ar.edu.utn.frba.dds.models.domain.services_api.fusionadorComunidades.entities.ComunidadFusionable;
import ar.edu.utn.frba.dds.models.domain.servicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Comunidades")
@Getter @Setter
public class Comunidad{

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", columnDefinition = "uuid")
  private UUID id;

  @Column(name = "id_amigable")
  private Long idAmigable;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "administradores_por_comunidad",
          joinColumns = @JoinColumn(name = "miembro_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
  )
  private List<Miembro> administradores;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "miembros_por_comunidad",
      joinColumns = @JoinColumn(name = "comunidad_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "miembro_id", referencedColumnName = "id")
  )
  private List<Miembro> miembros;

  @OneToMany
  @JoinColumn(name = "comunidad_fusionable_id", referencedColumnName = "id")
  private List<PropuestaFusion> propuestasFusion;

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
  private List<Incidente> incidentes;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "descripcion", columnDefinition = "TEXT")
  private String descripcion;

  @Embedded
  private Puntaje puntaje;

  public Comunidad() {
    this.administradores = new ArrayList<>();
    this.miembros = new ArrayList<>();
    this.incidentes = new ArrayList<>();
    this.propuestasFusion = new ArrayList<>();
  }

  @PrePersist
  public void antesDePersistir(){
    this.idAmigable = UUIDAttributeConverter.convertUuidToLong(UUID.randomUUID());
  }

  //esto es para fijarse el estado de los incidentes abiertos
  public void imprimirIncidentes(TipoFiltrado tipoFiltrado) {
    for (Incidente incidente : incidentes) {
      if (tipoFiltrado == TipoFiltrado.SOLO_ABIERTOS && incidente.getAbierto()) {
        System.out.println(incidente);
      } else if (tipoFiltrado == TipoFiltrado.SOLO_CERRADOS && !incidente.getAbierto()) {
        System.out.println(incidente);
      } else if (tipoFiltrado == TipoFiltrado.TODOS) {
        System.out.println(incidente);
      }
    }
  }

  
/*

  public void ingresarServicioNuevo(Establecimiento establecimiento, Miembro administrador, String nombre, String descripcion, int cantidad) throws NoEsAdministradorExcepcion {
    this.verificarQueEsAdministrador(administrador);
    ServicioComunitario servicio = new ServicioComunitario();
    servicio.setNombre(nombre);
    servicio.setDescripcion(descripcion);
    PrestacionDeServicio prestacionDeServicio = new PrestacionDeServicio(servicio,cantidad);
    prestacionDeServicio.setFunciona(false);
    establecimiento.agregarPrestaciones(prestacionDeServicio);
  }

  public void verificarQueEsAdministrador(Miembro administrador) throws NoEsAdministradorExcepcion{
    if (!this.administradores.contains(administrador)) {
      throw new NoEsAdministradorExcepcion();
    }
  }
*/
  public void agregarUsuarios(Miembro... miembros) {
    this.miembros.addAll(Arrays.asList(miembros));
    this.miembros.forEach(miembro -> miembro.getComunidades().add(this));
  }

  public void removerUsuarios(Miembro... miembros) {
    for (Miembro value : miembros) {
      this.miembros.remove(value);
    }
  }

  public int cantidadDeMiembros(){
    return this.miembros.size();
  }

  public int cantidadDeIncidentes(){
    return this.incidentes.size();
  }





  public void notificarMiembros(Incidente incidente){
    getIncidentes().add(incidente);
    Notificacion notificacion = new Notificacion();
    notificacion.crearNotificacion(incidente);
    this.miembros.stream().filter(miembro -> miembro.getUsuario() != incidente.getAbridor().getUsuario())
                          .forEach(m->m.getMedioDeNotificacion().evaluarEnvioDeNotificacion(notificacion));
/*    System.out.println("Se ha enviado notificacion al WhatssApp - ");*/
  }

  public void cerrarIncidente(Miembro autor,Incidente incidente){
    incidente.meCierro(autor);
    this.notificarMiembros(incidente);
  }
  public ComunidadPuntaje comunidadPuntaje(){
    return new ComunidadPuntajeBuilder().conId(this.getIdAmigable()).conPuntaje(this.puntaje.getValor()).conMiembros(this.getMiembros()).construir();
  }

  public ComunidadFusionable comunidadFusionable(){
    return new ComunidadFusionableBuilder().conId(this.getIdAmigable()).conIncidentes(this.incidentes).conEstablecimientos(establecimientosDeIncidentes())
            .conServicios(serviciosDeIncidentes()).conUsuarios(this.miembros).conPropuestasAnteriores(this.propuestasFusion)
            .conGradoDeConfianza(this.puntaje.getValor()).construir();
  }
  private List<Establecimiento> establecimientosDeIncidentes(){
    return this.incidentes.stream().map(Incidente::getEstablecimiento).collect(Collectors.toList());
  }

  private List<PrestacionDeServicio> serviciosDeIncidentes() {
    return this.incidentes.stream().map(Incidente::getPrestacionDeServicio).collect(Collectors.toList());
  }

  public void actualizarPuntajes(ComunidadPuntaje comunidadPuntaje){
    this.puntaje.setValor(comunidadPuntaje.puntaje);
    actualizarPuntajesMiembros(comunidadPuntaje);
  }

  private void actualizarPuntajesMiembros(ComunidadPuntaje comunidadPuntaje) {
    for(MiembroPuntaje miembroPuntaje : comunidadPuntaje.miembros){
      for(Miembro miembro : this.miembros){
        if(miembro.getId() == miembroPuntaje.id){
          miembro.getPuntaje().setValor(miembroPuntaje.puntaje);
        }
      }
    }
  }


}
