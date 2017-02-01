package co.fuziontek.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "mortalidad")
public class Mortalidad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Departamento departamento;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Enfermedad enfermedad;
    
    @Column(nullable = false)
    Integer total;
    
    @Column(nullable = false)
    Integer totalHombres;
    
    @Column(nullable = false)
    Integer totalMujeres;
    
    @Column(nullable = false)
    Integer totalIndeterminado;
    
    @Column(nullable = false)
    Integer hombres1menor;
    
    @Column(nullable = false)
    Integer mujeres1menor;
    
    @Column(nullable = false)
    Integer indeterminado1menor;
    
    @Column(nullable = false)
    Integer hombres1a4;
    
    @Column(nullable = false)
    Integer mujeres1a4;
    
    @Column(nullable = false)
    Integer indeterminado1a4;
    
    @Column(nullable = false)
    Integer hombres5a14;
    
    @Column(nullable = false)
    Integer mujeres5a14;
    
    @Column(nullable = false)
    Integer indeterminado5a14;
    
    @Column(nullable = false)
    Integer hombres15a44;
    
    @Column(nullable = false)
    Integer mujeres15a44;
    
    @Column(nullable = false)
    Integer indeterminado15a44;
    
    @Column(nullable = false)
    Integer hombres45a64;
    
    @Column(nullable = false)
    Integer mujeres45a64;
    
    @Column(nullable = false)
    Integer indeterminado45a64;
    
    @Column(nullable = false)
    Integer hombres65mayor;
    
    @Column(nullable = false)
    Integer mujeres65mayor;
    
    @Column(nullable = false)
    Integer indeterminado65mayor;

    public Mortalidad() {
    }
    
    public Mortalidad(Departamento departamento, Enfermedad enfermedad) {
        this.departamento = departamento;
        this.enfermedad = enfermedad;
    }

    public Mortalidad(Departamento departamento, Enfermedad enfermedad, Integer total, Integer totalHombres, Integer totalMujeres, Integer totalIndeterminado, Integer hombres1menor, Integer mujeres1menor, Integer indeterminado1menor, Integer hombres1a4, Integer mujeres1a4, Integer indeterminado1a4, Integer hombres5a14, Integer mujeres5a14, Integer indeterminado5a14, Integer hombres15a44, Integer mujeres15a44, Integer indeterminado15a44, Integer hombres45a64, Integer mujeres45a64, Integer indeterminado45a64, Integer hombres65mayor) {
        this.departamento = departamento;
        this.enfermedad = enfermedad;
        this.total = total;
        this.totalHombres = totalHombres;
        this.totalMujeres = totalMujeres;
        this.totalIndeterminado = totalIndeterminado;
        this.hombres1menor = hombres1menor;
        this.mujeres1menor = mujeres1menor;
        this.indeterminado1menor = indeterminado1menor;
        this.hombres1a4 = hombres1a4;
        this.mujeres1a4 = mujeres1a4;
        this.indeterminado1a4 = indeterminado1a4;
        this.hombres5a14 = hombres5a14;
        this.mujeres5a14 = mujeres5a14;
        this.indeterminado5a14 = indeterminado5a14;
        this.hombres15a44 = hombres15a44;
        this.mujeres15a44 = mujeres15a44;
        this.indeterminado15a44 = indeterminado15a44;
        this.hombres45a64 = hombres45a64;
        this.mujeres45a64 = mujeres45a64;
        this.indeterminado45a64 = indeterminado45a64;
        this.hombres65mayor = hombres65mayor;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalHombres() {
        return totalHombres;
    }

    public void setTotalHombres(Integer totalHombres) {
        this.totalHombres = totalHombres;
    }

    public Integer getTotalMujeres() {
        return totalMujeres;
    }

    public void setTotalMujeres(Integer totalMujeres) {
        this.totalMujeres = totalMujeres;
    }

    public Integer getTotalIndeterminado() {
        return totalIndeterminado;
    }

    public void setTotalIndeterminado(Integer totalIndeterminado) {
        this.totalIndeterminado = totalIndeterminado;
    }

    public Integer getHombres1menor() {
        return hombres1menor;
    }

    public void setHombres1menor(Integer hombres1menor) {
        this.hombres1menor = hombres1menor;
    }

    public Integer getMujeres1menor() {
        return mujeres1menor;
    }

    public void setMujeres1menor(Integer mujeres1menor) {
        this.mujeres1menor = mujeres1menor;
    }

    public Integer getIndeterminado1menor() {
        return indeterminado1menor;
    }

    public void setIndeterminado1menor(Integer indeterminado1menor) {
        this.indeterminado1menor = indeterminado1menor;
    }

    public Integer getHombres1a4() {
        return hombres1a4;
    }

    public void setHombres1a4(Integer hombres1a4) {
        this.hombres1a4 = hombres1a4;
    }

    public Integer getMujeres1a4() {
        return mujeres1a4;
    }

    public void setMujeres1a4(Integer mujeres1a4) {
        this.mujeres1a4 = mujeres1a4;
    }

    public Integer getIndeterminado1a4() {
        return indeterminado1a4;
    }

    public void setIndeterminado1a4(Integer indeterminado1a4) {
        this.indeterminado1a4 = indeterminado1a4;
    }

    public Integer getHombres5a14() {
        return hombres5a14;
    }

    public void setHombres5a14(Integer hombres5a14) {
        this.hombres5a14 = hombres5a14;
    }

    public Integer getMujeres5a14() {
        return mujeres5a14;
    }

    public void setMujeres5a14(Integer mujeres5a14) {
        this.mujeres5a14 = mujeres5a14;
    }

    public Integer getIndeterminado5a14() {
        return indeterminado5a14;
    }

    public void setIndeterminado5a14(Integer indeterminado5a14) {
        this.indeterminado5a14 = indeterminado5a14;
    }

    public Integer getHombres15a44() {
        return hombres15a44;
    }

    public void setHombres15a44(Integer hombres15a44) {
        this.hombres15a44 = hombres15a44;
    }

    public Integer getMujeres15a44() {
        return mujeres15a44;
    }

    public void setMujeres15a44(Integer mujeres15a44) {
        this.mujeres15a44 = mujeres15a44;
    }

    public Integer getIndeterminado15a44() {
        return indeterminado15a44;
    }

    public void setIndeterminado15a44(Integer indeterminado15a44) {
        this.indeterminado15a44 = indeterminado15a44;
    }

    public Integer getHombres45a64() {
        return hombres45a64;
    }

    public void setHombres45a64(Integer hombres45a64) {
        this.hombres45a64 = hombres45a64;
    }

    public Integer getMujeres45a64() {
        return mujeres45a64;
    }

    public void setMujeres45a64(Integer mujeres45a64) {
        this.mujeres45a64 = mujeres45a64;
    }

    public Integer getIndeterminado45a64() {
        return indeterminado45a64;
    }

    public void setIndeterminado45a64(Integer indeterminado45a64) {
        this.indeterminado45a64 = indeterminado45a64;
    }

    public Integer getHombres65mayor() {
        return hombres65mayor;
    }

    public void setHombres65mayor(Integer hombres65mayor) {
        this.hombres65mayor = hombres65mayor;
    }

    public Integer getMujeres65mayor() {
        return mujeres65mayor;
    }

    public void setMujeres65mayor(Integer mujeres65mayor) {
        this.mujeres65mayor = mujeres65mayor;
    }

    public Integer getIndeterminado65mayor() {
        return indeterminado65mayor;
    }

    public void setIndeterminado65mayor(Integer indeterminado65mayor) {
        this.indeterminado65mayor = indeterminado65mayor;
    }

    public Long getId() {
        return id;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public Enfermedad getEnfermedad() {
        return enfermedad;
    }
    
    
    
}
