package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.builders.entidades.EstablecimientoBuilder;
import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;
import ar.edu.utn.frba.dds.models.domain.servicios.Banio;
import ar.edu.utn.frba.dds.models.domain.servicios.Escalador;
import ar.edu.utn.frba.dds.models.domain.servicios.Genero;
import ar.edu.utn.frba.dds.models.domain.servicios.PrestacionDeServicio;
import ar.edu.utn.frba.dds.models.domain.servicios.TipoTraslado;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.TipoEstablecimiento;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Ubicacion;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class EstablecimientoTest {
    private Establecimiento establecimiento;
    private EstablecimientoBuilder establecimientoBuilder;
    private Banio banio;
    private PrestacionDeServicio prestacionBanio;
    private Escalador ascensor;
    private PrestacionDeServicio prestacionAscensor;
    private Ubicacion ubicacion;
    @BeforeEach
    public void init() throws IOException {
        this.establecimientoBuilder = new EstablecimientoBuilder();

        this.ubicacion = new Ubicacion();
        this.ubicacion.setLatitud(1.00);
        this.ubicacion.setLongitud(-1.00);
        Localizacion localizacion10 = new Localizacion();
        localizacion10.setProvincia("Buenos Aires");
        this.establecimiento = this.establecimientoBuilder.conNombre("Flores").conTipo(TipoEstablecimiento.SUCURSAL).
                conLocalizacion(localizacion10).construir();
        this.establecimiento.setCentroide(ubicacion);

        this.banio = new Banio();
        this.banio.setGenero(Genero.HOMBRE);
        this.banio.setDiscapacitado(true);
        this.prestacionBanio = new PrestacionDeServicio();
        prestacionBanio.setServicio(this.banio);
        prestacionBanio.setCantidad(3);
        this.prestacionBanio.setFunciona(true);

        this.ascensor = new Escalador();
        this.ascensor.setOrigen(TipoTraslado.CALLE);
        this.ascensor.setDestino(TipoTraslado.BARRERA);
        this.prestacionAscensor = new PrestacionDeServicio();
        this.prestacionAscensor.setServicio(this.ascensor);
        this.prestacionAscensor.setCantidad(2);
        this.prestacionAscensor.setFunciona(true);

    }
    @Test
    @DisplayName("Agregamos dos prestaciones de servicios y damos de baja uno")
    public void EstacionAgregaDosPrestacionesYEliminaUno(){
        establecimiento.agregarPrestaciones(this.prestacionAscensor,this.prestacionBanio);
        establecimiento.darDeBajaPrestaciones(this.prestacionAscensor);

        Assert.assertFalse(establecimiento.getPrestacionesDeServicios().contains(prestacionAscensor));
    }


}
