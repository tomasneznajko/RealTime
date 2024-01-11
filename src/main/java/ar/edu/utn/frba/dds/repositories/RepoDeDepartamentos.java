package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.Departamento;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.Municipio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepoDeDepartamentos implements WithSimplePersistenceUnit {
    public void agregar(Departamento departamento){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(departamento);
        tx.commit();
    }
    public void eliminar(Departamento departamento){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().remove(departamento);
        tx.commit();
    }

    public void modificar(Departamento departamento){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().merge(departamento);
        tx.commit();
    }
    public Departamento buscarPorId(Integer id){
        return entityManager().find(Departamento.class,id);
    }
    public List<Departamento> buscarTodos(){
        return entityManager().createQuery("from " + Departamento.class.getName()).getResultList();

    }
}