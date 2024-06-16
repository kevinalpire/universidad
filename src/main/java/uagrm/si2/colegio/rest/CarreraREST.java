package uagrm.si2.colegio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uagrm.si2.colegio.model.Carrera;
import uagrm.si2.colegio.model.TipoAsistencia;
import uagrm.si2.colegio.service.CarreraService;

import java.net.URI;
import java.util.List;

@PreAuthorize("hasRole('administrador')")
@RestController
@RequestMapping("/carrera/")
public class CarreraREST {

    @Autowired
    private CarreraService carreraService;

    @GetMapping(path = "/listar")
    public ResponseEntity<List<Carrera>> read() {
        return ResponseEntity.ok(carreraService.findAll());
    }

    @PostMapping(path = "/crear")
    public ResponseEntity<Carrera> create(@RequestBody Carrera carrera){
        try {
            Carrera nuevo = carreraService.save(carrera);
            return ResponseEntity.created(new URI("/carreras/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(path = "/editar")
    public ResponseEntity<Carrera> edit(@RequestBody Carrera carrera){

        Carrera carreraActualizado = carreraService.updateCarrera(carrera.getId(),carrera);

        if (carreraActualizado != null) {
            return ResponseEntity.ok(carreraActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{dato}")
    public ResponseEntity<Carrera> obtenerCarrera(@PathVariable("dato") int dato) {

        Carrera carrera = carreraService.findById(dato);
        if (carrera == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carrera);
    }

    @GetMapping(path = "/listarNombre")
    public ResponseEntity<List<Carrera>> listarNombres() {

        List<Carrera> lista = carreraService.where("nombre","roberto");

        return ResponseEntity.ok(carreraService.orderBy(lista,"id",false));
    }

    @GetMapping(path = "/delete/{dato}")
    public ResponseEntity<List<Carrera>> delete(@PathVariable("dato") int dato) {
        carreraService.deleteById((long)dato);
        return ResponseEntity.ok(carreraService.findAll());
    }
}
