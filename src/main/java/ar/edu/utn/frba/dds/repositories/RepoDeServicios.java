package ar.edu.utn.frba.dds.repositories;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.domain.servicios.Servicio;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepoDeServicios  implements WithSimplePersistenceUnit {

    public void agregar(Servicio servicio){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(servicio);
        tx.commit();
    }
    public void eliminar(Servicio servicio){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().remove(servicio);
        tx.commit();
    }

    public void modificar(Servicio servicio){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().merge(servicio);
        tx.commit();
    }
    public Servicio buscarPorId(Integer id){
        return entityManager().find(Servicio.class,id);
    }
    public List<Servicio> buscarTodos(){
        return entityManager().createQuery("from " + Servicio.class.getName()).getResultList();

    }
}