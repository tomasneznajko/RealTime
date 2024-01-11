package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion.Notificador;
import ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion.Whatsapp;
import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.comunidades.gradosDeConfianza.Puntaje;
import ar.edu.utn.frba.dds.models.domain.incidentes.AperturaIncidente;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;
import ar.edu.utn.frba.dds.models.domain.ranking.GeneradorRanking;
import ar.edu.utn.frba.dds.models.domain.ranking.RankingMayorCantidadIncidentes;
import ar.edu.utn.frba.dds.models.domain.ranking.RankingMayorGradoImpactoProblematicas;
import ar.edu.utn.frba.dds.models.domain.ranking.RankingMayorPromedioCierre;
import ar.edu.utn.frba.dds.models.domain.servicios.*;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.*;
import ar.edu.utn.frba.dds.models.excepciones.TipoEstablecimientoInvalidoExcepcion;
import ar.edu.utn.frba.dds.repositories.*;
import ar.edu.utn.frba.dds.server.utils.cronTasks.ConfigurationTask;


import java.io.IOException;
import java.util.List;

import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException, TipoEstablecimientoInvalidoExcepcion {

        ConfigurationTask.actualizarPuntajes(); //Preparamos cron de puntajes
        cargarCosas(); //Cargamos cosas iniciales para no tener app vacía
        Server.init(); //Acción :D

    }

    public static void somosDeChaco(RepoDeLocalizaciones repoDeLocalizaciones, Miembro... miembros) throws IOException {
        for(Miembro miembro : miembros){
            Localizacion localizacion = new Localizacion();
            localizacion.setProvincia("Chaco");

            /*repoDeLocalizaciones.agregar(localizacion);*/

            miembro.setLocalizacion(localizacion);
        }
    }

    public static void cargarCosas() throws TipoEstablecimientoInvalidoExcepcion, IOException {

        RepoDeProvincias repoDeProvincias = new RepoDeProvincias();
        RepoDeLocalizaciones repoDeLocalizaciones = new RepoDeLocalizaciones();
        RepoDeMediosDeNotificacion repoDeMediosDeNotificacion = new RepoDeMediosDeNotificacion();
        RepoDeEntidades repoDeEntidades = new RepoDeEntidades();
        RepoDeComunidades repoDeComunidades = new RepoDeComunidades();
        RepoDeMiembros repoDeMiembros = new RepoDeMiembros();
        RepoDePrestacionDeServicio repoDePrestacionDeServicio = new RepoDePrestacionDeServicio();
        RepoDeEstablecimientos repoDeEstablecimientos = new RepoDeEstablecimientos();

        Comunidad comunidad1 = new Comunidad();
        Puntaje puntaje1 = new Puntaje();
        puntaje1.setValor(5.00);
        comunidad1.setPuntaje(puntaje1);
        comunidad1.setDescripcion("RUM RUM RUUUUUM");
        comunidad1.setNombre("Sillas de ruedas");

        Comunidad comunidad2 = new Comunidad();
        Puntaje puntaje2 = new Puntaje();
        puntaje2.setValor(5.00);
        comunidad2.setPuntaje(puntaje2);
        comunidad2.setDescripcion("Que linda se ve mi comunidad");
        comunidad2.setNombre("No videntes");

        Comunidad comunidad3 = new Comunidad();
        Puntaje puntaje3 = new Puntaje();
        puntaje3.setValor(5.00);
        comunidad3.setPuntaje(puntaje3);
        comunidad3.setDescripcion("Nunca vimos una comunidad tan buena");
        comunidad3.setNombre("No videntes la secuela");

        Comunidad comunidad4 = new Comunidad();
        Puntaje puntaje4 = new Puntaje();
        puntaje4.setValor(2.00);
        comunidad4.setPuntaje(puntaje4);
        comunidad4.setDescripcion("Profe aprobanos, por favor");
        comunidad4.setNombre("Con bastones");

        Miembro miembro1 = new Miembro();
        miembro1.setUsuario("Pepe");
        Whatsapp wpp1 = new Whatsapp();
        wpp1.setTelefono("1140253180");
        System.out.println("Inserto medio de notificación.");
        repoDeMediosDeNotificacion.agregar(wpp1);
        miembro1.setMedioDeNotificacion(wpp1);
        Puntaje puntajeMiembro1 = new Puntaje();
        puntajeMiembro1.setValor(3.00);
        miembro1.setPuntaje(puntajeMiembro1);

        Miembro miembro2 = new Miembro();
        miembro2.setUsuario("Fede");
        miembro2.setContrasenia("12345678");
//    miembros.agregar(miembro2);
        Whatsapp wpp2 = new Whatsapp();
        wpp2.setTelefono("1140253180");
        miembro2.setMedioDeNotificacion(wpp2);
        repoDeMediosDeNotificacion.agregar(wpp2);
        Puntaje puntajeMiembro2 = new Puntaje();
        puntajeMiembro2.setValor(5.00);
        miembro2.setPuntaje(puntajeMiembro2);

        Miembro miembro3 = new Miembro();
        miembro3.setUsuario("Agus");
        Whatsapp wpp3 = new Whatsapp();
        wpp3.setTelefono("1140253180");
        repoDeMediosDeNotificacion.agregar(wpp3);
        miembro3.setMedioDeNotificacion(wpp3);
        Puntaje puntajeMiembro3 = new Puntaje();
        puntajeMiembro3.setValor(5.00);
        miembro3.setPuntaje(puntajeMiembro3);

        Miembro miembro4 = new Miembro();
        miembro4.setUsuario("Facu");
//    miembros.agregar(miembro4);
        Whatsapp wpp4 = new Whatsapp();
        wpp4.setTelefono("1140253180");
        repoDeMediosDeNotificacion.agregar(wpp4);
        miembro4.setMedioDeNotificacion(wpp4);
        Puntaje puntajeMiembro4 = new Puntaje();
        puntajeMiembro4.setValor(5.00);
        miembro4.setPuntaje(puntajeMiembro4);

        Miembro miembro5 = new Miembro();
        miembro5.setUsuario("Tomi");
        Whatsapp wpp5 = new Whatsapp();
        wpp5.setTelefono("1140253180");
        repoDeMediosDeNotificacion.agregar(wpp5);
        miembro5.setMedioDeNotificacion(wpp5);
        Puntaje puntajeMiembro5 = new Puntaje();
        puntajeMiembro5.setValor(5.00);
        miembro5.setPuntaje(puntajeMiembro5);

        comunidad1.agregarUsuarios(miembro1, miembro2, miembro3, miembro4);
        comunidad2.agregarUsuarios(miembro1,miembro2,miembro3);
        comunidad3.agregarUsuarios(miembro1,miembro2);
        comunidad4.agregarUsuarios(miembro1,miembro2);
        Localizacion localizacion = new Localizacion();
        localizacion.setProvincia("Chaco");
        System.out.println("Inserto provincia.");
        repoDeProvincias.agregar(localizacion.getProvincia());
        System.out.println("Inserto localización.");
        repoDeLocalizaciones.agregar(localizacion);
        miembro1.setLocalizacion(localizacion);
        somosDeChaco(repoDeLocalizaciones, miembro2, miembro3, miembro4, miembro5);

        System.out.println("Inserto miembro.");
        repoDeMiembros.agregar(miembro1);
        repoDeMiembros.agregar(miembro2);
        repoDeMiembros.agregar(miembro3);
        repoDeMiembros.agregar(miembro4);
        repoDeMiembros.agregar(miembro5);


        RepoDeServicios repoDeServicios = new RepoDeServicios();

        Servicio banio = new Banio();
        ((Banio) banio).setDiscapacitado(true);
        ((Banio) banio).setGenero(Genero.UNISEX);
        System.out.println("Inserto baño.");
        repoDeServicios.agregar(banio);

        Servicio escalador = new Escalador();
        ((Escalador) escalador).setOrigen(TipoTraslado.CALLE);
        ((Escalador) escalador).setOrigen(TipoTraslado.BARRERA);
        System.out.println("Inserto escalador.");
        repoDeServicios.agregar(escalador);

        PrestacionDeServicio prestacionBanio = new PrestacionDeServicio();
        prestacionBanio.setNombreServicio("Baño unisex");
        prestacionBanio.setCantidad(2);
        prestacionBanio.setFunciona(false);
        prestacionBanio.setServicio(banio);
        System.out.println("Inserto prestación baño.");
        repoDePrestacionDeServicio.agregar(prestacionBanio);

        PrestacionDeServicio prestacionEscalador = new PrestacionDeServicio();
        prestacionEscalador.setNombreServicio("Escalera mecánica");
        prestacionEscalador.setCantidad(2);
        prestacionEscalador.setFunciona(true);
        prestacionEscalador.setServicio(escalador);
        System.out.println("Inserto prestación escalador.");
        repoDePrestacionDeServicio.agregar(prestacionEscalador);

        Localizacion localizacionEntidad = new Localizacion();
        localizacionEntidad.setProvincia("Chaco");
        System.out.println("Inserto localización de entidad.");
        repoDeLocalizaciones.agregar(localizacionEntidad);

        Localizacion localizacionEstablecimiento = new Localizacion();
        localizacionEstablecimiento.setProvincia("Chaco");
        System.out.println("Inserto localización de establecimiento.");
        repoDeLocalizaciones.agregar(localizacionEstablecimiento);

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setLatitud(1.00);
        ubicacion.setLongitud(-1.00);

        Establecimiento establecimiento = new Establecimiento();
        establecimiento.setNombre("Estación de la Isla de Cerrito");
        establecimiento.setLocalizacion(localizacionEstablecimiento);
        establecimiento.agregarPrestaciones(prestacionEscalador, prestacionBanio);
        establecimiento.setCentroide(ubicacion);
        establecimiento.setTipoEstablecimiento(TipoEstablecimiento.ESTACION);
        System.out.println("Inserto establecimiento.");
        repoDeEstablecimientos.agregar(establecimiento);

        Entidad entidad = new Entidad();
        entidad.setNombre("Tren Sarmiento");
        entidad.setLocalizacion(localizacionEntidad);
        entidad.setTipoEntidad(TipoEntidad.LINEA_TRANSPORTE);
        entidad.setTipoEstablecimientos(TipoEstablecimiento.ESTACION);
        entidad.agregarEstablecimientos(establecimiento);
        establecimiento.setEntidad(entidad);
        System.out.println("Inserto entidad.");
        repoDeEntidades.agregar(entidad);

        AperturaIncidente aperturaIncidente = new AperturaIncidente();
        aperturaIncidente.setObservaciones("El baño está tapadisimo :c");
        aperturaIncidente.setPrestacionDeServicio(prestacionBanio);
        aperturaIncidente.setEstablecimiento(establecimiento);

        List<Incidente> incidentes = miembro1.abrirIncidente(aperturaIncidente, new Notificador());
        RepoDeIncidentes repoDeIncidentes = new RepoDeIncidentes();
        incidentes.forEach(repoDeIncidentes::agregar);

        System.out.println("Inserto comunidad.");
        repoDeComunidades.agregar(comunidad1);
        repoDeComunidades.agregar(comunidad2);
        repoDeComunidades.agregar(comunidad3);
        repoDeComunidades.agregar(comunidad4);

        GeneradorRanking generadorRanking = GeneradorRanking.getInstance();
        generadorRanking.agregarRanking(RankingMayorCantidadIncidentes.getInstance());
        generadorRanking.agregarRanking(RankingMayorGradoImpactoProblematicas.getInstance());
        generadorRanking.agregarRanking(RankingMayorPromedioCierre.getInstance());
        generadorRanking.generarRankings(repoDeComunidades.buscarTodos(), repoDeEntidades.buscarTodos());
        System.out.println(generadorRanking.getResultadosRanking().getResultados().get(RankingMayorCantidadIncidentes.getInstance()).get(0).getNombre());

    }

}

