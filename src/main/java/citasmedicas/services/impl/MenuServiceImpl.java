package citasmedicas.services.impl;

import citasmedicas.exceptions.MenuException;
import citasmedicas.models.entities.Menu;
import citasmedicas.repositories.MenuRepository;
import citasmedicas.services.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository repository;

    public MenuServiceImpl(MenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Menu> obtenerMenus() {
        return repository.findAll();
    }

    @Override
    public Menu guardar(Menu menu) {
        validarDatos(menu);
        return repository.save(menu);
    }

    @Override
    public Menu actualizar(Menu menu, Integer id) {
        Optional<Menu> menuConsultado = obtenerMenu(id);
        if (menuConsultado.isPresent()) {
            validarDatos(menu);
            Menu menuActualizar = new Menu();
            menuActualizar.setNombre(menu.getNombre());
            menuActualizar.setRuta("/".concat(menu.getRuta()));
            menuActualizar.setIcon(menu.getIcon());
            menuActualizar.setCodigo(menuConsultado.get().getCodigo());
            return repository.save(menuActualizar);
        }
        return null;
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Menu> obtenerMenu(Integer id) {
        Optional<Menu> menuConsultado = repository.findById(id);
        if (menuConsultado.isPresent()) {
            return menuConsultado;
        }
        throw new MenuException(MenuException.MENU_NO_ENCONTRADO);
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
