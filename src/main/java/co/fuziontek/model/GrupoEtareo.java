package co.fuziontek.model;

import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "grupo_etareo")
public class GrupoEtareo {
    
    @Id
    @Column(nullable = false)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @OneToMany( mappedBy="grupo_etareo", fetch = FetchType.LAZY)
    Set<Poblacion> poblacion = new TreeSet<>();
    

    public GrupoEtareo() {}

    public GrupoEtareo(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    
}
