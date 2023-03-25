package citasmedicas.service;

import citasmedicas.model.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {
    List<Menu> obtenerMenus();

    Menu guardar(Menu menu);

    Optional<Menu> actualizar(Menu menu, Integer id);

    void eliminar(Integer id);
}
