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
    
    private Double morteHMMenores5Y45a64;
    private Double morteMayor65;
    private Double mortAvad45a64;
    private Double mortAvadMayor65;
    
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

    public Double getMorteHMMenores5Y45a64() {
        return morteHMMenores5Y45a64;
    }

    public void setMorteHMMenores5Y45a64(Double morteHMMenores5Y45a64) {
        this.morteHMMenores5Y45a64 = morteHMMenores5Y45a64;
    }

    public Double getMorteMayor65() {
        return morteMayor65;
    }

    public void setMorteMayor65(Double morteMayor65) {
        this.morteMayor65 = morteMayor65;
    }

    public Double getMortAvad45a64() {
        return mortAvad45a64;
    }

    public void setMortAvad45a64(Double mortAvad45a64) {
        this.mortAvad45a64 = mortAvad45a64;
    }

    public Double getMortAvadMayor65() {
        return mortAvadMayor65;
    }

    public void setMortAvadMayor65(Double mortAvadMayor65) {
        this.mortAvadMayor65 = mortAvadMayor65;
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
