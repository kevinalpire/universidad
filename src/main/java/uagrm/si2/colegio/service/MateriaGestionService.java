package uagrm.si2.colegio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uagrm.si2.colegio.model.MateriaGestion;
import uagrm.si2.colegio.model.TipoGestion;
import uagrm.si2.colegio.repository.MateriaGestionRepository;
import uagrm.si2.colegio.repository.MateriaGestionRepository;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaGestionService{

    @Autowired
    private MateriaGestionRepository materiaGestionRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public MateriaGestion save(MateriaGestion materiaGestion) {
        return materiaGestionRepository.save(materiaGestion);
    }

    public List<MateriaGestion> findAll() {
        return materiaGestionRepository.findAll();
    }

    public MateriaGestion findById(int id) {
        Optional<MateriaGestion> userEntityOptional = materiaGestionRepository.findById((long)id);
        return userEntityOptional.orElse(null); // Retorna null si no se encuentra el usuario
    }

    //Método Where
    public List<MateriaGestion> where(String attribute, Object value) {
        String queryString = "SELECT d FROM materiaGestions d WHERE d." + attribute + " = :value";
        TypedQuery<MateriaGestion> query = entityManager.createQuery(queryString, MateriaGestion.class);
        query.setParameter("value", value);
        return query.getResultList();
    }

    //Método Where sobrecargado con una lista
    public List<MateriaGestion> where(List<MateriaGestion> materiaGestions, String attribute, Object value) {
        return materiaGestions.stream()
                .filter(materiaGestion -> {
                    try {
                        Object fieldValue = MateriaGestion.class.getDeclaredField(attribute).get(materiaGestion);
                        return fieldValue != null && fieldValue.equals(value);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public Boolean deleteById(Long id) {
        if (materiaGestionRepository.existsById(id)) {
            materiaGestionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void delete(MateriaGestion entity) {
        materiaGestionRepository.delete(entity);
    }


    public List<MateriaGestion> orderBy(List<MateriaGestion> materiaGestions, String attribute, boolean asc) {
        return materiaGestions.stream()
                .sorted((d1, d2) -> {
                    try {
                        Field field = MateriaGestion.class.getDeclaredField(attribute);
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

    public MateriaGestion updateMateriaGestion(Long id, MateriaGestion updatedMateriaGestion) {
        Optional<MateriaGestion> optionalMateriaGestion = materiaGestionRepository.findById(id);
        if (optionalMateriaGestion.isPresent()) {
            MateriaGestion existingMateriaGestion = optionalMateriaGestion.get();
            existingMateriaGestion.setMateria(updatedMateriaGestion.getMateria());
            existingMateriaGestion.setGestion(updatedMateriaGestion.getGestion());
            existingMateriaGestion.setAula(updatedMateriaGestion.getAula());
            existingMateriaGestion.setDocente(updatedMateriaGestion.getDocente());
            existingMateriaGestion.setGrupo(updatedMateriaGestion.getGrupo());
            return materiaGestionRepository.save(existingMateriaGestion);
        } else {
            throw new RuntimeException("MateriaGestion not found with id " + id);
        }
    }



}
