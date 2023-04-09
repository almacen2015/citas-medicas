package citasmedicas.service.impl;

import citasmedicas.model.Cita;
import citasmedicas.model.dto.AreaDTO;
import citasmedicas.model.dto.CitaDTO;
import citasmedicas.model.dto.ClienteDTO;
import citasmedicas.repository.CitaRepository;
import citasmedicas.service.AreaService;
import citasmedicas.service.CitaService;
import citasmedicas.service.ClienteService;
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

    private ModelMapper modelMapper;

    public CitaServiceImpl() {
        modelMapper = new ModelMapper();
    }

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
        return null;
    }
}
