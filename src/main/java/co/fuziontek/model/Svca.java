package co.fuziontek.model;

import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
    
    @OneToMany( mappedBy="svca", fetch = FetchType.LAZY)
    Set<Departamento> departamentos = new TreeSet<>();
    
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
