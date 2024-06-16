package uagrm.si2.colegio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uagrm.si2.colegio.model.Carrera;
import uagrm.si2.colegio.model.Gestion;
import uagrm.si2.colegio.service.GestionService;

import java.net.URI;
import java.util.List;

@PreAuthorize("hasRole('administrador')")
@RestController
@RequestMapping("/gestion")
public class GestionREST {
     @Autowired
    private GestionService gestionService;

    @GetMapping(path = "/listar")
    public ResponseEntity<List<Gestion>> read() {
        return ResponseEntity.ok(gestionService.findAll());
    }

    @PostMapping(path = "/crear")
    public ResponseEntity<Gestion> create(@RequestBody Gestion gestion){
        try {

            Gestion nuevo = gestionService.save(gestion);
            return ResponseEntity.created(new URI("/gestions/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(path = "/editar")
    public ResponseEntity<Gestion> edit(@RequestBody Gestion gestion){

        Gestion gestionActualizado = gestionService.updateGestion(gestion.getId(),gestion);

        if (gestionActualizado != null) {
            return ResponseEntity.ok(gestionActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{dato}")
    public ResponseEntity<Gestion> obtenerGestion(@PathVariable("dato") int dato) {

        Gestion gestion = gestionService.findById(dato);
        if (gestion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(gestion);
    }

    @GetMapping(path = "/listarNombre")
    public ResponseEntity<List<Gestion>> listarNombres() {

        List<Gestion> lista = gestionService.where("nombre","roberto");

        return ResponseEntity.ok(gestionService.orderBy(lista,"id",false));
    }

    @GetMapping(path = "/delete/{dato}")
    public ResponseEntity<List<Gestion>> delete(@PathVariable("dato") int dato) {
        gestionService.deleteById((long)dato);
        return ResponseEntity.ok(gestionService.findAll());
    }
}
