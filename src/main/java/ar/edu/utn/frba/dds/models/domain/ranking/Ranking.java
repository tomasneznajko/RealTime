package ar.edu.utn.frba.dds.models.domain.ranking;

import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;
import java.util.List;


public interface Ranking {
    List<Entidad> generarRanking(List<Comunidad> comunidades, List<Entidad> entidades);
    String name();
}
