package citasmedicas.models.dto;

public record ClienteDTO(Integer id,
                         String nombre,
                         String apellidoPaterno,
                         String apellidoMaterno,
                         String numeroDocumento,
                         String fechaNacimiento,
                         String sexo,
                         String telefono,
                         String email) {
}
