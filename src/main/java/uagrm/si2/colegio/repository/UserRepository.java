package uagrm.si2.colegio.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uagrm.si2.colegio.model.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    @Query ("select u from UserEntity u where u.username = ?1")
    Optional<UserEntity> getName(String username);
}