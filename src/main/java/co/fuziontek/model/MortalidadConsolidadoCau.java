package co.fuziontek.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "mortalidadconsolidadocau")
public class MortalidadConsolidadoCau {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Double mortMayores45;
    private Double mortAvadMayor45;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ConsolidadoCau cau;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Enfermedad enfermedad;

    public MortalidadConsolidadoCau() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMortMayores45() {
        return mortMayores45;
    }

    public void setMortMayores45(Double mortMayores45) {
        this.mortMayores45 = mortMayores45;
    }

    public Double getMortAvadMayor45() {
        return mortAvadMayor45;
    }

    public void setMortAvadMayor45(Double mortAvadMayor45) {
        this.mortAvadMayor45 = mortAvadMayor45;
    }

    public ConsolidadoCau getCau() {
        return cau;
    }

    public void setCau(ConsolidadoCau cau) {
        this.cau = cau;
    }

    public Enfermedad getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(Enfermedad enfermedad) {
        this.enfermedad = enfermedad;
    }
    
}
