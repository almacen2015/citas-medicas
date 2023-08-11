package citasmedicas.models.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private String nombre;
    private String ruta;
    private String icon;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(codigo, menu.codigo) && Objects.equals(nombre, menu.nombre) && Objects.equals(ruta, menu.ruta) && Objects.equals(icon, menu.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nombre, ruta, icon);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", ruta='" + ruta + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
