package ar.edu.utn.frba.dds.models.domain.ranking;

import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public class GeneradorRanking {
    private List<Ranking> rankings;

    @Getter
    private ResultadosRanking resultadosRanking;
    private static GeneradorRanking instance;

    public static GeneradorRanking getInstance() {
        if (instance == null) {
            instance = new GeneradorRanking();
        }
        return instance;
    }
    public GeneradorRanking() {
        this.rankings = new ArrayList<>();
        this.resultadosRanking = new ResultadosRanking();
    }

    public void agregarRanking(Ranking ranking) {
        this.rankings.add(ranking);
    }

    public ResultadosRanking generarRankings(List<Comunidad> comunidades, List<Entidad> entidades){
        for(Ranking ranking : this.rankings){
            List<Entidad> resultados = ranking.generarRanking(comunidades, entidades);
            resultadosRanking.agregarRanking(ranking, resultados);
        }

        return resultadosRanking;
    }
}
