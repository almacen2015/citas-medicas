package citasmedicas.controller;

import citasmedicas.model.dto.AreaDTO;
import citasmedicas.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/area")
@CrossOrigin(origins = "http://localhost:4200")
public class AreaController {

    @Autowired
    private AreaService service;

    @GetMapping
    public ResponseEntity<?> listar() {
        return new ResponseEntity<>(service.listar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerArea(@PathVariable Integer id) {
        return new ResponseEntity<>(service.obtenerPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody AreaDTO areaDTO) {
        return new ResponseEntity<>(service.guardar(areaDTO), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@RequestBody AreaDTO areaDTO, @PathVariable Integer id) {
        return new ResponseEntity<>(service.actualizar(areaDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
