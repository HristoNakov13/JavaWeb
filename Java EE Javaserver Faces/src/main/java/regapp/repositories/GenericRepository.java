package regapp.repositories;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<Entity, Id> {
    Entity save(Entity entity);

    List<Entity> findAll();

    Optional<Entity> findById(Id id);

    void deleteById(Id id);
}