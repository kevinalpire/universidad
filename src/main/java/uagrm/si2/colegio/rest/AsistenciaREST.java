package uagrm.si2.colegio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uagrm.si2.colegio.model.*;
import uagrm.si2.colegio.service.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/asistencia/")
public class AsistenciaREST {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    MateriaGestionService materiaService ;

    @Autowired
    HorarioService horarioService;

    @Autowired
    TipoAsistenciaService tipoAsistenciaService;
    @GetMapping(path = "/listar")
    private ResponseEntity<List<Asistencia>> getAllAsistencia(){
        return ResponseEntity.ok(asistenciaService.findAll());
    }

    @PostMapping(path = "/marcar")
    public ResponseEntity<String> marcarAsistencia(@RequestParam("materia") int id_materia,
                                              @RequestParam("latitud") float lat,
                                              @RequestParam("longitud") float lon) {

        System.out.println(id_materia);
        List<MateriaGestion> materias = materiaService.findAll();
        MateriaGestion  materia = new MateriaGestion();
        for (int i = 0; i <  materias.size(); i++) {
            if( materias .get(i).getId() == id_materia){
                materia = materias.get(i);
            }
        }

        List<Horario> horas = horarioService.findAll();
        Horario  horaActual  = new Horario();
        for (int i = 0; i < horas.size(); i++) {
            if( horas.get(i).getMateriaGestion().getId() == id_materia){
                horaActual =horas.get(i);
            }
        }
        Docente atual = materia.getDocente();

        if(lat < atual.getLatitud()+0.00002 && lat > atual.getLatitud() -0.00002 ||
            lon < atual.getLongiud() +0.00002 && lon > atual.getLongiud() -0.00002 ){

            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();

            Asistencia asistencia = new Asistencia();
            asistencia.setFecha(currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            asistencia.setHora(currentTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
            asistencia.setMinutos_tarde("0");
            asistencia.setLatitud(lat);
            asistencia.setLongiud(lon);
            asistencia.setMateria_gestion(materia);
            asistencia.setTipo_asistencia(tipoAsistenciaService.findById(0));

            asistenciaService.save(asistencia);

            return new ResponseEntity<>("Asistencia enviada correctamente", HttpStatus.OK);

        }

        return new ResponseEntity<>("localización desconocida", HttpStatus.BAD_REQUEST);
    }

        @GetMapping(path = "/listarDocente/{dato}")
    private ResponseEntity<List<Asistencia>> getAsistenciabyDocente(@PathVariable("dato") int dato){
        List<Asistencia> listaAsistencia = asistenciaService.findAll();
        System.out.println(listaAsistencia);
        List<Asistencia> listaDocente = new ArrayList<>();
        for (int i = 0; i < listaAsistencia.size(); i++) {
            if(listaAsistencia.get(i).getMateria_gestion().getDocente().getId() == dato){
                listaDocente.add(listaAsistencia.get(i));
            }
        }

        return ResponseEntity.ok(listaDocente);
    }
}
