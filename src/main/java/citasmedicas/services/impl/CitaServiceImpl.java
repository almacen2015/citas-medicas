package citasmedicas.services.impl;

import citasmedicas.exceptions.CitaException;
import citasmedicas.models.dto.AreaDTO;
import citasmedicas.models.dto.CitaDTO;
import citasmedicas.models.dto.ClienteDTO;
import citasmedicas.models.entities.Cita;
import citasmedicas.models.mappers.CitaMapper;
import citasmedicas.repositories.CitaRepository;
import citasmedicas.repositories.ClienteRepository;
import citasmedicas.services.AreaService;
import citasmedicas.services.CitaService;
import citasmedicas.services.ClienteService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {
    private final CitaRepository repository;
    private final ClienteService clienteService;
    private final AreaService areaService;
    private final CitaMapper citaMapper = CitaMapper.INSTANCE;

    public CitaServiceImpl(CitaRepository repository, ClienteService clienteService, AreaService areaService, ClienteRepository clienteRepository) {
        this.clienteService = clienteService;
        this.areaService = areaService;
        this.repository = repository;
    }

    @Override
    public List<CitaDTO> listarCitasPorCliente(Integer clienteId, String estado) {
        esClienteIdValido(clienteId);
        List<Cita> citasEncontradas = null;
        if (!estado.isEmpty()) {
            citasEncontradas = repository.findCitasByClienteIdAndEstado(clienteId, estado);
        } else {
            citasEncontradas = repository.findCitasByClienteId(clienteId);
        }
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
        validarDatos(citaDTO);
        Cita cita = citaMapper.citaDTOToCita(citaDTO);
        Cita newCita = repository.save(cita);
        return citaMapper.citaToCitaDTO(newCita);
    }

    private void esClienteIdValido(Integer clienteId) {
        if (clienteId == null || clienteId <= 0) {
            throw new CitaException(CitaException.CLIENTE_NO_VALIDO);
        }
    }

    private void validarDatos(CitaDTO citaDTO) {
        final String titulo = citaDTO.titulo();
        final AreaDTO area = citaDTO.areaDTO();
        final ClienteDTO cliente = citaDTO.clienteDTO();
        final String fechaInicio = citaDTO.fechaInicio();
        final String fechaFin = citaDTO.fechaFin();

        validarTitulo(titulo);
        validarArea(area);
        validarCliente(cliente);
        convertAndValidateDate(fechaInicio);
        convertAndValidateDate(fechaFin);

        Cita cita = citaMapper.citaDTOToCita(citaDTO);

        if (existeCitaMismoClienteAreaYFecha(cita)) {
            throw new CitaException(CitaException.EXISTE_CITA_MISMA_AREA);
        }

        if (existeCitaEntreRangoDeFechaYHora(cita)) {
            throw new CitaException(CitaException.EXISTE_CITA_MISMA_HORA);
        }
    }

    private void validarArea(AreaDTO area) {
        areaService.obtenerPorId(area.id());
    }

    private void validarCliente(ClienteDTO clienteDTO) {
        clienteService.obtenerClientePorId(clienteDTO.id());
    }

    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty() || titulo.isBlank() || titulo.length() > 255) {
            throw new CitaException(CitaException.TITULO_NO_VALIDO);
        }
    }

    private boolean existeCitaMismoClienteAreaYFecha(Cita cita) {
        Integer idCliente = cita.getCliente().getId();
        Integer idArea = cita.getArea().getId();
        List<Cita> citas = repository.findByClienteIdAndAreaIdAndFechaInicio(idCliente, idArea, cita.getFechaInicio());
        return !citas.isEmpty();
    }

    private boolean existeCitaEntreRangoDeFechaYHora(Cita cita) {
        final LocalDateTime fechaInicio = cita.getFechaInicio();
        final LocalDateTime fechaFin = cita.getFechaFin();
        List<Cita> citas = repository.findByFechaInicioBetween(fechaInicio, fechaFin);
        return !citas.isEmpty();
    }

    public LocalDateTime convertAndValidateDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            // Intentar parsear la fecha
            return LocalDateTime.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            // Si la fecha no es válida, lanzar una excepción o manejar el error
            System.out.println("Fecha no válida: " + dateStr);
            return null;
        }
    }
}
