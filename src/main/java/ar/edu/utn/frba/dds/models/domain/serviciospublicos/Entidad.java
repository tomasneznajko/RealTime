package ar.edu.utn.frba.dds.models.domain.serviciospublicos;

import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;
import ar.edu.utn.frba.dds.models.excepciones.LocalizacionEstablecimientoInvalidaExcepcion;
import ar.edu.utn.frba.dds.models.excepciones.TipoEstablecimientoInvalidoExcepcion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "entidad")
@Setter @Getter
public class Entidad {
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id_localizacion",referencedColumnName = "id")
  private Localizacion localizacion;

  @Column(name = "nombre")
  private String nombre;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipoEntidad")
  private TipoEntidad tipoEntidad;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipoEstablecimientos")
  private TipoEstablecimiento tipoEstablecimientos;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "entidad")
  private List<Establecimiento> establecimientos;

  public Entidad(){
    this.establecimientos = new ArrayList<>();
  }
  public void agregarEstablecimientos(Establecimiento... establecimientos) throws TipoEstablecimientoInvalidoExcepcion {

    if(!Arrays.stream(establecimientos).allMatch(e -> e.getLocalizacion().getProvincia().getNombre().equals(this.getLocalizacion().getProvincia().getNombre()))){
      throw new LocalizacionEstablecimientoInvalidaExcepcion();
    }
    if(!establecimientosConsistentes(List.of(establecimientos))){
      throw new TipoEstablecimientoInvalidoExcepcion();
    }

    for (Establecimiento value : establecimientos) {
      this.establecimientos.add(value);
    }
  }

  private boolean establecimientosConsistentes(List<Establecimiento> establecimientosRecibidos) {
    return establecimientosRecibidos.stream().allMatch(
            establecimiento -> establecimiento.getTipoEstablecimiento()==this.getTipoEstablecimientos()
    );
  }

  /**
   * param servicio: Lista de servicios
   */
  public void eliminarEstablecimientos(Establecimiento ... establecimiento) {
    for (Establecimiento value : establecimiento) {
      this.establecimientos.remove(value);
    }
  }
}
