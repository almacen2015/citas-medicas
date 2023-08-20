package citasmedicas.models.dto;

public record EmpleadoDTO(String id,
                          String nombre,
                          String apellidoPaterno,
                          String apellidoMaterno,
                          String numeroDocumento,
                          TipoEmpleadoDTO tipoEmpleadoDTO,
                          Boolean estado) {
}
