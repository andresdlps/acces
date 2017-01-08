package co.fuziontek.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "region")
public class Region {
    @Id
    @Column(nullable = false)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;

    public Region() {
    }

    public Region(Long id, String nombre) {
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
