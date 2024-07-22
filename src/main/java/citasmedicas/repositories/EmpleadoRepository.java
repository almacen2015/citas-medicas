package citasmedicas.repositories;

import citasmedicas.models.entities.Empleado;
import citasmedicas.repositories.customs.EmpleadoRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>, EmpleadoRepositoryCustom {
    List<Empleado> findAllByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    List<Empleado> findAllByApellidoPaternoContainingIgnoreCase(String apellido, Pageable pageable);

    Optional<Empleado> findByNumeroDocumento(String numeroDocumento);

    List<Empleado> findAllByEstado(boolean estado, Pageable pageable);
}
