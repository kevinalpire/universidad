package uagrm.si2.colegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uagrm.si2.colegio.model.Horario;
@Repository
public interface HorarioRepository  extends JpaRepository<Horario, Long> {
}
