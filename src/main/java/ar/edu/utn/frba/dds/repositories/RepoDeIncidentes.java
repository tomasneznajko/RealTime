package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.domain.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.domain.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.domain.incidentes.IncidenteResumido;
import ar.edu.utn.frba.dds.models.domain.serviciospublicos.Establecimiento;
import ar.edu.utn.frba.dds.repositories.convertsRepo.ConvertString;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.*;

public class RepoDeIncidentes  implements WithSimplePersistenceUnit {

    private ConvertString convertString;

    public RepoDeIncidentes(){
        this.convertString = new ConvertString();
    }

    public void agregar(Incidente incidente){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().persist(incidente);
        tx.commit();
    }
    public void eliminar(Incidente incidente){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().remove(incidente);
        tx.commit();
    }
    public List<IncidenteResumido> obtenerIncidentesPorLocalidadYProvincia(String localidad, String provincia) {
        TypedQuery<Incidente> query = entityManager().createQuery(
                "SELECT i FROM Incidente i " +
                        "JOIN i.establecimiento e " +
                        "JOIN e.localizacion l " +
                        "JOIN l.municipio m " +
                        "JOIN l.provincia p " +
                        "JOIN i.abridor a " +
                        "WHERE m.nombre = :localidad " +
                        "AND p.nombre = :provincia " +
                        " AND i.abierto = true" +
                        " ORDER BY a.id , i.fachaYHoraApertura", Incidente.class);

        query.setParameter("localidad", localidad);
        query.setParameter("provincia", provincia);





//        List<Incidente> incidentes = new ArrayList<>();
//
//        incidentes.add(incidente);

        return this.agruparIncidentes(query.getResultList());




       // return this.agruparIncidentes(incidentes);
    }


    public void modificar(Incidente incidente){
        EntityTransaction tx = entityManager().getTransaction();
        tx.begin();
        entityManager().merge(incidente);
        tx.commit();
    }
    public Incidente buscarPorId(UUID id){
        return entityManager().find(Incidente.class,id);
    }

    public List<Incidente> buscarPorListadoId(List<Long> ids){
        String idsSQL = this.convertString.converterListToString(ids);

        String query = "FROM " + Incidente.class.getName() + " incidente WHERE incidente.id in :idsSQL";

        return (List<Incidente>) entityManager()
                .createQuery(query)
                .setParameter("idsSQL", idsSQL)
                .getResultList();
    }
    public List<Incidente> buscarPorSemana(){
        String consultaSQL = "SELECT i FROM Incidente i WHERE DATEDIFF(CURDATE(), i.fachaYHoraApertura) <= 7";

        return entityManager().createQuery(consultaSQL, Incidente.class)
                .getResultList();
    }
    public List<IncidenteResumido> agruparIncidentes(List<Incidente> incidentes) {
    Map<String, IncidenteResumido> incidentesAgrupados = new HashMap<>();

    for (Incidente incidente : incidentes) {
        String claveAgrupacion = incidente.getAbridor() + "-" + incidente.getFachaYHoraApertura();

        if (incidentesAgrupados.containsKey(claveAgrupacion)) {
            IncidenteResumido incidenteResumido = incidentesAgrupados.get(claveAgrupacion);
            incidenteResumido.getComunidades().add(incidente.getComunidad());
        } else {
            IncidenteResumido incidenteResumido = new IncidenteResumido();
            incidenteResumido.setObservaciones(incidente.getObservaciones());
            incidenteResumido.setEstablecimiento(incidente.getEstablecimiento());
            incidenteResumido.setComunidades(new ArrayList<>(Collections.singletonList(incidente.getComunidad())));
            incidentesAgrupados.put(claveAgrupacion, incidenteResumido);
        }
    }

    return new ArrayList<>(incidentesAgrupados.values());
    }
    public List<Incidente> buscarTodos(){
        limpiarCache();
        return entityManager().createQuery("from " + Incidente.class.getName()).getResultList();

    }

    public void limpiarCache(){
        entityManager().clear();
    }


    public Incidente buscarPorIdLong(Long valor) {
        String query = "FROM " + Comunidad.class.getName() + " c WHERE c.idAmigable" + "= :valor";
        try {
            return (Incidente) entityManager()
                    .createQuery(query)
                    .setParameter("valor",valor)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}