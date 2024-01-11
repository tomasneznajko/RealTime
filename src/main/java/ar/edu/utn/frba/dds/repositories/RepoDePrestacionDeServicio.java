package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.domain.servicios.PrestacionDeServicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepoDePrestacionDeServicio  implements WithSimplePersistenceUnit {

    public void agregar(PrestacionDeServicio prestacionDeServicio){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(prestacionDeServicio);
        tx.commit();
    }
    public void eliminar(PrestacionDeServicio prestacionDeServicio){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().remove(prestacionDeServicio);
        tx.commit();
    }

    public void modificar(PrestacionDeServicio prestacionDeServicio){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().merge(prestacionDeServicio);
        tx.commit();
    }
    public PrestacionDeServicio buscarPorId(Long id){
        return entityManager().find(PrestacionDeServicio.class,id);
    }

    public List<PrestacionDeServicio> buscarPorFuncionamiento(Boolean funciona){
        String consultaSQL = "SELECT p FROM PrestacionDeServicio p WHERE p.funciona = :parametro";
        return entityManager().createQuery(consultaSQL, PrestacionDeServicio.class)
                .setParameter("parametro", funciona)
                .getResultList();
    }
    public List<PrestacionDeServicio> buscarTodos(){
        return entityManager().createQuery("from " + PrestacionDeServicio.class.getName()).getResultList();

    }
}