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
@Table(name = "grupo_etareo")
public class GrupoEtareo {
    
    @Id
    @Column(nullable = false)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;

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
