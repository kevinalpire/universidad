package uagrm.si2.colegio.service;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uagrm.si2.colegio.model.Rol;
import uagrm.si2.colegio.model.UserEntity;
import uagrm.si2.colegio.repository.RolRepository;
import uagrm.si2.colegio.repository.UserEntityRepository;
import jakarta.persistence.EntityManager;
import uagrm.si2.colegio.repository.UserRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserEntityService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private RolRepository rolRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public UserEntity save(UserEntity userEntity) {

        UserEntity user = new UserEntity();
        user.setUsername(userEntity.getUsername());
        user.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        user.setEmail(userEntity.getEmail());
        user.setRoles(userEntity.getRoles());


        return userRepository.save(user);
    }

    public List<UserEntity> findAll() {
        return userEntityRepository.findAll();
    }

    public List<Rol> findAllRoll() {
        return rolRepository.findAll();
    }



    public List<Rol> getRol(String attribute, Object value) {
        String queryString = "SELECT d FROM rols d WHERE d." + attribute + " = :value";
        TypedQuery<Rol> query = entityManager.createQuery(queryString, Rol.class);
        query.setParameter("value", value);
        return query.getResultList();
    }

    //Método Where
    public List<UserEntity> where(String attribute, Object value) {
        String queryString = "SELECT d FROM users d WHERE d." + attribute + " = :value";
        TypedQuery<UserEntity> query = entityManager.createQuery(queryString, UserEntity.class);
        query.setParameter("value", value);
        return query.getResultList();
    }
    public UserEntity findById(int id) {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findById((long)id);
        return userEntityOptional.orElse(null); // Retorna null si no se encuentra el usuario
    }

    public Rol findRolById(int id) {
        Optional<Rol> rolOptional = rolRepository.findById((long)id);
        return rolOptional.orElse(null); // Retorna null si no se encuentra el usuario
    }


    //Método Where sobrecargado con una lista
    public List<UserEntity> where(List<UserEntity> userEntitys, String attribute, Object value) {
        return userEntitys.stream()
                .filter(userEntity -> {
                    try {
                        Object fieldValue = UserEntity.class.getDeclaredField(attribute).get(userEntity);
                        return fieldValue != null && fieldValue.equals(value);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    public Boolean deleteById(Long id) {
        if (userEntityRepository.existsById(id)) {
            userEntityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void delete(UserEntity entity) {
        userEntityRepository.delete(entity);
    }


    public List<UserEntity> orderBy(List<UserEntity> userEntitys, String attribute, boolean asc) {
        return userEntitys.stream()
                .sorted((d1, d2) -> {
                    try {
                        Field field = UserEntity.class.getDeclaredField(attribute);
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

    public UserEntity updateUserEntity(Long id, UserEntity updatedUserEntity) {
        Optional<UserEntity> optionalUserEntity = userEntityRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            UserEntity existingUserEntity = optionalUserEntity.get();
            existingUserEntity.setUsername(updatedUserEntity.getUsername());
            existingUserEntity.setEmail(updatedUserEntity.getEmail());
            existingUserEntity.setPassword(updatedUserEntity.getPassword());

            existingUserEntity.setRoles(updatedUserEntity.getRoles());

            return userEntityRepository.save(existingUserEntity);
        } else {
            throw new RuntimeException("UserEntity not found with id " + id);
        }
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    public void createUser(UserEntity request) {
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRoles(request.getRoles());
        userRepository.save(user);
    }
}
