package citasmedicas.service;

import citasmedicas.model.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> obtenerMenus();

    Menu guardar(Menu menu);
}
