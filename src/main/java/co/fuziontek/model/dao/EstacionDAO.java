/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.fuziontek.model.dao;

import co.fuziontek.model.Departamento;
import co.fuziontek.model.Estacion;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Andres
 */
public interface EstacionDAO extends CrudRepository<Estacion, Long> {
    public Estacion findByNombre(String nombre);
    public List<Estacion> findByDepartamento(Departamento departamento);
}
