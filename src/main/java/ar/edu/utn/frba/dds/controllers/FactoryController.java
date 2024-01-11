package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.comunidadesController.ComunidadesController;
import ar.edu.utn.frba.dds.controllers.comunidadesController.incidentes.IncidentesController;
import ar.edu.utn.frba.dds.models.domain.ranking.RankingMayorCantidadIncidentes;
import ar.edu.utn.frba.dds.models.domain.ranking.RankingMayorGradoImpactoProblematicas;
import ar.edu.utn.frba.dds.models.domain.ranking.RankingMayorPromedioCierre;
import ar.edu.utn.frba.dds.repositories.*;

public class FactoryController {
    public static Object controller(String nombre) {
        Object controller = null;
        switch (nombre) {
            case "Usuarios":
                controller = new UsuariosController(new RepoDeMiembros(), new RepoDeMediosDeNotificacion(), new RepoDeRoles(),new RepoDeProvincias(),new RepoDeMunicipios(),new RepoDeDepartamentos());
                break;
            case "EntidadesControladoras":
                controller = new EntidadControladoraController(new RepoEntidadControladora());
                break;
            case "OrganismosDeControl":
                controller = new OrganismoDeControlController(new RepoOrganismoDeControl());
                break;
            case "Comunidades":
                controller = new ComunidadesController(new RepoDeComunidades(), new RepoDeMiembros(),new RepoDeIncidentes(),new RepoDePropuestasFusion());
                break;
            case "Incidentes":
                controller = new IncidentesController(new RepoDeComunidades(), new RepoDeIncidentes(), new RepoDePrestacionDeServicio(), new RepoDeEstablecimientos(), new RepoDeMiembros());
                break;
            case "Ranking/cantIncidentes":
                controller = new RankingController(RankingMayorCantidadIncidentes.getInstance());
                break;
            case "Ranking/gradoImpacto":
                controller = new RankingController(RankingMayorGradoImpactoProblematicas.getInstance());
                break;
            case "Ranking/promCierre":
                controller = new RankingController(RankingMayorPromedioCierre.getInstance());
                break;
        }
        return controller;
    }
}
