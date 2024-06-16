package uagrm.si2.colegio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uagrm.si2.colegio.model.Horario;
import uagrm.si2.colegio.model.Materia;
import uagrm.si2.colegio.repository.HorarioRepository;

import java.util.List;

@Service
public class HorarioService {
    @Autowired
    private HorarioRepository horarioRepository;


    public List<Horario> findAll() {
        return horarioRepository.findAll();
    }

    public Horario save(Horario horario) {
        return horarioRepository.save(horario);
    }
}
