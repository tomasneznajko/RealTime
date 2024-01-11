package ar.edu.utn.frba.dds.models.domain.ranking;

import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankingMayorCantidadIncidentes implements Ranking {
    private static RankingMayorCantidadIncidentes instance;

    public static RankingMayorCantidadIncidentes getInstance() {
        if (instance == null) {
            instance = new RankingMayorCantidadIncidentes();
        }
        return instance;
    }

    @Override
    public List<Entidad> generarRanking(List<Comunidad> comunidades, List<Entidad> entidades) {
        LocalDateTime fechaDesde = LocalDateTime.now().minusDays(7);
        LocalDateTime fechaHasta = LocalDateTime.now();

        return entidades.stream()
                .sorted(Comparator.comparingInt(entidad -> obtenerCantidadIncidentes(entidad, comunidades, fechaDesde, fechaHasta)))
                .collect(Collectors.toList());
    }

    @Override
    public String name() {
        return "Cantidad de Incidentes";
    }

    private int obtenerCantidadIncidentes(Entidad entidad, List<Comunidad> comunidades, LocalDateTime fechaDesde, LocalDateTime fechaHasta){
        List<Incidente> incidentesEntidad = filtrarIncidentes(entidad,comunidades,fechaDesde,fechaHasta);
        return incidentesEntidad.size();
    }

    private List<Incidente> filtrarIncidentes(Entidad entidad, List<Comunidad> comunidades, LocalDateTime fechaDesde, LocalDateTime fechaHasta){
        return comunidades.stream()
                .flatMap(comunidad -> comunidad.getIncidentes().stream())
                .filter(incidente -> incidente.getEstablecimiento().getEntidad().equals(entidad))
                .filter(incidente -> incidente.getFechaYHoraCierre().isAfter(fechaDesde) && incidente.getFechaYHoraCierre().isBefore(fechaHasta))
                .collect(Collectors.toList());
    }
}
