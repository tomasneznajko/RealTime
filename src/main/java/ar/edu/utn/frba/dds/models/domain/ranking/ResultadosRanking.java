package ar.edu.utn.frba.dds.models.domain.ranking;

import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Entidad;
import com.mysql.cj.xdevapi.JsonString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Getter @Setter
public class ResultadosRanking {

    private Map<Ranking, List<Entidad>> resultados;
    private LocalDateTime fechaCreacion;

    public ResultadosRanking(){
        this.resultados = new HashMap<>();
        this.fechaCreacion = LocalDateTime.now();
    }

    public void agregarRanking(Ranking ranking,List<Entidad> resultadoRanking){
        this.resultados.put(ranking, resultadoRanking);
    }
}
