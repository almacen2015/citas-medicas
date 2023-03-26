package citasmedicas.service;

import citasmedicas.model.Menu;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(rollbackFor = Exception.class)
public interface MenuService {
    List<Menu> obtenerMenus();

    Menu guardar(Menu menu);

    Menu actualizar(Menu menu, Integer id);

    void eliminar(Integer id);

    Optional<Menu> obtenerMenu(Integer id);
}
