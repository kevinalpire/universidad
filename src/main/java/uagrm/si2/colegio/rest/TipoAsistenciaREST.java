package uagrm.si2.colegio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uagrm.si2.colegio.model.TipoAsistencia;

import uagrm.si2.colegio.service.TipoAsistenciaService;

import java.util.List;

@RestController
@RequestMapping("/tipo_asistencia/")
public class TipoAsistenciaREST {

    @Autowired
    private TipoAsistenciaService tipoAsistenciaService;

    @GetMapping(path = "/listar")
    public ResponseEntity<List<TipoAsistencia>> read() {
        return ResponseEntity.ok(tipoAsistenciaService.findAll());
    }

    @GetMapping("/buscar/{dato}")
    public ResponseEntity<TipoAsistencia> obtenerTipoSsistencia(@PathVariable("dato") int dato) {
        TipoAsistencia tipoAsistencia = tipoAsistenciaService.findById(dato);
        if (tipoAsistencia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipoAsistencia);

    }
}
