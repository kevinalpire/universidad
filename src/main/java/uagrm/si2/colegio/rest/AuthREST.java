package uagrm.si2.colegio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uagrm.si2.colegio.model.Rol;
import uagrm.si2.colegio.model.RoleEnum;
import uagrm.si2.colegio.model.UserEntity;
import uagrm.si2.colegio.repository.UserRepository;
import uagrm.si2.colegio.request.UserRequest;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import uagrm.si2.colegio.service.UserEntityService;

@RestController
@RequestMapping("/auth")
public class AuthREST {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping ("/hola")
    public String hello(){
        return "hola mundo";
    }

    @GetMapping ("/holaSeguro")
    @PreAuthorize("hasRole('administrador')")
    public String hellonoseguro(){
        return "hola mundo Seguro";
    }


    @Autowired
    private UserEntityService userService;
    @PostMapping("/store2")
    public ResponseEntity<String> createUser(@RequestBody UserEntity request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PostMapping("/store")
    public ResponseEntity <?> Store ( @RequestBody UserRequest userRequest){




        Set<Rol>roles = userRequest.getRoles().stream()
                .map(role-> Rol.builder()
                        .roleEnum(RoleEnum.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode( userRequest.getPassword()))
                .email(userRequest.getEmail())
                .roles(roles)
                .build();

        userRepository.save(userEntity);
        return  ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/delete")
    public String Delete(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "Usuario eliminado";

    }
}
