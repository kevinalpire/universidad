package uagrm.si2.colegio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uagrm.si2.colegio.model.TipoGestion;
import uagrm.si2.colegio.service.TipoGestionService;

import java.util.List;

@PreAuthorize("hasRole('administrador')")
@RestController
@RequestMapping("/tipo_gestion/")
public class TipoGestionREST {
    @Autowired
    private TipoGestionService tipoGestionService;

    @GetMapping(path = "/listar")
    public ResponseEntity<List<TipoGestion>> read() {
        return ResponseEntity.ok(tipoGestionService.findAll());
    }

    @GetMapping("/buscar/{dato}")
    public ResponseEntity<TipoGestion> obtenerTipoGestion(@PathVariable("dato") int dato) {
        TipoGestion tipoGestion = tipoGestionService.findById(dato);
        if (tipoGestion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipoGestion);

    }
}
