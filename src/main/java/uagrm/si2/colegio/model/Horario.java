package uagrm.si2.colegio.model;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table (name = "horarios")

public class Horario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private int dia;
    private String hora_inicio;


    @ManyToOne
    @JoinColumn(name = "id_materia_gestion")
    private MateriaGestion materiaGestion;

}
