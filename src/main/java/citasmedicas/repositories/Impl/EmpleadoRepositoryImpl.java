package citasmedicas.repositories.Impl;

import citasmedicas.models.entities.Empleado;
import citasmedicas.repositories.customs.EmpleadoRepositoryCustom;
import citasmedicas.repositories.filtros.FiltroEmpleado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpleadoRepositoryImpl implements EmpleadoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Empleado> buscarPorFiltroEmpleado(FiltroEmpleado filtroEmpleado) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Empleado> cq = cb.createQuery(Empleado.class);
        Root<Empleado> empleadoRoot = cq.from(Empleado.class);

        String nombre = filtroEmpleado.nombre();
        String apellidoPaterno = filtroEmpleado.apellidoPaterno();
        String apellidoMaterno = filtroEmpleado.apellidoMaterno();
        Boolean estado = filtroEmpleado.estado();
        String numeroDocumento = filtroEmpleado.numeroDocumento();

        List<Predicate> predicados = new ArrayList<>();

        if (nombre != null && !nombre.isEmpty() && !nombre.isBlank()) {
            predicados.add(cb.like(cb.lower(empleadoRoot.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }

        if (apellidoPaterno != null && !apellidoPaterno.isEmpty() && !apellidoPaterno.isBlank()) {
            predicados.add(cb.like(cb.lower(empleadoRoot.get("apellidoPaterno")), "%" + apellidoPaterno.toLowerCase() + "%"));
        }

        if (apellidoMaterno != null && !apellidoMaterno.isEmpty() && !apellidoMaterno.isBlank()) {
            predicados.add(cb.like(cb.lower(empleadoRoot.get("apellidoMaterno")), "%" + apellidoMaterno.toLowerCase() + "%"));
        }

        if (numeroDocumento != null && !numeroDocumento.isEmpty() && !numeroDocumento.isBlank()) {
            predicados.add(cb.equal(empleadoRoot.get("numeroDocumento"), numeroDocumento));
        }

        if (estado != null) {
            predicados.add(cb.equal(empleadoRoot.get("estado"), estado));
        }

        cq.where(cb.and(predicados.toArray(new Predicate[0])));

        return entityManager.createQuery(cq).getResultList();

    }
}
