package citasmedicas.models.dto;

public record EmpleadoDTO(Integer id,
                          String nombre,
                          String apellidoPaterno,
                          String apellidoMaterno,
                          String numeroDocumento,
                          TipoEmpleadoDTO tipoEmpleadoDTO,
                          Boolean estado) {
}
