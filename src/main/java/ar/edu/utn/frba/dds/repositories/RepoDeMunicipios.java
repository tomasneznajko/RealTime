package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.Municipio;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.Provincia;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepoDeMunicipios implements WithSimplePersistenceUnit {
    public void agregar(Municipio municipio){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(municipio);
        tx.commit();
    }
    public void eliminar(Municipio municipio){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().remove(municipio);
        tx.commit();
    }

    public void modificar(Municipio municipio){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().merge(municipio);
        tx.commit();
    }
    public Municipio buscarPorId(Integer id){
        return entityManager().find(Municipio.class,id);
    }
    public List<Municipio> buscarTodos(){
        return entityManager().createQuery("from " + Municipio.class.getName()).getResultList();

    }
}

