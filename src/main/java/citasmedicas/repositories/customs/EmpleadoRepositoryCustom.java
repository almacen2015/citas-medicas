package citasmedicas.repositories.customs;

import citasmedicas.models.entities.Empleado;
import citasmedicas.repositories.filtros.FiltroEmpleado;

import java.util.List;

public interface EmpleadoRepositoryCustom {
    List<Empleado> buscarEmpleado(FiltroEmpleado filtroEmpleado);
}
