package co.fuziontek.model.dao;

import co.fuziontek.model.Departamento;
import co.fuziontek.model.Mortalidad;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Andres
 */
public interface MortalidadDAO extends CrudRepository<Mortalidad, Long>{
    List<Mortalidad> findByDepartamento(Departamento departamento);
}
