package uagrm.si2.colegio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uagrm.si2.colegio.model.TipoAsistencia;
import uagrm.si2.colegio.model.TipoAsistencia;
import uagrm.si2.colegio.model.UserEntity;
import uagrm.si2.colegio.repository.TipoAsistenciaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TipoAsistenciaService{
    @Autowired
    private TipoAsistenciaRepository tipoAsistenciaRepository;
    public List<TipoAsistencia> findAll() {
        return tipoAsistenciaRepository.findAll();
    }

    public TipoAsistencia findById(int id) {
        Optional<TipoAsistencia> userEntityOptional = tipoAsistenciaRepository.findById((long)id);
        return userEntityOptional.orElse(null); // Retorna null si no se encuentra el usuario
    }

}
