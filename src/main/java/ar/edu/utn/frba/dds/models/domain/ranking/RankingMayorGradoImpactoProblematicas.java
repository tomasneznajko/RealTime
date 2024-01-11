package ar.edu.utn.frba.dds.models.domain.ranking;

import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RankingMayorGradoImpactoProblematicas implements Ranking{

    private static RankingMayorGradoImpactoProblematicas instance;

    public static RankingMayorGradoImpactoProblematicas getInstance() {
        if (instance == null) {
            instance = new RankingMayorGradoImpactoProblematicas();
        }
        return instance;
    }

    @Override
    public List<Entidad> generarRanking(List<Comunidad> comunidades, List<Entidad> entidades) {
        Map<Entidad, Double> promedioImpactoPorEntidad = entidades.stream()
                .collect(Collectors.toMap(entidad -> entidad, entidad ->calcularPromedioImpacto(entidad, comunidades)));

        List<Entidad> resultados = promedioImpactoPorEntidad.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return resultados;
    }

    @Override
    public String name() {
        return "Grado de Impacto de Problematicas";
    }

    private Double calcularPromedioImpacto(Entidad entidad, List<Comunidad> comunidades){
        List<Incidente> incidentesEntidad = comunidades.stream()
                .flatMap(comunidad -> comunidad.getIncidentes().stream())
                .filter(incidente -> incidente.getEstablecimiento().getEntidad().equals(entidad))
                .collect(Collectors.toList());

        if(incidentesEntidad.isEmpty()){
            return 0.0;
        }

        int totalMiembros = 0;
        int totalIncidentes = incidentesEntidad.size();

        for(Incidente incidente : incidentesEntidad){
            totalMiembros += miembrosComunidadDelIncidente(incidente,comunidades).getMiembros().size();
        }

        double promedioImpactoEntidad = totalMiembros / totalIncidentes;
        return promedioImpactoEntidad;
    }

    private Comunidad miembrosComunidadDelIncidente(Incidente incidente, List<Comunidad> comunidades){
        return comunidades.stream()
                .filter(comunidad -> comunidad.getIncidentes().contains((incidente)))
                .findFirst()
                .orElse(null);
    }
}
