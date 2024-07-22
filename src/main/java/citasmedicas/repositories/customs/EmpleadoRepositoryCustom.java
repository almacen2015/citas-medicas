package citasmedicas.repositories.customs;

import citasmedicas.models.entities.Empleado;
import citasmedicas.repositories.filtros.FiltroEmpleado;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmpleadoRepositoryCustom {
    List<Empleado> buscarPorFiltroEmpleado(FiltroEmpleado filtroEmpleado, Pageable paginado);

}
