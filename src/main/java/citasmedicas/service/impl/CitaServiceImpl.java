package citasmedicas.service.impl;

import citasmedicas.exceptions.CitaException;
import citasmedicas.model.Cita;
import citasmedicas.model.dto.AreaDTO;
import citasmedicas.model.dto.CitaDTO;
import citasmedicas.model.dto.ClienteDTO;
import citasmedicas.repository.CitaRepository;
import citasmedicas.service.AreaService;
import citasmedicas.service.CitaService;
import citasmedicas.service.ClienteService;
import citasmedicas.model.enums.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository repository;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CitaDTO> listar() {
        List<Cita> citas = repository.findAll();
        List<CitaDTO> citaDTOS = new ArrayList<>();
        for (Cita cita : citas) {
            Optional<AreaDTO> areaDTO = areaService.obtenerPorId(cita.getArea().getId());
            Optional<ClienteDTO> clienteDTO = clienteService.obtenerCliente(cita.getCliente().getId());
            CitaDTO citaDTO = modelMapper.map(cita, CitaDTO.class);
            citaDTO.setAreaDTO(areaDTO.get());
            citaDTO.setClienteDTO(clienteDTO.get());
            citaDTOS.add(citaDTO);
        }
        return citaDTOS;
    }

    @Override
    public CitaDTO guardar(CitaDTO citaDTO) {
        Cita cita = modelMapper.map(citaDTO, Cita.class);
        validarDatos(cita);
        return modelMapper.map(repository.save(cita), CitaDTO.class);
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
