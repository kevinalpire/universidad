package uagrm.si2.colegio.model;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table (name = "gestions")

public class Gestion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nombre;
    private int ano;

    @ManyToOne
    @JoinColumn(name = "id_tipo_gestion")
    private TipoGestion tipo_gestion;

}
