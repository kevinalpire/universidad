package uagrm.si2.colegio.service;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uagrm.si2.colegio.model.Docente;
import uagrm.si2.colegio.repository.DocenteRepository;
import jakarta.persistence.EntityManager;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public Docente save(Docente docente) {
        return docenteRepository.save(docente);
    }

    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    //Método Where
    public List<Docente> where(String attribute, Object value) {
        String queryString = "SELECT d FROM docentes d WHERE d." + attribute + " = :value";
        TypedQuery<Docente> query = entityManager.createQuery(queryString, Docente.class);
        query.setParameter("value", value);
        return query.getResultList();
    }

    //Método Where sobrecargado con una lista
    public List<Docente> where(List<Docente> docentes, String attribute, Object value) {
        return docentes.stream()
                .filter(docente -> {
                    try {
                        Object fieldValue = Docente.class.getDeclaredField(attribute).get(docente);
                        return fieldValue != null && fieldValue.equals(value);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public Boolean deleteById(Long id) {
        if (docenteRepository.existsById(id)) {
            docenteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void delete(Docente entity) {
        docenteRepository.delete(entity);
    }


    public List<Docente> orderBy(List<Docente> docentes, String attribute, boolean asc) {
        return docentes.stream()
                .sorted((d1, d2) -> {
                    try {
                        Field field = Docente.class.getDeclaredField(attribute);
                        field.setAccessible(true);
                        Comparable value1 = (Comparable) field.get(d1);
                        Comparable value2 = (Comparable) field.get(d2);
                        return asc ? value1.compareTo(value2) : value2.compareTo(value1);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                        return 0;
                    }
                })
                .collect(Collectors.toList());
    }

    public Docente updateDocente(Long id, Docente updatedDocente) {
        Optional<Docente> optionalDocente = docenteRepository.findById(id);
        if (optionalDocente.isPresent()) {
            Docente existingDocente = optionalDocente.get();
            existingDocente.setNombre(updatedDocente.getNombre());
            existingDocente.setCodigo(updatedDocente.getCodigo());
            existingDocente.setCi(updatedDocente.getCi());
            existingDocente.setProfesion(updatedDocente.getProfesion());
            existingDocente.setGenero(updatedDocente.getGenero());
            return docenteRepository.save(existingDocente);
        } else {
            throw new RuntimeException("Docente not found with id " + id);
        }
    }

}
