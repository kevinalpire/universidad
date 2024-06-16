package uagrm.si2.colegio.model;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table (name = "materias")

public class Materia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nombre;
    private String sigla;
    private int semestre;


    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;


}
