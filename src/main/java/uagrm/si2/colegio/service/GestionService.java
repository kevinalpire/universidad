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
import uagrm.si2.colegio.model.Carrera;
import uagrm.si2.colegio.model.Gestion;
import uagrm.si2.colegio.repository.GestionRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GestionService {
    @Autowired
    private GestionRepository gestionRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public Gestion save(Gestion gestion) {



        return gestionRepository.save(gestion);
    }

    public List<Gestion> findAll() {
        return gestionRepository.findAll();
    }

    public Gestion findById(int id) {
        Optional<Gestion> userEntityOptional = gestionRepository.findById((long)id);
        return userEntityOptional.orElse(null); // Retorna null si no se encuentra el usuario
    }

    //Método Where
    public List<Gestion> where(String attribute, Object value) {
        String queryString = "SELECT * FROM gestions d WHERE d." + attribute + " = :value";
        TypedQuery<Gestion> query = entityManager.createQuery(queryString, Gestion.class);
        query.setParameter("value", value);
        return query.getResultList();
    }

    //Método Where sobrecargado con una lista
    public List<Gestion> where(List<Gestion> gestions, String attribute, Object value) {
        return gestions.stream()
                .filter(gestion -> {
                    try {
                        Object fieldValue = Gestion.class.getDeclaredField(attribute).get(gestion);
                        return fieldValue != null && fieldValue.equals(value);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public Boolean deleteById(Long id) {
        if (gestionRepository.existsById(id)) {
            gestionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void delete(Gestion entity) {
        gestionRepository.delete(entity);
    }


    public List<Gestion> orderBy(List<Gestion> gestions, String attribute, boolean asc) {
        return gestions.stream()
                .sorted((d1, d2) -> {
                    try {
                        Field field = Gestion.class.getDeclaredField(attribute);
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

    public Gestion updateGestion(Long id, Gestion updatedGestion) {
        Optional<Gestion> optionalGestion = gestionRepository.findById(id);
        if (optionalGestion.isPresent()) {
            Gestion existingGestion = optionalGestion.get();

            existingGestion.setNombre(updatedGestion.getNombre());
            existingGestion.setAno(updatedGestion.getAno());
            existingGestion.setTipo_gestion(updatedGestion.getTipo_gestion());
            return gestionRepository.save(existingGestion);
        } else {
            throw new RuntimeException("Gestion not found with id " + id);
        }
    }


}
