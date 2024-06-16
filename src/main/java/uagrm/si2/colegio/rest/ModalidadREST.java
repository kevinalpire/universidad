package uagrm.si2.colegio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uagrm.si2.colegio.model.Modalidad;
import uagrm.si2.colegio.service.ModalidadService;

import java.util.List;

@PreAuthorize("hasRole('administrador')")
@RestController
@RequestMapping("/modalidad/")
public class ModalidadREST {

    @Autowired
    private ModalidadService modalidadService;

    @GetMapping(path = "/listar")
    public ResponseEntity<List<Modalidad>> read() {
        return ResponseEntity.ok(modalidadService.findAll());
    }

    @GetMapping("/buscar/{dato}")
    public ResponseEntity<Modalidad> obtenerTipoSsistencia(@PathVariable("dato") int dato) {
        Modalidad modalidad = modalidadService.findById(dato);
        if (modalidad == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modalidad);

    }
}
