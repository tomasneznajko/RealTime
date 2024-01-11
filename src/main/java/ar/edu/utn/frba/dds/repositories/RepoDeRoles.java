package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.usuario.Rol;
import ar.edu.utn.frba.dds.models.domain.usuario.TipoRol;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;

public class RepoDeRoles implements WithSimplePersistenceUnit {
    public Rol buscarPorTipoRol(TipoRol valor) {
        String query = "FROM " + Rol.class.getName() + " rol WHERE rol.tipo = :valor";
        try {
            return entityManager()
                    .createQuery(query, Rol.class)
                    .setParameter("valor", valor)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void agregar(Rol rol){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(rol);
        tx.commit();
    }
    public void eliminar(Rol rol){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().remove(rol);
        tx.commit();
    }

    public void modificar(Rol rol){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().merge(rol);
        tx.commit();
    }

    public List<Rol> buscarTodos(){
        return entityManager().createQuery("from " + Rol.class.getName()).getResultList();

    }

    public Rol buscarPorId(Long id){
        return entityManager().find(Rol.class,id);
    }
}
