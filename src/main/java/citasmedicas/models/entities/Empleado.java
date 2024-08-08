package citasmedicas.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String numeroDocumento;
    @ManyToOne
    @JoinColumn(name = "tipoempleado_id")
    private TipoEmpleado tipoEmpleado;
    private Boolean estado;

    @Override
    public String toString() {
        return "Empleado{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                ", tipoEmpleado=" + tipoEmpleado +
                ", estado=" + estado +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(id, empleado.id) && Objects.equals(nombre, empleado.nombre) && Objects.equals(apellidoPaterno, empleado.apellidoPaterno) && Objects.equals(apellidoMaterno, empleado.apellidoMaterno) && Objects.equals(numeroDocumento, empleado.numeroDocumento) && Objects.equals(tipoEmpleado, empleado.tipoEmpleado) && Objects.equals(estado, empleado.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellidoPaterno, apellidoMaterno, numeroDocumento, tipoEmpleado, estado);
    }
}
