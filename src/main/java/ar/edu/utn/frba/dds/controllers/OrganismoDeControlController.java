package ar.edu.utn.frba.dds.controllers;
import ar.edu.utn.frba.dds.models.domain.cargamasiva.CargadorOrganismoDeControl;
import ar.edu.utn.frba.dds.models.domain.entidadesExtra.OrganismoDeControl;
import io.javalin.http.Context;
import ar.edu.utn.frba.dds.repositories.RepoOrganismoDeControl;
import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganismoDeControlController {
    private RepoOrganismoDeControl repoOrganismoDeControl;

    public OrganismoDeControlController(RepoOrganismoDeControl repoOrganismoDeControl){
        this.repoOrganismoDeControl = repoOrganismoDeControl;
    }

    public void cargar(Context context){
        context.render("OrganismosDeControl/cargar.hbs");
    }

    public void cargarPost(Context context) {
        Map<String, Object> modelo = new HashMap<>();
        UploadedFile archivo = context.uploadedFile("archivocsv");

        if (archivo == null) {
            modelo.put("error", "No se ha seleccionado ningún archivo.");
            context.render("OrganismosDeControl/cargar.hbs", modelo);
            return;
        }
        String nombreArchivo = archivo.filename();
        if (!nombreArchivo.endsWith(".txt")) {
            modelo.put("error", "El archivo debe ser de extension .txt");
            context.render("OrganismosDeControl/cargar.hbs", modelo);
            return;
        }
        try {
            File archivoFile = convertirUploadedFileAFile(archivo);
            List<OrganismoDeControl> organismosDeControl = CargadorOrganismoDeControl.obtenerOrganismosDeControl(archivoFile);

            for (OrganismoDeControl organismo : organismosDeControl) {
                repoOrganismoDeControl.agregar(organismo);
            }
        } catch (Exception e) {
            modelo.put("error", "Error al leer el archivo.");
            context.render("OrganismosDeControl/cargar.hbs", modelo);
            return;
        }
        modelo.put("exito", "Organismos de control cargados exitosamente");
        context.render("OrganismosDeControl/cargar.hbs",modelo);
    }

    private File convertirUploadedFileAFile(UploadedFile uploadedFile) throws IOException {
        File archivoTemporal = File.createTempFile("temp", ".txt");

        OutputStream outputStream = new FileOutputStream(archivoTemporal);
        uploadedFile.content().transferTo(outputStream);

        return archivoTemporal;
    }

}


