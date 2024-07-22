package citasmedicas.repositories.Impl;

import citasmedicas.models.entities.Empleado;
import citasmedicas.repositories.customs.EmpleadoRepositoryCustom;
import citasmedicas.repositories.filtros.FiltroEmpleado;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EmpleadoRespositoryImpl implements EmpleadoRepositoryCustom {

    private final EntityManager entityManager;

    public EmpleadoRespositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Empleado> buscarEmpleado(FiltroEmpleado filtroEmpleado) {
        return List.of();
    }
}
