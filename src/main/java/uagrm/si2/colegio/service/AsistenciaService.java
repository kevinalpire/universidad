package uagrm.si2.colegio.service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import uagrm.si2.colegio.model.Asistencia;
import uagrm.si2.colegio.model.Docente;
import uagrm.si2.colegio.repository.AsistenciaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AsistenciaService{
    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public List<Asistencia> findAll() {
        return  asistenciaRepository.findAll();
    }
    public Asistencia save(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }
}
