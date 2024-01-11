package ar.edu.utn.frba.dds.models.domain.ranking;

import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RankingMayorPromedioCierre implements Ranking{

    private static RankingMayorPromedioCierre instance;

    public static RankingMayorPromedioCierre getInstance() {
        if (instance == null) {
            instance = new RankingMayorPromedioCierre();
        }
        return instance;
    }
    @Override
    public List<Entidad> generarRanking(List<Comunidad> comunidades, List<Entidad> entidades){
        Map<Entidad, Duration> promedioCierrePorEntidad = entidades.stream()
                .collect(Collectors.toMap(entidad -> entidad, entidad-> obtenerDuracionPromedioCierre(entidad,comunidades)));

        List<Entidad> resultados = promedioCierrePorEntidad.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return resultados;
    }

    @Override
    public String name() {
        return "Promedio de Cierre de Incidentes";
    }

    private Duration obtenerDuracionPromedioCierre(Entidad entidad, List<Comunidad> comunidades){
        LocalDateTime fechaHasta = LocalDateTime.now();
        LocalDateTime fechaDesde = fechaHasta.minusDays(7);

        List<Incidente> incidentesEntidad = comunidades.stream()
                .flatMap(comunidad -> comunidad.getIncidentes().stream())
                .filter(incidente -> !incidente.getAbierto())
                .filter(incidente -> incidente.getEstablecimiento().getEntidad().equals(entidad))
                .filter(incidente -> incidente.getFechaYHoraCierre().isAfter(fechaDesde) && incidente.getFechaYHoraCierre().isBefore(fechaHasta))
                .collect(Collectors.toList());

        if(incidentesEntidad.isEmpty()){
            return Duration.ZERO;
        }

        long duracionTotal = 0;
        int cantidadIncidentes = incidentesEntidad.size();

        for(Incidente incidente : incidentesEntidad){
            duracionTotal += calcularDuracionCierre(incidente).toMinutes();
        }

        return Duration.ofMinutes((duracionTotal / cantidadIncidentes));
    }

    private Duration calcularDuracionCierre(Incidente incidente){
        LocalDateTime horaApertura = incidente.getFachaYHoraApertura();
        LocalDateTime horaCierre = incidente.getFechaYHoraCierre();
        return Duration.between(horaApertura, horaCierre);
    }
}
