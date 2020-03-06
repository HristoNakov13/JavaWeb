package regapp.repositories.implementations;

import regapp.domain.entities.Position;
import regapp.repositories.PositionRepository;

public class PositionRepositoryImpl extends GenericRepositoryImpl<Position, String> implements PositionRepository {
    public PositionRepositoryImpl() {
        super(Position.class);
    }
}
