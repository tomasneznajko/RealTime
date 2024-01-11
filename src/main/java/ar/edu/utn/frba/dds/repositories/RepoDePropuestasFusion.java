package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.comunidades.PropuestaFusion;
import ar.edu.utn.frba.dds.models.domain.services_api.georef.entities.Provincia;
import ar.edu.utn.frba.dds.repositories.convertsRepo.ConvertString;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepoDePropuestasFusion implements WithSimplePersistenceUnit {

    private ConvertString convertString;

    public RepoDePropuestasFusion(){
        this.convertString = new ConvertString();
    }
    public void agregar(PropuestaFusion propuestaFusion) {
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(propuestaFusion);
        tx.commit();
    }

    public void eliminar(PropuestaFusion propuestaFusion) {
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().remove(propuestaFusion);
        tx.commit();
    }

    public void modificar(PropuestaFusion propuestaFusion) {
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().merge(propuestaFusion);
        tx.commit();
    }

    public List<PropuestaFusion> buscarPorComunidades(Long comunidadFusionable1, Long comunidadFusionable2,List<Long> comunidadesPropuestas) {
        String query = "FROM " + PropuestaFusion.class.getName() + " propuesta " +
                "WHERE propuesta.comunidad_fusionable_id = :comunidadFusionable1 or propuesta.comunidad_fusionable_id = :comunidadFusionable2" +
                "and propuesta.id_comunidad_propuesta in :comunidadesPropuestas";

        return (List<PropuestaFusion>) entityManager()
                .createQuery(query)
                .setParameter("comunidadFusionable1",comunidadFusionable1)
                .setParameter("comunidadFusionable2",comunidadFusionable2)
                .setParameter("comunidadesPropuestas", comunidadesPropuestas)
                .getResultList();
    }

    public List<PropuestaFusion> buscarPorListadoId(List<Long> ids){
        return null;
    }

    public List<PropuestaFusion> buscarTodos() {
        return entityManager().createQuery("from " + PropuestaFusion.class.getName()).getResultList();

    }
}
