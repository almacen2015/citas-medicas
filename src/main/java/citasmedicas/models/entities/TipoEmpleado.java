package citasmedicas.models.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tipos_empleados")
public class TipoEmpleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;

    public TipoEmpleado() {
    }

    public TipoEmpleado(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public TipoEmpleado(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoEmpleado that = (TipoEmpleado) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return "TipoEmpleado{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
