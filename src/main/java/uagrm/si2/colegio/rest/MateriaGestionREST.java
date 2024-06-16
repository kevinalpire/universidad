package uagrm.si2.colegio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uagrm.si2.colegio.DTO.HorarioDTO;
import uagrm.si2.colegio.model.Horario;
import uagrm.si2.colegio.model.MateriaGestion;
import uagrm.si2.colegio.model.TipoAsistencia;
import uagrm.si2.colegio.service.HorarioService;
import uagrm.si2.colegio.service.MateriaGestionService;

import java.net.URI;
import java.util.List;

@PreAuthorize("hasRole('administrador')")
@RestController
@RequestMapping("/materiaGestion/")
public class MateriaGestionREST {

    @Autowired
    private MateriaGestionService materiaGestionService;
    @Autowired
    HorarioService horarioService;

    @GetMapping(path = "/listar")
    public ResponseEntity<List<MateriaGestion>> read() {
        return ResponseEntity.ok(materiaGestionService.findAll());
    }

    @PostMapping(path = "/crear")
    public ResponseEntity<MateriaGestion> create(@RequestBody HorarioDTO materiaGestion){
        try {
            MateriaGestion nuevo = materiaGestionService.save(materiaGestion.getMateriaGestion());

            Horario horario = new Horario() ;
            horario.setMateriaGestion(nuevo);
            horario.setDia(0);
            horario.setHora_inicio(materiaGestion.getHorario());

            horarioService.save(horario);



            return ResponseEntity.created(new URI("/materiaGestions/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(path = "/editar")
    public ResponseEntity<MateriaGestion> edit(@RequestBody MateriaGestion materiaGestion){

        MateriaGestion materiaGestionActualizado = materiaGestionService.updateMateriaGestion(materiaGestion.getId(),materiaGestion);

        if (materiaGestionActualizado != null) {
            return ResponseEntity.ok(materiaGestionActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{dato}")
    public ResponseEntity<MateriaGestion> obtenerMateriaGestion(@PathVariable("dato") int dato) {

        MateriaGestion materiaGestion = materiaGestionService.findById(dato);
        if (materiaGestion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materiaGestion);
    }

    @GetMapping(path = "/listarNombre")
    public ResponseEntity<List<MateriaGestion>> listarNombres() {

        List<MateriaGestion> lista = materiaGestionService.where("nombre","roberto");

        return ResponseEntity.ok(materiaGestionService.orderBy(lista,"id",false));
    }

    @GetMapping(path = "/delete/{dato}")
    public ResponseEntity<List<MateriaGestion>> delete(@PathVariable("dato") int dato) {
        materiaGestionService.deleteById((long)dato);
        return ResponseEntity.ok(materiaGestionService.findAll());
    }
}
