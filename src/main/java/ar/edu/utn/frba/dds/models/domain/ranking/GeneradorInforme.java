package ar.edu.utn.frba.dds.models.domain.ranking;

import ar.edu.utn.frba.dds.models.domain.entidadesExtra.EntidadControladora;
import ar.edu.utn.frba.dds.models.domain.entidadesExtra.OrganismoDeControl;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
public class GeneradorInforme {
    public String generarInformeEntidadControladora(EntidadControladora entidadControladora, ResultadosRanking resultadosRankings){
        String tipoInforme = "Informe para Entidad Controladora\n";
        List<Entidad> entidadesEntidadControladora = entidadControladora.getEntidades();
        return generarInforme(tipoInforme, entidadesEntidadControladora, resultadosRankings);
    }

    public String generarInformeOrganismoDeControl(OrganismoDeControl organismoDeControl, ResultadosRanking resultadosRanking){
        String tipoInforme = "Informe para Organismo De Control\n";
        List<Entidad> entidadesEntidadControladora = organismoDeControl.getEntidades();
        return generarInforme(tipoInforme, entidadesEntidadControladora, resultadosRanking);
    }

    private String generarInforme(String tipoInforme, List<Entidad> entidades, ResultadosRanking resultadosRanking){
        StringBuilder informe = new StringBuilder();
        informe.append("Fecha: ").append(resultadosRanking.getFechaCreacion().format(DateTimeFormatter.ISO_DATE)).append("\n");
        informe.append(tipoInforme);
        int posicion;
        for(Map.Entry<Ranking,List<Entidad>> datoRanking : resultadosRanking.getResultados().entrySet()){
            Ranking ranking = datoRanking.getKey();
            List<Entidad> entidadesRanking = datoRanking.getValue();

            informe.append("Ranking: ").append(ranking.getClass().getSimpleName()).append("\n");

            posicion = 1;
            for(Entidad entidadRanking : entidadesRanking){
                if(entidades.contains(entidadRanking)){
                    informe.append("Posicion ")
                            .append(posicion).append(": ")
                            .append(entidadRanking.getNombre())
                            .append("\n");
                }
            }
            informe.append("\n\n");
        }
        return informe.toString();
    }
}
