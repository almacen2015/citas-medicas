package citasmedicas.service.impl;

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
        return repository.save(menu);
    }
}
