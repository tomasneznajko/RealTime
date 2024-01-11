package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.domain.localizaciones.Localizacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepoDeLocalizaciones  implements WithSimplePersistenceUnit {
    public void agregar(Localizacion localizacion){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(localizacion);
        tx.commit();
    }
    public void eliminar(Localizacion localizacion){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().remove(localizacion);
        tx.commit();
    }

    public void modificar(Localizacion localizacion){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().merge(localizacion);
        tx.commit();
    }
    public Localizacion buscarPorId(Integer id){
        return entityManager().find(Localizacion.class,id);
    }
    public List<Localizacion> buscarTodos(){
        return entityManager().createQuery("from " + Localizacion.class.getName()).getResultList();

    }
}