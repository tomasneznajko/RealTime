package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.builders.entidades.EstablecimientoBuilder;
import ar.edu.utn.frba.dds.models.builders.entidades.MiembroBuilder;
import ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion.Whatsapp;
import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;
import ar.edu.utn.frba.dds.models.excepciones.NoEsAdministradorExcepcion;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.TipoEstablecimiento;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Ubicacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ComunidadTest {
    private Comunidad comunidad;
    private MiembroBuilder miembroBuilder = new MiembroBuilder();
    private Miembro cualquiera;
    private Miembro administrador;
    private Establecimiento establecimiento;
    private EstablecimientoBuilder establecimientoBuilder;
    private Ubicacion ubicacion;


    @BeforeEach
    public void init() throws IOException {
        this.establecimientoBuilder = new EstablecimientoBuilder();
        this.ubicacion = new Ubicacion();
        this.ubicacion.setLatitud(1.00);
        this.ubicacion.setLongitud(-1.00);
        Localizacion localizacion5 = new Localizacion();
        localizacion5.setProvincia("Buenos Aires");
        this.establecimiento = establecimientoBuilder.conNombre("Flores").conTipo(TipoEstablecimiento.ESTACION).
                conLocalizacion(localizacion5).construir();
        this.establecimiento.setCentroide(ubicacion);

        Localizacion localizacion6 = new Localizacion();
        localizacion5.setProvincia("chaco");
        this.cualquiera = miembroBuilder.conCuenta("Thompson","Nosejaja").conLocalizacion(localizacion6).construir();
        Whatsapp whatsapp = new Whatsapp();
        whatsapp.setTelefono("1144134775");

        Localizacion localizacion7 = new Localizacion();
        localizacion7   .setProvincia("Chaco");

        this.administrador = miembroBuilder.conCuenta("adminResponsable","cuidador123").conLocalizacion(localizacion7).
            conMedioDeNotidicacion(whatsapp).construir();
            /*conMedioDeNotidicacion(new Email("tomas.neznajko@gmail.com")).construir();*/



        this.comunidad = new Comunidad();
    }

    @Test
    @DisplayName("Agregar un servicio nuevo en una estación sin ser administrador de la comunidad")
    public void agregarServicioSinSerAdmin(){
        Assertions.assertThrows(NoEsAdministradorExcepcion.class,()->{
        //    this.comunidad.ingresarServicioNuevo(this.establecimiento, this.cualquiera,"servicio","muyInclusivo",3);
        });
    }

    @Test
    @DisplayName("Agregar un servicio nuevo en una estación siendo administrador de la comunidad")
    public void agregarServicioSiendoAdmin() throws NoEsAdministradorExcepcion {
        Assertions.assertDoesNotThrow(()->{
            //this.comunidad.agregarAdministradores(this.administrador);
           // this.comunidad.ingresarServicioNuevo(this.establecimiento,this.administrador,"servicioBueno","servicioMuyResponsable",2);
        });
    }

 /*     @Test
    @DisplayName("Agregar un servicio nuevo en una estación siendo administrador de la comunidad")
    public void AbrirIncidente(){
        LocalTime horaInicio1 = LocalTime.of(3, 20);
        LocalTime horaFin1 = LocalTime.of(12, 30);
        RangoHorario rangoHorario1 = new RangoHorario(horaInicio1, horaFin1);
        LocalTime horaInicio2 = LocalTime.of(18, 20);
        LocalTime horaFin2 = LocalTime.of(21, 0);
        RangoHorario rangoHorario2 = new RangoHorario(horaInicio2, horaFin2);
        List<RangoHorario> rangosHorariosElegidos = new ArrayList<>();
        rangosHorariosElegidos.add(rangoHorario1);
        rangosHorariosElegidos.add(rangoHorario2);
        this.administrador.getMedioDeNotificacion().setRangosHorariosElegidos(rangosHorariosElegidos);

        PrestacionDeServicio prestacionDeServicio = new PrestacionDeServicio(new Banio(), 3);
        prestacionDeServicio.setNombreServicio("Baño 1");
        this.establecimiento.agregarPrestaciones(prestacionDeServicio);
        this.comunidad.agregarAdministradores(this.administrador);
        this.comunidad.abrirIncidente(administrador,"Hola",this.establecimiento,prestacionDeServicio);
    } */

    // Ejemplo 1: Crear un rango horario de 8:00 a 12:30


    // Ejemplo 2: Crear otro rango horario de 13:30 a 18:00




}
