package uagrm.si2.colegio.DTO;


import lombok.Data;
import uagrm.si2.colegio.model.MateriaGestion;

@Data
public class HorarioDTO {
    String horario;
    MateriaGestion materiaGestion;
}
