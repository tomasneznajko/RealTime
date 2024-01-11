package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.domain.cargamasiva.CargadorEntidadesControladoras;
import ar.edu.utn.frba.dds.models.domain.cargamasiva.CargadorOrganismoDeControl;
import ar.edu.utn.frba.dds.models.domain.entidadesExtra.EntidadControladora;
import ar.edu.utn.frba.dds.models.domain.entidadesExtra.OrganismoDeControl;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;
import ar.edu.utn.frba.dds.repositories.RepoEntidadControladora;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntidadControladoraController {

    private RepoEntidadControladora repoEntidadControladora;

    public EntidadControladoraController(RepoEntidadControladora repoEntidadControladora){
        this.repoEntidadControladora = repoEntidadControladora;
    }

    public void cargar(Context context){
        context.render("EntidadControladora/cargar.hbs");
    }

    public void cargarPost(Context context) {
        Map<String, Object> modelo = new HashMap<>();
        UploadedFile archivo = context.uploadedFile("archivocsv");

        if (archivo == null) {
            modelo.put("error", "No se ha seleccionado ningún archivo.");
            context.render("EntidadControladora/cargar.hbs", modelo);
            return;
        }
        String nombreArchivo = archivo.filename();
        if (!nombreArchivo.endsWith(".txt")) {
            modelo.put("error", "El archivo debe ser de extension .txt");
            context.render("EntidadControladora/cargar.hbs", modelo);
            return;
        }
        try {
            File archivoFile = convertirUploadedFileAFile(archivo);
            List<EntidadControladora> entidadesControladoras = CargadorEntidadesControladoras.obtenerEntidadesControladoras(archivoFile);

            for (EntidadControladora entidad : entidadesControladoras) {
                repoEntidadControladora.agregar(entidad);
            }
        } catch (Exception e) {
            modelo.put("error", "Error al leer el archivo.");
            context.render("EntidadControladora/cargar.hbs", modelo);
            return;
        }
        modelo.put("exito", "Entidades Controladoras cargadas exitosamente");
        context.render("EntidadControladora/cargar.hbs", modelo);
    }

        private File convertirUploadedFileAFile(UploadedFile uploadedFile) throws IOException {
            File archivoTemporal = File.createTempFile("temp", ".txt");

            OutputStream outputStream = new FileOutputStream(archivoTemporal);
            uploadedFile.content().transferTo(outputStream);

            return archivoTemporal;
        }
}
