package citasmedicas.services.impl;

import citasmedicas.exceptions.CitaException;
import citasmedicas.models.dto.CitaDTO;
import citasmedicas.models.entities.Cita;
import citasmedicas.models.enums.Estado;
import citasmedicas.models.mappers.CitaMapper;
import citasmedicas.repositories.CitaRepository;
import citasmedicas.services.CitaService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {
    private final CitaRepository repository;
    private final CitaMapper citaMapper = CitaMapper.INSTANCE;

    public CitaServiceImpl(CitaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CitaDTO> listarCitasPorCliente(Integer clienteId) {
        esClienteIdValido(clienteId);
        List<Cita> citasEncontradas = repository.findCitasByClienteId(clienteId);
        return citaMapper.citasToCitasDTO(citasEncontradas);
    }

    @Override
    public List<CitaDTO> listar() {
        List<Cita> citas = repository.findAll();
        return citaMapper.citasToCitasDTO(citas);
    }

    @Override
    @Transactional(rollbackOn = CitaException.class)
    public CitaDTO guardar(CitaDTO citaDTO) {
        Cita cita = citaMapper.citaDTOToCita(citaDTO);
        validarDatos(cita);
        Cita newCita = repository.save(cita);
        return citaMapper.citaToCitaDTO(newCita);
    }

    private void esClienteIdValido(Integer clienteId) {
        if (clienteId == null || clienteId <= 0) {
            throw new CitaException(CitaException.CLIENTE_NO_VALIDO);
        }
    }

    private void validarDatos(Cita cita) {
        if (cita.getTitulo().isEmpty()) {
            throw new CitaException(CitaException.TITULO_NO_VALIDO);
        }
        if (cita.getArea() == null) {
            throw new CitaException(CitaException.AREA_NO_VALIDO);
        }
        if (cita.getCliente() == null) {
            throw new CitaException(CitaException.CLIENTE_NO_VALIDO);
        }
        if (cita.getFechaInicio() == null) {
            throw new CitaException(CitaException.FECHA_INICIO_NO_VALIDO);
        }
        if (cita.getFechaFin() == null) {
            throw new CitaException(CitaException.FECHA_FIN_NO_VALIDO);
        }
        if (existeCitaMismoClienteAreaYFecha(cita)) {
            throw new CitaException(CitaException.EXISTE_CITA_MISMA_AREA);
        }
        if (existeCitaEntreRangoDeFechaYHora(cita)) {
            throw new CitaException(CitaException.EXISTE_CITA_MISMA_HORA);
        }
    }

    private boolean existeCitaMismoClienteAreaYFecha(Cita cita) {
        Integer idCliente = cita.getCliente().getId();
        Integer idArea = cita.getArea().getId();
        List<Cita> citas = repository.findByClienteIdAndAreaIdAndFechaInicio(idCliente, idArea, cita.getFechaInicio());
        return !citas.isEmpty();
    }

    private boolean existeCitaEntreRangoDeFechaYHora(Cita cita) {
        String estado = String.valueOf(Estado.Activo);
        List<Cita> citas = repository.findCitaBetweenFechaInicioAndFechaFin(cita.getFechaInicio(), estado);
        return !citas.isEmpty();
    }
}
