package uagrm.si2.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uagrm.si2.colegio.model.Modalidad;
@Repository
public interface ModalidadRepository  extends JpaRepository<Modalidad, Long> {
}
