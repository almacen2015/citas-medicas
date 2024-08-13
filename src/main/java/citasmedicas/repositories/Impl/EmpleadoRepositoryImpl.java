package citasmedicas.repositories.Impl;

import citasmedicas.models.entities.Empleado;
import citasmedicas.repositories.customs.EmpleadoRepositoryCustom;
import citasmedicas.repositories.filtros.FiltroEmpleado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpleadoRepositoryImpl implements EmpleadoRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Empleado> buscarPorFiltroEmpleado(FiltroEmpleado filtroEmpleado, Pageable paginado) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Empleado> cq = cb.createQuery(Empleado.class);
        Root<Empleado> empleadoRoot = cq.from(Empleado.class);

        String nombre = filtroEmpleado.nombre();
        String apellidoPaterno = filtroEmpleado.apellidoPaterno();
        String apellidoMaterno = filtroEmpleado.apellidoMaterno();
        Boolean estado = filtroEmpleado.estado();
        String numeroDocumento = filtroEmpleado.numeroDocumento();

        List<Predicate> predicados = new ArrayList<>();

        if (esDatoValido(nombre)) {
            predicados.add(cb.like(cb.lower(empleadoRoot.get("nombre")), "%" + nombre.toLowerCase() + "%"));
        }

        if (esDatoValido(apellidoPaterno)) {
            predicados.add(cb.like(cb.lower(empleadoRoot.get("apellidoPaterno")), "%" + apellidoPaterno.toLowerCase() + "%"));
        }

        if (esDatoValido(apellidoMaterno)) {
            predicados.add(cb.like(cb.lower(empleadoRoot.get("apellidoMaterno")), "%" + apellidoMaterno.toLowerCase() + "%"));
        }

        if (esDatoValido(numeroDocumento)) {
            predicados.add(cb.equal(empleadoRoot.get("numeroDocumento"), numeroDocumento));
        }

        if (esEstadoValido(estado)) {
            predicados.add(cb.equal(empleadoRoot.get("estado"), estado));
        }

        cq.where(cb.and(predicados.toArray(new Predicate[0])));

        TypedQuery<Empleado> query = entityManager.createQuery(cq);
        query.setMaxResults(paginado.getPageSize());
        query.setFirstResult((int) paginado.getOffset());

        return query.getResultList();

    }

    private boolean esEstadoValido(Boolean estado) {
        return estado != null;
    }

    private boolean esDatoValido(String dato) {
        return dato != null && !dato.isEmpty() && !dato.isBlank();
    }
}
