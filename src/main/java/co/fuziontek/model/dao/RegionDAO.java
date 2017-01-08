/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.fuziontek.model.dao;

import co.fuziontek.model.Region;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Andres
 */
public interface RegionDAO extends CrudRepository<Region, Long>{
    
}
