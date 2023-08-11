package citasmedicas.service.impl;

import citasmedicas.exceptions.CitaException;
import citasmedicas.mappers.CitaMapper;
import citasmedicas.models.dto.CitaDTO;
import citasmedicas.models.entities.Cita;
import citasmedicas.models.enums.Estado;
import citasmedicas.repository.CitaRepository;
import citasmedicas.service.AreaService;
import citasmedicas.service.CitaService;
import citasmedicas.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {
    private final CitaRepository repository;
    private final AreaService areaService;
    private final ClienteService clienteService;
    private final CitaMapper citaMapper = CitaMapper.INSTANCE;

    public CitaServiceImpl(CitaRepository repository, AreaService areaService, ClienteService clienteService) {
        this.repository = repository;
        this.areaService = areaService;
        this.clienteService = clienteService;
    }

    @Override
    public List<CitaDTO> listar() {
        List<Cita> citas = repository.findAll();
        List<CitaDTO> citaDTOS = new ArrayList<>();
        for (Cita cita : citas) {
            CitaDTO citaDTO = citaMapper.citaToCitaDTO(cita);
            citaDTOS.add(citaDTO);
        }
        return citaDTOS;
    }

    @Override
    public CitaDTO guardar(CitaDTO citaDTO) {
        Cita cita = citaMapper.citaDTOToCita(citaDTO);
        validarDatos(cita);
        Cita newCita = repository.save(cita);
        return citaMapper.citaToCitaDTO(newCita);
    }

    private void validarDatos(Cita cita) {
        if (cita.getTitulo().equals("")) {
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
