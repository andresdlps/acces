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
@Table(name = "morbilidadDesnutricion")
public class MorbilidadDesnutricion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;
    
    String enfermedad;
    
    Double morbilidadMenores5Hombres;
    
    Double morbilidadMenores5Mujeres;
    
    Double morbilidadMenores5Total;
    
    Double morbilidadMenores5AVAD;
    
    Double morbilidadMenores5Costos;

    public MorbilidadDesnutricion() {
    }

    public MorbilidadDesnutricion(String enfermedad, Double morbilidadMenores5Hombres, Double morbilidadMenores5Mujeres) {
        this.enfermedad = enfermedad;
        this.morbilidadMenores5Hombres = morbilidadMenores5Hombres;
        this.morbilidadMenores5Mujeres = morbilidadMenores5Mujeres;
        this.morbilidadMenores5Total = morbilidadMenores5Hombres + morbilidadMenores5Mujeres;
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

    public Double getMorbilidadMenores5Hombres() {
        return morbilidadMenores5Hombres;
    }

    public void setMorbilidadMenores5Hombres(Double morbilidadMenores5Hombres) {
        this.morbilidadMenores5Hombres = morbilidadMenores5Hombres;
    }

    public Double getMorbilidadMenores5Mujeres() {
        return morbilidadMenores5Mujeres;
    }

    public void setMorbilidadMenores5Mujeres(Double morbilidadMenores5Mujeres) {
        this.morbilidadMenores5Mujeres = morbilidadMenores5Mujeres;
    }

    public Double getMorbilidadMenores5AVAD() {
        return morbilidadMenores5AVAD;
    }

    public void setMorbilidadMenores5AVAD(Double morbilidadMenores5AVAD) {
        this.morbilidadMenores5AVAD = morbilidadMenores5AVAD;
    }

    public Double getMorbilidadMenores5Costos() {
        return morbilidadMenores5Costos;
    }

    public void setMorbilidadMenores5Costos(Double morbilidadMenores5Costos) {
        this.morbilidadMenores5Costos = morbilidadMenores5Costos;
    }

    public Double getMorbilidadMenores5Total() {
        return morbilidadMenores5Total;
    }

    public void setMorbilidadMenores5Total(Double morbilidadMenores5Total) {
        this.morbilidadMenores5Total = morbilidadMenores5Total;
    }
    
    
}
