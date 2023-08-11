package citasmedicas.models.dto;

public record CitaDTO(Integer id,
                      ClienteDTO clienteDTO,
                      AreaDTO areaDTO,
                      String titulo,
                      String fechaInicio,
                      String fechaFin,
                      String estado) {
}
