package ar.edu.utn.frba.dds.controllers;
import ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion.Email;
import ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion.Whatsapp;
import ar.edu.utn.frba.dds.models.domain.ValidadorContrasenias.ValidadorDeContrasenias;
import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.comunidades.gradosDeConfianza.GradoDeConfianza;
import ar.edu.utn.frba.dds.models.domain.comunidades.gradosDeConfianza.Puntaje;
import ar.edu.utn.frba.dds.models.domain.comunidades.gradosDeConfianza.TipoDeGrado;
import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.ServicioGeoref;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.Departamento;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.Municipio;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.Provincia;
import ar.edu.utn.frba.dds.models.domain.usuario.Rol;
import ar.edu.utn.frba.dds.models.domain.usuario.TipoRol;
import ar.edu.utn.frba.dds.repositories.*;
import ar.edu.utn.frba.dds.server.utils.ICrudViewsHandler;
import at.favre.lib.crypto.bcrypt.BCrypt;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context; //
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import io.javalin.http.HttpStatus; //
import javax.persistence.EntityTransaction; //
import org.jetbrains.annotations.NotNull; //

import java.util.*;
import java.util.stream.Collectors;

public class UsuariosController implements WithSimplePersistenceUnit {
    private static final Double puntajeInicial = 5.00;
    private RepoDeMiembros repoMiembros;
    private RepoDeMediosDeNotificacion repoDeMediosDeNotificacion;
    private RepoDeRoles repoDeRoles;
    private RepoDeProvincias repoDeProvincias;
    private RepoDeMunicipios repoDeMunicipios;
    private RepoDeDepartamentos repoDeDepartamentos;

    public UsuariosController(RepoDeMiembros repoMiembros, RepoDeMediosDeNotificacion repoDeMediosDeNotificacion, RepoDeRoles repoDeRoles,
        RepoDeProvincias repoDeProvincias, RepoDeMunicipios repoDeMunicipios, RepoDeDepartamentos repoDeDepartamentos){
        this.repoMiembros = repoMiembros;
        this.repoDeMediosDeNotificacion = repoDeMediosDeNotificacion;
        this.repoDeRoles = repoDeRoles;
        this.repoDeProvincias = repoDeProvincias;
        this.repoDeMunicipios = repoDeMunicipios;
        this.repoDeDepartamentos = repoDeDepartamentos;
    }

    public void login(Context context){
        context.render("Usuarios/login.hbs");
    }

    public void loginPost(Context context){
        String contrasenia = context.formParam("contrasenia");
        String nombreDeUsuario = context.formParam("nombreDeUsuario");

        Miembro miembro = this.repoMiembros.buscarPor("usuario", nombreDeUsuario);

        if(miembro == null || !BCrypt.verifyer().verify(contrasenia.getBytes(), miembro.getContrasenia().getBytes()).verified)
        {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("error", "Nombre de usuario o contraseña incorrecta");
            context.render("Usuarios/login.hbs", modelo);
            return;
        }
        else if(miembro.getPuntaje().getGradoDeConfianza().getNombre() == TipoDeGrado.NO_CONFIABLE){
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("error", "Usuario considerado no confiable");
            context.render("Usuarios/login.hbs", modelo);
            return;
        }
        context.sessionAttribute("usuario_id", miembro.getId());
        context.sessionAttribute("tipo_rol", miembro.getRol().getTipo().toString());

        context.redirect("/comunidades");
    }

    public void register(Context context) throws IOException {

        Map<String, Object> modelo = new HashMap<>();

        List<Provincia> provincias = ServicioGeoref.getInstance().listadoDeProvincias().provincias;
        provincias.sort(Comparator.comparing(Provincia::getNombre)); //Ordenamos
        modelo.put("provincias", provincias);
        context.render("Usuarios/register.hbs",modelo);
    }

    public void registerPost(Context context) throws IOException {
        Map<String, Object> modelo = new HashMap<>();

        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String contrasenia = context.formParam("contrasenia");
        String nombreDeUsuario = context.formParam("nombreDeUsuario");
        String email = context.formParam("email");
        String medioNotificacion = context.formParam("medioNotificacion");
        String provincia = context.formParam("provincia");
        String municipio = context.formParam("municipio");
        String departamento = context.formParam("departamento");


            //Validaciones
        if (!(new ValidadorDeContrasenias().esValida(contrasenia))) {
            modelo.put("error", "La contraseña no es valida");
            context.render("Usuarios/register.hbs", modelo);
            return;
        }

        Miembro miembroPorUsername = this.repoMiembros.buscarPor("usuario", nombreDeUsuario);
        if (miembroPorUsername != null) {
            modelo.put("error", "El nombre de usuario ya existe.");
            context.render("Usuarios/register.hbs", modelo);
            return;
        }

        Miembro miembroPorEmail = this.repoMiembros.buscarPor("mail", email);
        if (miembroPorEmail != null) {
            modelo.put("error", "El email ya está registrado.");
            context.render("Usuarios/register.hbs", modelo);
            return;
        }

        //Carga a db
        MedioDeNotificacion medioDeNotificacion;
        String atributo;
        String valor;

        if("telefono".equals(medioNotificacion)){
            String telefono = context.formParam("telefono");

            MedioDeNotificacion existe = repoDeMediosDeNotificacion.buscarPor("telefono", telefono);
            if(existe != null){
                modelo.put("error", "El teléfono ya está registrado.");
                context.render("Usuarios/register.hbs", modelo);
                return;
            }


            atributo = "telefono";
            valor = telefono;
            medioDeNotificacion = new Whatsapp(telefono);
        }
        else{
            medioDeNotificacion = new Email(email);
            atributo = "email";
            valor = email;
        }

        repoDeMediosDeNotificacion.agregar(medioDeNotificacion);
        MedioDeNotificacion medio = repoDeMediosDeNotificacion.buscarPor(atributo, valor);

        Miembro miembro = new Miembro();
        miembro.medioDeNotificacion = medio;
        miembro.setUsuario(nombreDeUsuario);
        miembro.setMail(email);
        miembro.setNombre(nombre);
        miembro.setApellido(apellido);

        Localizacion localizacion = new Localizacion();

        Provincia nuevaProvincia = ServicioGeoref.getInstance().buscarProvincia(provincia);

        System.out.println(nuevaProvincia);
        System.out.println(nuevaProvincia.id);
        System.out.println(nuevaProvincia.nombre);

        if(esNuevaProvincia(nuevaProvincia)){
            this.repoDeProvincias.agregar(nuevaProvincia);
        }
        localizacion.setProvincia(provincia);

        if(municipio!=""){
            Municipio nuevoMunicipio = ServicioGeoref.getInstance().buscarMunicipio(municipio,localizacion.getProvincia().id,localizacion.getMaxMunicipios());
            System.out.println(nuevoMunicipio);

            if(esNuevoMunicipio(nuevoMunicipio)){
                this.repoDeMunicipios.agregar(nuevoMunicipio);
            }
            localizacion.setMunicipio(municipio);
        }
        if (departamento != ""){
            Departamento nuevoDepartamento = ServicioGeoref.getInstance().buscarDepartamento(departamento,localizacion.getProvincia().id,localizacion.getMaxDepartamentos());
            if(esNuevoDepartamento(nuevoDepartamento)) {
                this.repoDeDepartamentos.agregar(nuevoDepartamento);
            }
            localizacion.setDepartamento(departamento);
        }

        miembro.setLocalizacion(localizacion);


        Puntaje puntaje = new Puntaje();
        puntaje.setValor(puntajeInicial);
        miembro.setPuntaje(puntaje);

        miembro.setRol(repoDeRoles.buscarPorTipoRol(TipoRol.NORMAL));
        String contraseniaHASH = BCrypt.withDefaults().hashToString(12, contrasenia.toCharArray());
        miembro.setContrasenia(contraseniaHASH);

        repoMiembros.agregar(miembro);

        modelo.put("exito", "Usuario registrado con exito");
        context.render("Usuarios/login.hbs", modelo);
    }

    private boolean esNuevoDepartamento(Departamento nuevoDepartamento) {
        List<Departamento> departamentos = this.repoDeDepartamentos.buscarTodos();
        return !departamentos.stream().anyMatch(departamento->departamento.id == nuevoDepartamento.id);
    }

    private boolean esNuevoMunicipio(Municipio nuevoMunicipio) {
        List<Municipio> municipios = this.repoDeMunicipios.buscarTodos();
        return !municipios.stream().anyMatch(municipio->municipio.id == nuevoMunicipio.id);
    }

    private boolean esNuevaProvincia(Provincia nuevaProvincia) {
        List<Provincia> provincias = this.repoDeProvincias.buscarTodos();
        return !provincias.stream().anyMatch(unaProvincia->unaProvincia.id == nuevaProvincia.id);
    }



    public void index(Context context){
        Map<String, Object> modelo = new HashMap<>();
        List<Miembro> usuarios = repoMiembros.buscarTodos();
        List<Rol> roles = repoDeRoles.buscarTodos();
        System.out.println(roles);
        modelo.put("roles",roles);
        modelo.put("usuarios", usuarios);
        context.render("Usuarios/index.hbs",modelo);
    }

    public void imagen(Context context){

            int miembroId = Integer.parseInt(context.pathParam("miembroId"));
            UploadedFile uploadedFile = context.uploadedFile("imagen");
            if (uploadedFile != null) {
                // Procesa y guarda la imagen

//                String nombreArchivo = uploadedFile.getFilename();
  //              String rutaArchivo = "ruta/donde/guardar/" + nombreArchivo;
    //            uploadedFile.getContent().transferTo(new File(rutaArchivo));

                // Asocia la ruta de la imagen con el miembro en la base de datos
      //          Miembro miembro = miembroService.getMiembroById(miembroId);
        //        miembro.getFotos().add(rutaArchivo);
          //      miembroService.actualizarMiembro(miembro);
            }
       // context.redirect("/exito");

    }


    public void show(Context context) throws IOException {
        Map<String, Object> modelo = new HashMap<>();
        //Long idUsuario = Long.parseLong(context.sessionAttribute("id"));
        Miembro usuario = repoMiembros.buscarPorId(context.sessionAttribute("usuario_id"));

        modelo.put("usuario", usuario);
        context.render("Usuarios/show.hbs",modelo);
}




    public void edit(Context context) throws IOException {
        Miembro usuario = this.repoMiembros.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        List<Provincia> provincias = ServicioGeoref.getInstance().listadoDeProvincias().provincias;
        provincias.sort(Comparator.comparing(Provincia::getNombre));
        model.put("usuario", usuario);
        model.put("provincias",provincias);
        context.render("Usuarios/admin.hbs", model);

    }


    public void update(Context context) throws IOException {
        Miembro usuario = repoMiembros.buscarPorId(context.sessionAttribute("usuario_id"));
        //Miembro usuario = (Miembro) this.repoMiembros.buscarPorId(Long.parseLong(context.pathParam("id")));

        this.asignarParametrosYactualizar(usuario, context);



  //      long id = Long.parseLong(context.pathParam("id"));



    //    String redirectTo = "/usuario;


        context.redirect("/usuario");


    }


    public void actualizar(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();
        entityManager().merge(o);
        tx.commit();
    }



    private void asignarParametrosYactualizar(Miembro usuario, Context context) throws IOException {
        Map<String, Object> modelo = new HashMap<>();

        String nombre = context.formParam("nombre");
        String apellido = context.formParam("apellido");
        String email = context.formParam("email");
        String medioNotificacion = context.formParam("medioSelect");
        String desc = context.formParam("descripcion");

/*        String provincia = context.formParam("provincia");
        String municipio = context.formParam("municipio");
        String departamento = context.formParam("departamento");*/



        if(medioNotificacion != null){
            Long viejo2 =  usuario.medioDeNotificacion.getId();
            usuario.setMedioDeNotificacion(null);
            this.repoMiembros.modificar(usuario);
            MedioDeNotificacion medioc = repoDeMediosDeNotificacion.buscarPorId(viejo2);
            repoDeMediosDeNotificacion.eliminar(medioc);
        }

        UploadedFile fotoFile = context.uploadedFile("foto");
        if (fotoFile != null) {
            String fotoFileName = fotoFile.filename();
            String rutaDestino = "src/main/resources/public/upload/";
            String rutaTotal = "upload/" + fotoFileName;

            try (InputStream fotoInputStream = fotoFile.content()) {
                File destino = new File(rutaDestino, fotoFileName);
                // Copiar el contenido del archivo al destino
                Files.copy(fotoInputStream, destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                List<String> nuevasFotos = new ArrayList<>();
                nuevasFotos.add(rutaTotal);
                usuario.setFotos(nuevasFotos);
           //     usuario.setFotos(Collections.singletonList(destino.getAbsolutePath()));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ha ocurrido un error al guardar el archivo: " + e.getMessage());
            }
        }

        Miembro miembroPorEmail = this.repoMiembros.buscarPor("mail", email);

        if (miembroPorEmail != null && usuario.getId() != miembroPorEmail.getId()){
            modelo.put("error", "El email es el registrado / ya se registro ese mail");
            context.render("Usuarios/admin.hbs", modelo);
            return;
        }  //mail registrado por otro usuario


        MedioDeNotificacion medioDeNotificacion;
        String atributo;
        String valor;

        if("whatsapp".equals(medioNotificacion)){
            String telefono = context.formParam("whatsappInput");

            MedioDeNotificacion existe = repoDeMediosDeNotificacion.buscarPor("telefono", telefono);
            if(existe != null){
                modelo.put("error", "El teléfono ya está registrado.");
                context.render("Usuarios/admin.hbs", modelo);
                return;
            }

            atributo = "telefono";
            valor = telefono;
            medioDeNotificacion = new Whatsapp(telefono);

        }
        else{
                medioDeNotificacion = new Email(email);
                atributo = "email";
                valor = email;
        }

        repoDeMediosDeNotificacion.agregar(medioDeNotificacion);

        MedioDeNotificacion medio = repoDeMediosDeNotificacion.buscarPor(atributo, valor);

        usuario.medioDeNotificacion = medio;
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(email);
        usuario.setDescripcion(desc);
        /*if(esNuevaProvincia(nuevaProvincia)){
            this.repoDeProvincias.agregar(nuevaProvincia);
        }
        localizacion.setProvincia(provincia);

        if(municipio!=""){
            Municipio nuevoMunicipio = ServicioGeoref.getInstance().buscarMunicipio(municipio,localizacion.getProvincia().id,localizacion.getMaxMunicipios());
            System.out.println(nuevoMunicipio);

            if(esNuevoMunicipio(nuevoMunicipio)){
                this.repoDeMunicipios.agregar(nuevoMunicipio);
            }
            localizacion.setMunicipio(municipio);
        }
        if (departamento != ""){
            Departamento nuevoDepartamento = ServicioGeoref.getInstance().buscarDepartamento(departamento,localizacion.getProvincia().id,localizacion.getMaxDepartamentos());
            if(esNuevoDepartamento(nuevoDepartamento)) {
                this.repoDeDepartamentos.agregar(nuevoDepartamento);
            }
            localizacion.setDepartamento(departamento);
        }

        if(!Objects.equals(provincia, null)){
            usuario.setLocalizacion(null);
            Localizacion localizacion = new Localizacion();
            localizacion.setProvincia(provincia);
            System.out.println(municipio);
            System.out.println(departamento);
            if(!Objects.equals(municipio, "")){
                localizacion.setMunicipio(municipio);
            }
            if(!Objects.equals(departamento, "")){
                localizacion.setDepartamento(departamento);
            }
            usuario.setLocalizacion(localizacion);
        }*/
        this.repoMiembros.modificar(usuario);

    }

    public void mostrarUsuario(Context context){
        Long idUsuario = Long.parseLong(context.pathParam("id"));
        Miembro usuario = repoMiembros.buscarPorId(idUsuario);
        List<Rol> roles = repoDeRoles.buscarTodos();

        Map<String, Object> modelo = new HashMap<>();
        modelo.put("usuario", usuario);

        context.render("Usuarios/show.hbs",modelo);

    }

    public void updateRol(Context context) {
        if (!Objects.equals(context.formParam("rol"), null)) {
            Miembro miembroModificable = this.repoMiembros.buscarPorId(Long.valueOf(context.pathParam("id")));
            miembroModificable.setRol(repoDeRoles.buscarPorId(Long.valueOf(context.formParam("rol"))));
            this.repoMiembros.modificar(miembroModificable);
        }
        context.status(HttpStatus.CREATED);
        context.redirect("/usuarios");
    }
}
