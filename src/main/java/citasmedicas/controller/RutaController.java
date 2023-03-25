package citasmedicas.controller;

import citasmedicas.model.Ruta;
import citasmedicas.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ruta")
@CrossOrigin(origins = "http://localhost:4200")
public class RutaController {

    @Autowired
    private RutaService service;

    @GetMapping
    public ResponseEntity<?> listarRutas() {
        return new ResponseEntity<>(service.obtenerRutas(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> guardarRuta(@RequestBody Ruta ruta) {
        return new ResponseEntity<>(service.guardar(ruta), HttpStatus.CREATED);
    }
}
