package co.fuziontek.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "consolidadocai")
public class ConsolidadoCai {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Double morbMenor5Inc;
    private Double morbMenor5;
    private Double morbMenor5Cai;
    private Double morbMenor5AVADCai;
    private Double morbMenor5CostosCai;
    
    private Double morbMayor15Inc;
    private Double morbMayor15;
    private Double morbMayor15Cai;
    private Double morbMayor15AVADCai;
    private Double morbMayor15CostosCai;
    
    private Double morbMayor15EPOC;
    private Double morbMayor15CaiEPOC;
    private Double morbMayor15AVADCaiEPOC;
    private Double morbMayor15CostosCaiEPOC;

    public ConsolidadoCai() {
    }

    public Double getMorbMenor5Inc() {
        return morbMenor5Inc;
    }

    public void setMorbMenor5Inc(Double morbMenor5Inc) {
        this.morbMenor5Inc = morbMenor5Inc;
    }

    public Double getMorbMenor5() {
        return morbMenor5;
    }

    public void setMorbMenor5(Double morbMenor5) {
        this.morbMenor5 = morbMenor5;
    }

    public Double getMorbMenor5Cai() {
        return morbMenor5Cai;
    }

    public void setMorbMenor5Cai(Double morbMenor5Cai) {
        this.morbMenor5Cai = morbMenor5Cai;
    }

    public Double getMorbMenor5AVADCai() {
        return morbMenor5AVADCai;
    }

    public void setMorbMenor5AVADCai(Double morbMenor5AVADCai) {
        this.morbMenor5AVADCai = morbMenor5AVADCai;
    }

    public Double getMorbMenor5CostosCai() {
        return morbMenor5CostosCai;
    }

    public void setMorbMenor5CostosCai(Double morbMenor5CostosCai) {
        this.morbMenor5CostosCai = morbMenor5CostosCai;
    }

    public Double getMorbMayor15Inc() {
        return morbMayor15Inc;
    }

    public void setMorbMayor15Inc(Double morbMayor15Inc) {
        this.morbMayor15Inc = morbMayor15Inc;
    }

    public Double getMorbMayor15() {
        return morbMayor15;
    }

    public void setMorbMayor15(Double morbMayor15) {
        this.morbMayor15 = morbMayor15;
    }

    public Double getMorbMayor15Cai() {
        return morbMayor15Cai;
    }

    public void setMorbMayor15Cai(Double morbMayor15Cai) {
        this.morbMayor15Cai = morbMayor15Cai;
    }

    public Double getMorbMayor15AVADCai() {
        return morbMayor15AVADCai;
    }

    public void setMorbMayor15AVADCai(Double morbMayor15AVADCai) {
        this.morbMayor15AVADCai = morbMayor15AVADCai;
    }

    public Double getMorbMayor15CostosCai() {
        return morbMayor15CostosCai;
    }

    public void setMorbMayor15CostosCai(Double morbMayor15CostosCai) {
        this.morbMayor15CostosCai = morbMayor15CostosCai;
    }

    public Double getMorbMayor15EPOC() {
        return morbMayor15EPOC;
    }

    public void setMorbMayor15EPOC(Double morbMayor15EPOC) {
        this.morbMayor15EPOC = morbMayor15EPOC;
    }

    public Double getMorbMayor15CaiEPOC() {
        return morbMayor15CaiEPOC;
    }

    public void setMorbMayor15CaiEPOC(Double morbMayor15CaiEPOC) {
        this.morbMayor15CaiEPOC = morbMayor15CaiEPOC;
    }

    public Double getMorbMayor15AVADCaiEPOC() {
        return morbMayor15AVADCaiEPOC;
    }

    public void setMorbMayor15AVADCaiEPOC(Double morbMayor15AVADCaiEPOC) {
        this.morbMayor15AVADCaiEPOC = morbMayor15AVADCaiEPOC;
    }

    public Double getMorbMayor15CostosCaiEPOC() {
        return morbMayor15CostosCaiEPOC;
    }

    public void setMorbMayor15CostosCaiEPOC(Double morbMayor15CostosCaiEPOC) {
        this.morbMayor15CostosCaiEPOC = morbMayor15CostosCaiEPOC;
    }
    
}
