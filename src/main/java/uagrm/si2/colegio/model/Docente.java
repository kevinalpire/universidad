package uagrm.si2.colegio.model;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Entity
@Data
@Table (name = "docentes")
public class Docente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;
    private int codigo;
    private int ci;
    private String profesion;
    private String genero;
    private float longiud;
    private float latitud;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;


}
