package uagrm.si2.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uagrm.si2.colegio.model.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
}
