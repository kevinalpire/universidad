package uagrm.si2.colegio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uagrm.si2.colegio.model.Rol;
import uagrm.si2.colegio.model.UserEntity;
import uagrm.si2.colegio.service.UserEntityService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@PreAuthorize("hasRole('administrador')")
@RestController
@RequestMapping("/user")
public class UserEntityREST {
    @Autowired
    private UserEntityService userEntityService;

    @GetMapping(path = "/listar")
    public ResponseEntity<List<UserEntity>> read() {
        return ResponseEntity.ok(userEntityService.findAll());
    }

    @GetMapping(path = "/listarRol")
    public ResponseEntity<List<Rol>> readRol() {
        return ResponseEntity.ok(userEntityService.findAllRoll());
    }

    @PostMapping(path = "/crear")
    public ResponseEntity<UserEntity> create(@RequestBody UserEntity userEntity){
        try {

            UserEntity nuevo = userEntityService.save(userEntity);
            return ResponseEntity.created(new URI("/userEntitys/crear/"+nuevo.getId())).body(nuevo);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(path = "/editar")
    public ResponseEntity<UserEntity> edit(@RequestBody UserEntity userEntity){

        UserEntity userEntityActualizado = userEntityService.updateUserEntity(userEntity.getId(),userEntity);

        if (userEntityActualizado != null) {
            return ResponseEntity.ok(userEntityActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/buscar/{dato}")
    public ResponseEntity<UserEntity> obtenerUserEntity(@PathVariable("dato") int dato) {
        UserEntity userEntity = userEntityService.findById(dato);
        if (userEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userEntity);

     }

    @GetMapping("/buscarRol/{dato}")
    public ResponseEntity<List<Rol>> obtenerRol(@PathVariable("dato") int dato) {
        List<Rol> roles = new ArrayList<>();
        Rol rol = userEntityService.findRolById(dato);
        roles.add(rol);

        if (roles == null || roles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roles);
    }

    @GetMapping(path = "/listarNombre")
    public ResponseEntity<List<UserEntity>> listarNombres() {

        List<UserEntity> lista = userEntityService.where("nombre","roberto");

        return ResponseEntity.ok(userEntityService.orderBy(lista,"id",false));
    }

    @GetMapping(path = "/delete/{dato}")
    public ResponseEntity<List<UserEntity>> delete(@PathVariable("dato") int dato) {
        userEntityService.deleteById((long)dato);
        return ResponseEntity.ok(userEntityService.findAll());
    }
}
