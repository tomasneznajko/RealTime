package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.domain.entidadesExtra.OrganismoDeControl;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepoOrganismoDeControl implements WithSimplePersistenceUnit {
    public void agregar(OrganismoDeControl organismoDeControl){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(organismoDeControl);
        tx.commit();
    }

    public List<OrganismoDeControl> buscarTodos(){
        return entityManager().createQuery("from " + OrganismoDeControl.class.getName()).getResultList();

    }
}
