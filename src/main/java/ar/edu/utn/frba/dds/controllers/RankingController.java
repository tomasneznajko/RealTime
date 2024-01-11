package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.controllers.Controller;
import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.ranking.*;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingController extends Controller {
    private Ranking ranking;
    private GeneradorRanking generadorRanking;
    public RankingController(Ranking ranking) {
        this.ranking = ranking;
        this.generadorRanking = GeneradorRanking.getInstance();
    }
    public void ranking(Context context) {
        if (miembroEnSesion(context) == null) {
            context.redirect("/login");
        }
        Map<String, Object> model = new HashMap<>();
        List<Entidad> listaRanking = this.generadorRanking.getResultadosRanking().
                                          getResultados().
                                          get(ranking);
        model.put("titulo", ranking.name());
        model.put
                ("ranking",
                    listaRanking.subList(0, Integer.min(9, listaRanking.size())) // Recortar la lista hasta que solo hayan 10 elementos?
                );
        context.render("ranking/ranking.hbs", model);
    }
}
