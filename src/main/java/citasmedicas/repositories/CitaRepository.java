package citasmedicas.repositories;

import citasmedicas.models.entities.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findCitasByClienteIdAndEstado(Integer clienteId, String estado);

    List<Cita> findCitasByClienteId(Integer clienteId);

    @Query(value = "select c from Cita c where c.cliente.id =:clienteId and c.area.id =:areaId and DATE(c.fechaInicio) =:fechaInicio")
    List<Cita> findByClienteIdAndAreaIdAndFechaInicio(Integer clienteId, Integer areaId, LocalDateTime fechaInicio);

    @Query(value = "select c from Cita c where :fechaInicio between c.fechaInicio and c.fechaFin and c.estado=:estado")
    List<Cita> findCitaBetweenFechaInicioAndFechaFin(LocalDateTime fechaInicio, String estado);
}
