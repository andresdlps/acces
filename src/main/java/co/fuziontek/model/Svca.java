package co.fuziontek.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Andres
 */
@Entity
public class Svca {
    @Id
    @Column(nullable = false)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    private double promedioPM10;

    public Svca() {
    }
    
    public Svca(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPromedioPM10() {
        return promedioPM10;
    }

    public void setPromedioPM10(double promedioPM10) {
        this.promedioPM10 = promedioPM10;
    }
    
    
}
