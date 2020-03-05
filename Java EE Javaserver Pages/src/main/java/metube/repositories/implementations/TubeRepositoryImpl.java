package metube.repositories.implementations;

import metube.domain.entities.Tube;
import metube.repositories.TubeRepository;

import javax.persistence.EntityManager;

public class TubeRepositoryImpl extends GenericRepositoryImpl<Tube, String> implements TubeRepository {
    private EntityManager entityManager;

    public TubeRepositoryImpl() {
        super(Tube.class);
    }
}
