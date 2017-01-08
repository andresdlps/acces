package co.fuziontek.model.dao;

import co.fuziontek.model.Departamento;
import co.fuziontek.model.Region;
import co.fuziontek.model.Svca;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Andres
 */
@Transactional
public interface DepartamentoDAO extends CrudRepository<Departamento, Long>{
    public List<Departamento> findByRegion(Region region);
    public List<Departamento> findBySvca(Svca svca);
}
