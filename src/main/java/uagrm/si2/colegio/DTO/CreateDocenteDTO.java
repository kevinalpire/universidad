package uagrm.si2.colegio.DTO;

import lombok.Data;
import uagrm.si2.colegio.model.Docente;
import uagrm.si2.colegio.model.UserEntity;

@Data
public class CreateDocenteDTO {
    private Docente docente;
    private UserEntity user;
}
