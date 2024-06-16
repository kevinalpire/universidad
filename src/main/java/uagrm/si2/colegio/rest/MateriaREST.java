package uagrm.si2.colegio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uagrm.si2.colegio.model.Materia;
import uagrm.si2.colegio.model.TipoAsistencia;
import uagrm.si2.colegio.service.MateriaService;

import java.net.URI;
import java.util.List;

@PreAuthorize("hasRole('administrador')")
@RestController
@RequestMapping("/materia/")
public class MateriaREST {

    @Autowired
    private MateriaService materiaService;

    @GetMapping(path = "/listar")
    public ResponseEntity<List<Materia>> read() {
        return ResponseEntity.ok(materiaService.findAll());
    }

    @PostMapping(path = "/crear")
    public ResponseEntity<Materia> create(@RequestBody Materia materia){
        try {
            Materia nuevo = materiaService.save(materia);
            return ResponseEntity.created(new URI("/materias/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(path = "/editar")
    public ResponseEntity<Materia> edit(@RequestBody Materia materia){

        Materia materiaActualizado = materiaService.updateMateria(materia.getId(),materia);

        if (materiaActualizado != null) {
            return ResponseEntity.ok(materiaActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{dato}")
    public ResponseEntity<Materia> obtenerMateria(@PathVariable("dato") int dato) {

        Materia materia = materiaService.findById(dato);
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    @GetMapping(path = "/listarNombre")
    public ResponseEntity<List<Materia>> listarNombres() {

        List<Materia> lista = materiaService.where("nombre","roberto");

        return ResponseEntity.ok(materiaService.orderBy(lista,"id",false));
    }

    @GetMapping(path = "/delete/{dato}")
    public ResponseEntity<List<Materia>> delete(@PathVariable("dato") int dato) {
        materiaService.deleteById((long)dato);
        return ResponseEntity.ok(materiaService.findAll());
    }
}
