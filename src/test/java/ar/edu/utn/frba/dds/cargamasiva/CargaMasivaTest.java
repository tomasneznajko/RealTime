package ar.edu.utn.frba.dds.cargamasiva;
import ar.edu.utn.frba.dds.models.domain.cargamasiva.CargadorEntidadesControladoras;
import ar.edu.utn.frba.dds.models.domain.cargamasiva.CargadorOrganismoDeControl;
import ar.edu.utn.frba.dds.models.domain.entidadesExtra.EntidadControladora;
import ar.edu.utn.frba.dds.models.domain.entidadesExtra.OrganismoDeControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.io.File;

public class CargaMasivaTest {

    @Test
    @DisplayName("Lectura exitosa de tres entidades a partir del archivo csv")
    public void lecturaDeTresEntidades(){
        String rutaCSV = "src\\test\\resources\\testcsvcorrecto.txt";
        File archivoCSV = new File(rutaCSV);
        List<EntidadControladora> entidadesControladoras = CargadorEntidadesControladoras.obtenerEntidadesControladoras(archivoCSV);

        Assertions.assertEquals(2,entidadesControladoras.size());
    }

    @Test
    @DisplayName("Lectura de csv vacio retorna lista vacia")
    public void lecturaDeCsvVacio(){
        String rutaCSV = "src\\test\\resources\\testcsvvacio.txt";
        File archivoCSV = new File(rutaCSV);
        List<OrganismoDeControl> organismosDeControl = CargadorOrganismoDeControl.obtenerOrganismosDeControl(archivoCSV);
        Assertions.assertEquals(0,organismosDeControl.size());
    }

    @Test
    @DisplayName("Lectura de csv inexistente retorna un null")
    public void lecturaDeCsvInexistente(){
        String rutaCSV = "src\\test\\resources\\testcsvinexistente.txt";
        File archivoCSV = new File(rutaCSV);
        List<OrganismoDeControl> organismosDeControl = CargadorOrganismoDeControl.obtenerOrganismosDeControl(archivoCSV);
        Assertions.assertEquals(0,organismosDeControl.size());
    }
}
