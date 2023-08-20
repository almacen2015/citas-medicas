package citasmedicas.repositories;

import citasmedicas.models.entities.TipoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoEmpleadoRepository extends JpaRepository<TipoEmpleado, Integer> {
    Optional<TipoEmpleado> findByNombre(String nombre);
}
