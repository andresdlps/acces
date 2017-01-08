package co.fuziontek.model.dao;

import co.fuziontek.model.Departamento;
import co.fuziontek.model.Poblacion;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Andres
 */
public interface PoblacionDAO extends CrudRepository<Poblacion, Long>{
    public List<Poblacion> findByDepartamento(Departamento departamento);
}
