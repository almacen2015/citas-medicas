package citasmedicas.service.impl;

import citasmedicas.exceptions.MenuException;
import citasmedicas.model.Menu;
import citasmedicas.repository.MenuRepository;
import citasmedicas.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository repository;

    @Override
    public List<Menu> obtenerMenus() {
        return repository.findAll();
    }

    @Override
    public Menu guardar(Menu menu) {
        validarDatos(menu);
        return repository.save(menu);
    }

    private void validarDatos(Menu menu) {
        if (menu.getNombre().equals("")) {
            throw new MenuException(MenuException.NOMBRE_NO_VALIDO);
        }
        if (menu.getRuta().equals("")) {
            throw new MenuException(MenuException.RUTA_NO_VALIDA);
        }
    }
}
