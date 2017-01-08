package co.fuziontek.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "consolidadoash")
public class ConsolidadoAsh {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Double mortNum;
    
    private Double mortAVAD;
    
    private Double mortCostos;
    
    private Double morbMenor5Inc;
    
    private Double morbMenor5;
    
    private Double morbMenor5Ash;
    
    private Double morbMenor5AVADAsh;
    
    private Double morbMenor5CostosAsh;
    
    private Double morbMayor5Inc;
    
    private Double morbMayor5;
    
    private Double morbMayor5Ash;
    
    private Double morbMayor5AVADAsh;
    
    private Double morbMayor5CostosAsh;

    public ConsolidadoAsh() {
    }

    public ConsolidadoAsh(Double mortNum, Double mortAVAD, Double mortCostos, Double morbMenor5Inc, Double morbMenor5, Double morbMenor5Ash, Double morbMenor5AVADAsh, Double morbMenor5CostosAsh, Double morbMayor5Inc, Double morbMayor5, Double morbMayor5Ash, Double morbMayor5AVADAsh, Double morbMayor5CostosAsh) {
        this.mortNum = mortNum;
        this.mortAVAD = mortAVAD;
        this.mortCostos = mortCostos;
        this.morbMenor5Inc = morbMenor5Inc;
        this.morbMenor5 = morbMenor5;
        this.morbMenor5Ash = morbMenor5Ash;
        this.morbMenor5AVADAsh = morbMenor5AVADAsh;
        this.morbMenor5CostosAsh = morbMenor5CostosAsh;
        this.morbMayor5Inc = morbMayor5Inc;
        this.morbMayor5 = morbMayor5;
        this.morbMayor5Ash = morbMayor5Ash;
        this.morbMayor5AVADAsh = morbMayor5AVADAsh;
        this.morbMayor5CostosAsh = morbMayor5CostosAsh;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMortNum() {
        return mortNum;
    }

    public void setMortNum(Double mortNum) {
        this.mortNum = mortNum;
    }

    public Double getMortAVAD() {
        return mortAVAD;
    }

    public void setMortAVAD(Double mortAVAD) {
        this.mortAVAD = mortAVAD;
    }

    public Double getMortCostos() {
        return mortCostos;
    }

    public void setMortCostos(Double mortCostos) {
        this.mortCostos = mortCostos;
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

    public Double getMorbMenor5Ash() {
        return morbMenor5Ash;
    }

    public void setMorbMenor5Ash(Double morbMenor5Ash) {
        this.morbMenor5Ash = morbMenor5Ash;
    }

    public Double getMorbMenor5AVADAsh() {
        return morbMenor5AVADAsh;
    }

    public void setMorbMenor5AVADAsh(Double morbMenor5AVADAsh) {
        this.morbMenor5AVADAsh = morbMenor5AVADAsh;
    }

    public Double getMorbMenor5CostosAsh() {
        return morbMenor5CostosAsh;
    }

    public void setMorbMenor5CostosAsh(Double morbMenor5CostosAsh) {
        this.morbMenor5CostosAsh = morbMenor5CostosAsh;
    }

    public Double getMorbMayor5Inc() {
        return morbMayor5Inc;
    }

    public void setMorbMayor5Inc(Double morbMayor5Inc) {
        this.morbMayor5Inc = morbMayor5Inc;
    }

    public Double getMorbMayor5() {
        return morbMayor5;
    }

    public void setMorbMayor5(Double morbMayor5) {
        this.morbMayor5 = morbMayor5;
    }

    public Double getMorbMayor5Ash() {
        return morbMayor5Ash;
    }

    public void setMorbMayor5Ash(Double morbMayor5Ash) {
        this.morbMayor5Ash = morbMayor5Ash;
    }

    public Double getMorbMayor5AVADAsh() {
        return morbMayor5AVADAsh;
    }

    public void setMorbMayor5AVADAsh(Double morbMayor5AVADAsh) {
        this.morbMayor5AVADAsh = morbMayor5AVADAsh;
    }

    public Double getMorbMayor5CostosAsh() {
        return morbMayor5CostosAsh;
    }

    public void setMorbMayor5CostosAsh(Double morbMayor5CostosAsh) {
        this.morbMayor5CostosAsh = morbMayor5CostosAsh;
    }
    
    
    
}
