package ar.edu.utn.frba.dds.models.domain.ValidadorContrasenias;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ValidacionCompararContraPeores implements Validable{
    private static List<String> contraseñasComunes;

    public ValidacionCompararContraPeores() {
        this.contraseñasComunes = new ArrayList<>();
    }
    public boolean esValida(String clave) {
        if (contraseñasComunes == null) {
            leerContraseñasDesdeArchivo();
        }

        for (String contraseña : contraseñasComunes) {
            if (contraseña.equals(clave)) {
                return false;
            }
        }

        return true;
    }

    private void leerContraseñasDesdeArchivo() {
        Path path = Paths.get("src/main/java/ValidadorContrasenias/10000-most-common-passwords.txt");
        File archivo = new File(path.toAbsolutePath().toString());

        try (Scanner lector = new Scanner(archivo)) {
            while (lector.hasNextLine()) {
                this.contraseñasComunes.add(lector.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

