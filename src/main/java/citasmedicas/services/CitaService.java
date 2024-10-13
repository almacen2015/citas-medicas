package citasmedicas.services;


import citasmedicas.models.dto.CitaDTO;

import java.util.List;

public interface CitaService {
    List<CitaDTO> listar();

    CitaDTO guardar(CitaDTO citaDTO);

    List<CitaDTO> listarCitasPorCliente(Integer clienteId);
}
