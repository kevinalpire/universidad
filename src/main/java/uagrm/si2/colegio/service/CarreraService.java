package uagrm.si2.colegio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uagrm.si2.colegio.model.Carrera;
import uagrm.si2.colegio.model.TipoGestion;
import uagrm.si2.colegio.repository.CarreraRepository;
import uagrm.si2.colegio.repository.CarreraRepository;


import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarreraService{

    @Autowired
    private CarreraRepository carreraRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public Carrera save(Carrera carrera) {
        return carreraRepository.save(carrera);
    }

    public List<Carrera> findAll() {
        return carreraRepository.findAll();
    }

    public Carrera findById(int id) {
        Optional<Carrera> userEntityOptional = carreraRepository.findById((long)id);
        return userEntityOptional.orElse(null); // Retorna null si no se encuentra el usuario
    }

    //Método Where
    public List<Carrera> where(String attribute, Object value) {
        String queryString = "SELECT d FROM carreras d WHERE d." + attribute + " = :value";
        TypedQuery<Carrera> query = entityManager.createQuery(queryString, Carrera.class);
        query.setParameter("value", value);
        return query.getResultList();
    }

    //Método Where sobrecargado con una lista
    public List<Carrera> where(List<Carrera> carreras, String attribute, Object value) {
        return carreras.stream()
                .filter(carrera -> {
                    try {
                        Object fieldValue = Carrera.class.getDeclaredField(attribute).get(carrera);
                        return fieldValue != null && fieldValue.equals(value);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public Boolean deleteById(Long id) {
        if (carreraRepository.existsById(id)) {
            carreraRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void delete(Carrera entity) {
        carreraRepository.delete(entity);
    }


    public List<Carrera> orderBy(List<Carrera> carreras, String attribute, boolean asc) {
        return carreras.stream()
                .sorted((d1, d2) -> {
                    try {
                        Field field = Carrera.class.getDeclaredField(attribute);
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

    public Carrera updateCarrera(Long id, Carrera updatedCarrera) {
        Optional<Carrera> optionalCarrera = carreraRepository.findById(id);
        if (optionalCarrera.isPresent()) {
            Carrera existingCarrera = optionalCarrera.get();
            existingCarrera.setNombre(updatedCarrera.getNombre());
            existingCarrera.setModalidad(updatedCarrera.getModalidad());
            return carreraRepository.save(existingCarrera);
        } else {
            throw new RuntimeException("Carrera not found with id " + id);
        }
    }



}
