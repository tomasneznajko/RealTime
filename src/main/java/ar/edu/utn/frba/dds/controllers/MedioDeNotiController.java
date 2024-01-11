package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion.MedioDeNotificacion;
import ar.edu.utn.frba.dds.server.utils.ICrudViewsHandler;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import javax.persistence.EntityTransaction;

public class MedioDeNotiController implements WithSimplePersistenceUnit {


  public MedioDeNotificacion buscarPorId(Integer id){return entityManager().find(MedioDeNotificacion.class,id);}


  public void delete(MedioDeNotificacion medio) {
    EntityTransaction tx = entityManager().getTransaction();
    tx.begin();
    entityManager().remove(medio);
    tx.commit();
  }
}
