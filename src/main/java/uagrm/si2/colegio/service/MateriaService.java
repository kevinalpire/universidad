package uagrm.si2.colegio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uagrm.si2.colegio.model.Materia;
import uagrm.si2.colegio.model.TipoGestion;
import uagrm.si2.colegio.repository.MateriaRepository;
import uagrm.si2.colegio.repository.MateriaRepository;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaService{

    @Autowired
    private MateriaRepository materiaRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public Materia save(Materia materia) {
        return materiaRepository.save(materia);
    }

    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }

    public Materia findById(int id) {
        Optional<Materia> userEntityOptional = materiaRepository.findById((long)id);
        return userEntityOptional.orElse(null); // Retorna null si no se encuentra el usuario
    }

    //Método Where
    public List<Materia> where(String attribute, Object value) {
        String queryString = "SELECT d FROM materias d WHERE d." + attribute + " = :value";
        TypedQuery<Materia> query = entityManager.createQuery(queryString, Materia.class);
        query.setParameter("value", value);
        return query.getResultList();
    }

    //Método Where sobrecargado con una lista
    public List<Materia> where(List<Materia> materias, String attribute, Object value) {
        return materias.stream()
                .filter(materia -> {
                    try {
                        Object fieldValue = Materia.class.getDeclaredField(attribute).get(materia);
                        return fieldValue != null && fieldValue.equals(value);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public Boolean deleteById(Long id) {
        if (materiaRepository.existsById(id)) {
            materiaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void delete(Materia entity) {
        materiaRepository.delete(entity);
    }


    public List<Materia> orderBy(List<Materia> materias, String attribute, boolean asc) {
        return materias.stream()
                .sorted((d1, d2) -> {
                    try {
                        Field field = Materia.class.getDeclaredField(attribute);
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

    public Materia updateMateria(Long id, Materia updatedMateria) {
        Optional<Materia> optionalMateria = materiaRepository.findById(id);
        if (optionalMateria.isPresent()) {
            Materia existingMateria = optionalMateria.get();
            existingMateria.setNombre(updatedMateria.getNombre());
            existingMateria.setCarrera(updatedMateria.getCarrera());
            existingMateria.setSigla(updatedMateria.getSigla());
            existingMateria.setSemestre(updatedMateria.getSemestre());
            return materiaRepository.save(existingMateria);
        } else {
            throw new RuntimeException("Materia not found with id " + id);
        }
    }



}
