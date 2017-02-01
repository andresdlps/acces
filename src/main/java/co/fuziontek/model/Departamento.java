package co.fuziontek.model;

import java.util.Set;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "departamento")
public class Departamento {
    @Id
    @Column(nullable = false)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @OneToMany( mappedBy="departamento", fetch = FetchType.LAZY)
    Set<Poblacion> poblacion = new TreeSet<>();
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Region region;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Svca svca;   
    
    private double promedioPM10;

    public Departamento() {}

    public Departamento(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Svca getSvca() {
        return svca;
    }

    public void setSvca(Svca svca) {
        this.svca = svca;
    }

    public double getPromedioPM10() {
        return promedioPM10;
    }

    public void setPromedioPM10(double promedioPM10) {
        this.promedioPM10 = promedioPM10;
    }
    
    
}
