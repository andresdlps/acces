package co.fuziontek.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "enfermedad")
public class Enfermedad {
    @Id
    @Column(nullable = false)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
//    @OneToMany(mappedBy="enfermedad", cascade=CascadeType.ALL)
//    private Set<Enfermedad> enfermedad;

    public Enfermedad() {
    }
    
    public Enfermedad(Long id, String nombre) {
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
