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
@Table(name = "consolidadocau")
public class ConsolidadoCau {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Double mortTodas;
    private Double mortTodasAVAD;
    private Double mortTodasMenores;
    private Double mortTodasMenoresAVAD;
    private Double costosMortalidad;
    
    private Double RRbc;
    private Double FAbc;
    private Double MorbBC;
    private Double MorbBCAVAD;
    private Double MorbBCCostos;
    
    private Double MorbEVRI;
    private Double MorbEVRIAVAD;
    private Double MorbEVRICostos;
    
    private Double DAR;
    private Double DARAVAD;
    private Double DARCostos;
    
    private Double Adm;
    private Double AdmAVAD;
    private Double AdmCostos;
    
    private Double visitas;
    private Double visitasAVAD;
    private Double visitasCostos;
    
    private Double SR;
    private Double SRAVAD;
    private Double SRCostos;
    
    public ConsolidadoCau() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCostosMortalidad() {
        return costosMortalidad;
    }

    public void setCostosMortalidad(Double costosMortalidad) {
        this.costosMortalidad = costosMortalidad;
    }

    public Double getRRbc() {
        return RRbc;
    }

    public void setRRbc(Double RRbc) {
        this.RRbc = RRbc;
    }

    public Double getFAbc() {
        return FAbc;
    }

    public void setFAbc(Double FAbc) {
        this.FAbc = FAbc;
    }

    public Double getMorbBC() {
        return MorbBC;
    }

    public void setMorbBC(Double MorbBC) {
        this.MorbBC = MorbBC;
    }

    public Double getMorbBCAVAD() {
        return MorbBCAVAD;
    }

    public void setMorbBCAVAD(Double MorbBCAVAD) {
        this.MorbBCAVAD = MorbBCAVAD;
    }

    public Double getMorbBCCostos() {
        return MorbBCCostos;
    }

    public void setMorbBCCostos(Double MorbBCCostos) {
        this.MorbBCCostos = MorbBCCostos;
    }

    public Double getMorbEVRI() {
        return MorbEVRI;
    }

    public void setMorbEVRI(Double MorbEVRI) {
        this.MorbEVRI = MorbEVRI;
    }

    public Double getMorbEVRIAVAD() {
        return MorbEVRIAVAD;
    }

    public void setMorbEVRIAVAD(Double MorbEVRIAVAD) {
        this.MorbEVRIAVAD = MorbEVRIAVAD;
    }

    public Double getMorbEVRICostos() {
        return MorbEVRICostos;
    }

    public void setMorbEVRICostos(Double MorbEVRICostos) {
        this.MorbEVRICostos = MorbEVRICostos;
    }

    public Double getDAR() {
        return DAR;
    }

    public void setDAR(Double DAR) {
        this.DAR = DAR;
    }

    public Double getDARAVAD() {
        return DARAVAD;
    }

    public void setDARAVAD(Double DARAVAD) {
        this.DARAVAD = DARAVAD;
    }

    public Double getDARCostos() {
        return DARCostos;
    }

    public void setDARCostos(Double DARCostos) {
        this.DARCostos = DARCostos;
    }

    public Double getAdm() {
        return Adm;
    }

    public void setAdm(Double Adm) {
        this.Adm = Adm;
    }

    public Double getAdmAVAD() {
        return AdmAVAD;
    }

    public void setAdmAVAD(Double AdmAVAD) {
        this.AdmAVAD = AdmAVAD;
    }

    public Double getAdmCostos() {
        return AdmCostos;
    }

    public void setAdmCostos(Double AdmCostos) {
        this.AdmCostos = AdmCostos;
    }

    public Double getVisitas() {
        return visitas;
    }

    public void setVisitas(Double visitas) {
        this.visitas = visitas;
    }

    public Double getVisitasAVAD() {
        return visitasAVAD;
    }

    public void setVisitasAVAD(Double visitasAVAD) {
        this.visitasAVAD = visitasAVAD;
    }

    public Double getVisitasCostos() {
        return visitasCostos;
    }

    public void setVisitasCostos(Double visitasCostos) {
        this.visitasCostos = visitasCostos;
    }

    public Double getMortTodas() {
        return mortTodas;
    }

    public void setMortTodas(Double mortTodas) {
        this.mortTodas = mortTodas;
    }

    public Double getMortTodasAVAD() {
        return mortTodasAVAD;
    }

    public void setMortTodasAVAD(Double mortTodasAVAD) {
        this.mortTodasAVAD = mortTodasAVAD;
    }

    public Double getMortTodasMenores() {
        return mortTodasMenores;
    }

    public void setMortTodasMenores(Double mortTodasMenores) {
        this.mortTodasMenores = mortTodasMenores;
    }

    public Double getMortTodasMenoresAVAD() {
        return mortTodasMenoresAVAD;
    }

    public void setMortTodasMenoresAVAD(Double mortTodasMenoresAVAD) {
        this.mortTodasMenoresAVAD = mortTodasMenoresAVAD;
    }

    public Double getSR() {
        return SR;
    }

    public void setSR(Double SR) {
        this.SR = SR;
    }

    public Double getSRAVAD() {
        return SRAVAD;
    }

    public void setSRAVAD(Double SRAVAD) {
        this.SRAVAD = SRAVAD;
    }

    public Double getSRCostos() {
        return SRCostos;
    }

    public void setSRCostos(Double SRCostos) {
        this.SRCostos = SRCostos;
    }
    
    
    
}
