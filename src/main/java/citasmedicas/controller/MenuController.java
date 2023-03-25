package citasmedicas.controller;

import citasmedicas.model.Menu;
import citasmedicas.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService service;

    @GetMapping
    public ResponseEntity<?> listar(){
        return new ResponseEntity<>(service.obtenerMenus(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Menu menu){
        return new ResponseEntity<>(service.guardar(menu), HttpStatus.OK);
    }
}
