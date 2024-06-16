package uagrm.si2.colegio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uagrm.si2.colegio.model.Modalidad;
import uagrm.si2.colegio.repository.ModalidadRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ModalidadService {

    @Autowired
    private ModalidadRepository modalidadRepository;

    public List<Modalidad> findAll() {
        return modalidadRepository.findAll();
    }

    public Modalidad findById(int id) {
        Optional<Modalidad> userEntityOptional = modalidadRepository.findById((long)id);
        return userEntityOptional.orElse(null); // Retorna null si no se encuentra el usuario
    }
}
