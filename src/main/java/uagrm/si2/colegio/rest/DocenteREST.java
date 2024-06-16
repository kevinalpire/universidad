package uagrm.si2.colegio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uagrm.si2.colegio.DTO.CreateDocenteDTO;
import uagrm.si2.colegio.model.Docente;
import uagrm.si2.colegio.model.UserEntity;
import uagrm.si2.colegio.service.DocenteService;
import uagrm.si2.colegio.service.UserEntityService;

import javax.print.Doc;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@PreAuthorize("hasRole('administrador')")
@RestController
@RequestMapping("/docentes")
public class DocenteREST {

    @Autowired
    private DocenteService docenteService;
    @Autowired
    private UserEntityService userService;

    @PreAuthorize("hasAnyRole('administrador')")
    @GetMapping(path = "/listar")
    public ResponseEntity<List<Docente>> read() {
        return ResponseEntity.ok(docenteService.findAll());
    }

    @PostMapping(path = "/crear")
    public ResponseEntity<Docente> create(@RequestBody CreateDocenteDTO docente){
        try {
            System.out.println(docente);
            UserEntity nuevoUser = userService.save(docente.getUser());
            docente.getDocente().setUser(nuevoUser);

            Docente nuevo = docenteService.save(docente.getDocente());
            return ResponseEntity.created(new URI("/docentes/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping(path = "/editar")
    public ResponseEntity<Docente> edit(@RequestBody Docente docente){

        Docente docenteActualizado = docenteService.updateDocente(docente.getId(),docente);

        if (docenteActualizado != null) {
            return ResponseEntity.ok(docenteActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{dato}")
    public ResponseEntity<Docente> obtenerDocente(@PathVariable("dato") int dato) {

        List<Docente> lista = docenteService.where("id",dato);
        Docente docente = lista.get(0);
        return ResponseEntity.ok(docente);
    }

    @GetMapping(path = "/listarNombre")
    public ResponseEntity<List<Docente>> listarNombres() {

        List<Docente> lista = docenteService.where("nombre","roberto");

        return ResponseEntity.ok(docenteService.orderBy(lista,"id",false));
    }

    @GetMapping(path = "/delete/{dato}")
    public ResponseEntity<List<Docente>> delete(@PathVariable("dato") int dato) {
        docenteService.deleteById((long)dato);
        return ResponseEntity.ok(docenteService.findAll());
    }
}