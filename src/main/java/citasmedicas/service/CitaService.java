package citasmedicas.service;


import citasmedicas.models.dto.CitaDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
public interface CitaService {
    List<CitaDTO> listar();

    CitaDTO guardar(CitaDTO citaDTO);
}
