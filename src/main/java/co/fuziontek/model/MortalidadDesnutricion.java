/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(name = "mortalidadDesnutricion")
public class MortalidadDesnutricion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;
    
    String enfermedad;
    
    Double muertesMenoresHombres;
    
    Double muertesMenoresMujeres;
    
    Double muertesMenoresTotal;
    
    Double mortalidadMenoresCostos;

    public MortalidadDesnutricion() {
    }

    public MortalidadDesnutricion(String enfermedad, Double muertesMenoresHombres, Double muertesMenoresMujeres) {
        this.enfermedad = enfermedad;
        this.muertesMenoresHombres = muertesMenoresHombres;
        this.muertesMenoresMujeres = muertesMenoresMujeres;
        this.muertesMenoresTotal = muertesMenoresHombres + muertesMenoresMujeres;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public Double getMuertesMenoresHombres() {
        return muertesMenoresHombres;
    }

    public void setMuertesMenoresHombres(Double muertesMenoresHombres) {
        this.muertesMenoresHombres = muertesMenoresHombres;
    }

    public Double getMuertesMenoresMujeres() {
        return muertesMenoresMujeres;
    }

    public void setMuertesMenoresMujeres(Double muertesMenoresMujeres) {
        this.muertesMenoresMujeres = muertesMenoresMujeres;
    }

    public Double getMuertesMenoresTotal() {
        return muertesMenoresTotal;
    }

    public void setMuertesMenoresTotal(Double muertesMenoresTotal) {
        this.muertesMenoresTotal = muertesMenoresTotal;
    }

    public Double getMortalidadMenoresCostos() {
        return mortalidadMenoresCostos;
    }

    public void setMortalidadMenoresCostos(Double mortalidadMenoresCostos) {
        this.mortalidadMenoresCostos = mortalidadMenoresCostos;
    }
    
    
}
