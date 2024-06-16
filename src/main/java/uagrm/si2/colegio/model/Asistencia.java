package uagrm.si2.colegio.model;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table (name = "asistencias")
public class Asistencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String fecha;
    private String hora;
    private String minutos_tarde;
    private float longiud;
    private float latitud;

    @ManyToOne
    @JoinColumn(name = "id_materia")
    private MateriaGestion materia_gestion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_asistencia")
    private TipoAsistencia tipo_asistencia;


}
