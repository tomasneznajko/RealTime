package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.Provincia;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepoDeProvincias implements WithSimplePersistenceUnit {
    public void agregar(Provincia provincia){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(provincia);
        tx.commit();
    }
    public void eliminar(Provincia provincia){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().remove(provincia);
        tx.commit();
    }

    public void modificar(Provincia provincia){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().merge(provincia);
        tx.commit();
    }
    public Provincia buscarPorId(Integer id){
        return entityManager().find(Provincia.class,id);
    }
    public List<Provincia> buscarTodos(){
        return entityManager().createQuery("from " + Provincia.class.getName()).getResultList();

    }
}
