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
@Table(name = "mortalidadconsolidadocai")
public class MortalidadConsolidadoCai {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Double morteMujeresMayores44;
    private Double morteMujeresMenores5;
    private Double mortAvad;
    private Double mortCostos;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ConsolidadoCai cai;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Enfermedad enfermedad;

    public MortalidadConsolidadoCai() {
    }

    public Double getMorteMujeresMayores44() {
        return morteMujeresMayores44;
    }

    public void setMorteMujeresMayores44(Double morteMujeresMayores44) {
        this.morteMujeresMayores44 = morteMujeresMayores44;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMorteMujeresMenores5() {
        return morteMujeresMenores5;
    }

    public void setMorteMujeresMenores5(Double morteMujeresMenores5) {
        this.morteMujeresMenores5 = morteMujeresMenores5;
    }

    public Double getMortAvad() {
        return mortAvad;
    }

    public void setMortAvad(Double mortAvad) {
        this.mortAvad = mortAvad;
    }

    public Double getMortCostos() {
        return mortCostos;
    }

    public void setMortCostos(Double mortCostos) {
        this.mortCostos = mortCostos;
    }

    public ConsolidadoCai getCai() {
        return cai;
    }

    public void setCai(ConsolidadoCai cai) {
        this.cai = cai;
    }

    public Enfermedad getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(Enfermedad enfermedad) {
        this.enfermedad = enfermedad;
    }
    
    
}
