package co.fuziontek.model;

import javax.persistence.CascadeType;
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
public class Estacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nombre;
    
    private Double sumaPm10;
    
    private Long count;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Departamento departamento;  

    public Estacion() {
    }

    public Estacion(String nombre, Departamento departamento) {
        this.nombre = nombre;
        this.departamento = departamento;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getSumaPm10() {
        return sumaPm10;
    }

    public void setSumaPm10(Double sumaPm10) {
        this.sumaPm10 = sumaPm10;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    
    
    
}
