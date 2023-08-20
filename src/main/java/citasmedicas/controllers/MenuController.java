package citasmedicas.controllers;

import citasmedicas.models.entities.Menu;
import citasmedicas.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "http://localhost:4200")
public class MenuController {

    @Autowired
    private MenuService service;

    @GetMapping
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(service.obtenerMenus(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Menu menu) {
        return new ResponseEntity<>(service.guardar(menu), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@RequestBody Menu menu, @PathVariable Integer id) {
        return new ResponseEntity<>(service.actualizar(menu, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerMenu(@PathVariable Integer id) {
        return new ResponseEntity<>(service.obtenerMenu(id), HttpStatus.OK);
    }
}
