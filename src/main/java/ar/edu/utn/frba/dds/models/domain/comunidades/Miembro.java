package ar.edu.utn.frba.dds.models.domain.comunidades;
import ar.edu.utn.frba.dds.models.builders.puntajes.MiembroPuntajeBuilder;
import ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion.Notificador;
import ar.edu.utn.frba.dds.models.domain.comunidades.gradosDeConfianza.Puntaje;
import ar.edu.utn.frba.dds.models.domain.comunidades.intereses.Interes;
import ar.edu.utn.frba.dds.models.domain.incidentes.AperturaIncidente;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;

import ar.edu.utn.frba.dds.models.domain.services_api.calculadorPuntaje.entities.MiembroPuntaje;
import ar.edu.utn.frba.dds.models.domain.servicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Ubicacion;
import ar.edu.utn.frba.dds.models.domain.usuario.Rol;
import ar.edu.utn.frba.dds.models.excepciones.NoEsUnaPrestacionValidaExcepcion;
import ar.edu.utn.frba.dds.models.excepciones.PrestacionFuncionaExcepcion;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.*;
@Entity
@Table(name = "Miembros")
@Setter @Getter
public class Miembro {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "apellido")
  private String apellido;

  @Column(name = "descripcion",columnDefinition = "TEXT")
  private String descripcion;

  @ManyToOne(cascade = CascadeType.ALL)
  private Rol rol;

  @Column(name = "mail")
  private String mail;

  @Column(name = "contrasenia")
  private String contrasenia;

  @Column(name = "usuario")
  private String usuario;


  @Embedded
  private Puntaje puntaje;


  @ElementCollection
  @CollectionTable(name = "usuario_fotos1", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id") )
  @Column(name = "path")
  private List<String> fotos;


  @ManyToMany(cascade = CascadeType.ALL)
  private List<Interes> intereses;


  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "localizacion_id", referencedColumnName = "id")
  private Localizacion localizacion;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "medioDeNotificacion_id", referencedColumnName = "id")
  public MedioDeNotificacion medioDeNotificacion;

  @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "miembros")
  /*@JoinTable(name = "miembros_por_comunidad",
          joinColumns = @JoinColumn(name = "comunidad_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "miembro_id", referencedColumnName = "id"))
   */
  private Set<Comunidad> comunidades;


  @Embedded
  Ubicacion ubicacion;

  public int cantComunidades() { return  this.comunidades.size(); }

  public int cantFotos() { return this.fotos.size();}

  public Miembro(){
    this.comunidades = new HashSet<>();
    this.intereses = new ArrayList<>();
    this.fotos = new ArrayList<>();
  }

  public int cantIntereses() { return this.intereses.size(); }

  public void agregarIntereses(Interes ... interes){
    Collections.addAll(this.intereses,interes);
  }

  public void eliminarIntereses(Interes ... interes){
    for (Interes value : interes) {
      this.intereses.remove(value);
    }
  }

  public void actualizarIntereses() throws IOException {
    for(Interes interes : this.intereses){
      interes.actualizarInteres(this.localizacion);
      interes.actualizarInteresados(this);
    }
  }

  public Boolean actualizarPrestacionDeServicio(Establecimiento establecimiento, PrestacionDeServicio prestacionDeServicio){
    if(!establecimiento.getPrestacionesDeServicios().contains(prestacionDeServicio)){
      throw new NoEsUnaPrestacionValidaExcepcion();
    }
    return prestacionDeServicio.getFunciona();
  }


  public List<Incidente> abrirIncidente(AperturaIncidente aperturaIncidente, Notificador notificador){
    if(actualizarPrestacionDeServicio(aperturaIncidente.getEstablecimiento(),aperturaIncidente.getPrestacionDeServicio())){
      throw new PrestacionFuncionaExcepcion();
    }
    return notificador.notificar(this,aperturaIncidente);

  }


  public MiembroPuntaje miembroPuntaje(){
    return new MiembroPuntajeBuilder().conId(this.getId()).conPuntaje(this.puntaje.getValor()).construir();
  }



  /**
   * @param usuario: Nombre de Usuario
   * @param clave: Contraseña
   * @return Nuevo Usuario
   */
}
