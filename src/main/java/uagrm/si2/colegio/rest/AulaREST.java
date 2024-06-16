package uagrm.si2.colegio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uagrm.si2.colegio.model.Aula;
import uagrm.si2.colegio.service.AulaService;

import java.net.URI;
import java.util.List;
@PreAuthorize("hasRole('administrador')")
@RestController
@RequestMapping("/aulas")
public class AulaREST {

    @Autowired
    private AulaService aulaService;

    @GetMapping(path = "/listar")
    public ResponseEntity<List<Aula>> read() {
        return ResponseEntity.ok(aulaService.findAll());
    }

    @PostMapping(path = "/crear")
    public ResponseEntity<Aula> create(@RequestBody Aula aula){
        try {
            Aula nuevo = aulaService.save(aula);
            return ResponseEntity.created(new URI("/aulas/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(path = "/editar")
    public ResponseEntity<Aula> edit(@RequestBody Aula aula){

        Aula aulaActualizado = aulaService.updateAula(aula.getId(),aula);

        if (aulaActualizado != null) {
            return ResponseEntity.ok(aulaActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{dato}")
    public ResponseEntity<Aula> obtenerAula(@PathVariable("dato") int dato) {

        List<Aula> lista = aulaService.findAll();
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getId()==dato){
                return ResponseEntity.ok(lista.get(i));
            }
        }
        Aula aula = lista.get(0);
        return ResponseEntity.ok(aula);
    }

    @GetMapping(path = "/listarNombre")
    public ResponseEntity<List<Aula>> listarNombres() {

        List<Aula> lista = aulaService.where("nombre","roberto");

        return ResponseEntity.ok(aulaService.orderBy(lista,"id",false));
    }

    @GetMapping(path = "/delete/{dato}")
    public ResponseEntity<List<Aula>> delete(@PathVariable("dato") int dato) {
        aulaService.deleteById((long)dato);
        return ResponseEntity.ok(aulaService.findAll());
    }
}
