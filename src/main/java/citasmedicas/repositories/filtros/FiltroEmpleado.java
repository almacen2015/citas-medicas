package citasmedicas.repositories.filtros;

public record FiltroEmpleado(String nombre,
                             String apellidoPaterno,
                             String apellidoMaterno,
                             String numeroDocumento,
                             Boolean estado) {
}
