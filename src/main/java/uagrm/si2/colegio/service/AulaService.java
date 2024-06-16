package uagrm.si2.colegio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import uagrm.si2.colegio.repository.AulaRepository;
import uagrm.si2.colegio.model.Aula;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AulaService {
    @Autowired
    private AulaRepository aulaRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public Aula save(Aula aula) {
        return aulaRepository.save(aula);
    }

    public List<Aula> findAll() {
        return aulaRepository.findAll();
    }

    //Método Where
    public List<Aula> where(String attribute, Object value) {
        String queryString = "SELECT d FROM aulas d WHERE d." + attribute + " = :value";
        TypedQuery<Aula> query = entityManager.createQuery(queryString, Aula.class);
        query.setParameter("value", value);
        return query.getResultList();
    }

    //Método Where sobrecargado con una lista
    public List<Aula> where(List<Aula> aulas, String attribute, Object value) {
        return aulas.stream()
                .filter(aula -> {
                    try {
                        Object fieldValue = Aula.class.getDeclaredField(attribute).get(aula);
                        return fieldValue != null && fieldValue.equals(value);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public Boolean deleteById(Long id) {
        if (aulaRepository.existsById(id)) {
            aulaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void delete(Aula entity) {
        aulaRepository.delete(entity);
    }


    public List<Aula> orderBy(List<Aula> aulas, String attribute, boolean asc) {
        return aulas.stream()
                .sorted((d1, d2) -> {
                    try {
                        Field field = Aula.class.getDeclaredField(attribute);
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

    public Aula updateAula(Long id, Aula updatedAula) {
        Optional<Aula> optionalAula = aulaRepository.findById(id);
        if (optionalAula.isPresent()) {
            Aula existingAula = optionalAula.get();

            existingAula.setPiso(updatedAula.getPiso());
            existingAula.setNumero(updatedAula.getNumero());
            existingAula.setModulo(updatedAula.getModulo());
            return aulaRepository.save(existingAula);
        } else {
            throw new RuntimeException("Aula not found with id " + id);
        }
    }

}
