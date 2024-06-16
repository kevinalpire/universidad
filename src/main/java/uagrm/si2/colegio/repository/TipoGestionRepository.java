package uagrm.si2.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uagrm.si2.colegio.model.TipoGestion;
@Repository
public interface TipoGestionRepository  extends JpaRepository<TipoGestion, Long> {
}
