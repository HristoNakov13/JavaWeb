package regapp.repositories.implementations;

import regapp.domain.entities.Position;
import regapp.repositories.PositionRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Optional;

public class PositionRepositoryImpl extends GenericRepositoryImpl<Position, String> implements PositionRepository {

    @Inject
    public PositionRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Position.class);
    }

    public Optional<Position> findByName(String positionName) {
        String queryString = "SELECT p " +
                        "FROM Position p " +
                        "WHERE p.name = :positionName";

        return Optional.ofNullable(super.getEntityManager()
                .createQuery(queryString, Position.class)
                .getSingleResult());
    }
}
