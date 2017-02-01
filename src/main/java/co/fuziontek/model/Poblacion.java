package co.fuziontek.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Andres
 */
@Entity
public class Poblacion {
    
    @Id
    private Long id;
    
    @Column(nullable = false)
    Integer total;
    
    @Column(nullable = false)
    Integer hombres;
    
    @Column(nullable = false)
    Integer mujeres;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private GrupoEtareo grupo_etareo;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Departamento departamento;

    public Poblacion() {}

    public Poblacion(Long Id, Integer total, Integer hombres, Integer mujeres, GrupoEtareo grupo_etareo, Departamento departamento) {
        this.id = Id;
        this.total = total;
        this.hombres = hombres;
        this.mujeres = mujeres;
        this.grupo_etareo = grupo_etareo;
        this.departamento = departamento;
    }

    public Long getId() {
        return id;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getHombres() {
        return hombres;
    }

    public Integer getMujeres() {
        return mujeres;
    }

    public GrupoEtareo getGrupo_etareo() {
        return grupo_etareo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }
    
    
}
