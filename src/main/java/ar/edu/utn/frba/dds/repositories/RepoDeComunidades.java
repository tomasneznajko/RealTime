package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RepoDeComunidades  implements WithSimplePersistenceUnit {
    public void agregar(Comunidad comunidad) {
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(comunidad);
        tx.commit();
    }
    public void eliminar(Comunidad comunidad){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().remove(comunidad);
        tx.commit();
    }

    public void modificar(Comunidad comunidad){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().merge(comunidad);
        tx.commit();
    }
    public Comunidad buscarPorId(UUID id){
        return entityManager().find(Comunidad.class,id);
    }


/*    public List<Comunidad> buscarRestantes(Miembro miembro){
        String query = "FROM " + Comunidad.class.getName() + " comunidad WHERE comunidad." + atributo + " = :valor";
        try {
            return (Miembro) entityManager()
                    .createQuery(query)
                    .setParameter("valor", valor)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }*/
    public List<Comunidad> buscarTodos(){
        return entityManager().createQuery("from " + Comunidad.class.getName()).getResultList();
    }

    public List<Comunidad> buscarRestantesA(Miembro miembroActual) {
        return this.buscarTodos().stream().filter(comunidad -> !comunidad.getMiembros().contains(miembroActual)).collect(Collectors.toList());
    }

    public Comunidad buscarPorIdLong(Long valor) {
        String query = "FROM " + Comunidad.class.getName() + " c WHERE c.idAmigable" + "= :valor";
        try {
            return (Comunidad) entityManager()
                    .createQuery(query)
                    .setParameter("valor",valor)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}