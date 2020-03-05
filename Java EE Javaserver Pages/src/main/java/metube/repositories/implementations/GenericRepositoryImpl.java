package metube.repositories.implementations;

import metube.repositories.GenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public abstract class GenericRepositoryImpl<Entity, Id> implements GenericRepository<Entity, Id> {
    private EntityManager entityManager;
    private Class<Entity> clazz;

    public GenericRepositoryImpl(Class<Entity> clazz) {
        this.clazz = clazz;
        this.entityManager = Persistence
                .createEntityManagerFactory("metube")
                .createEntityManager();
    }

    @Override
    public Entity save(Entity entity) {
        this.entityManager.getTransaction().begin();

        this.entityManager.persist(entity);
        this.entityManager.flush();

        this.entityManager.getTransaction().commit();

        return entity;
    }

    @Override
    public List<Entity> findAll() {
        String queryString = "SELECT e FROM " + this.getEntityName() + " e";

        return this.entityManager
                .createQuery(queryString, this.clazz)
                .getResultList();
    }

    @Override
    public Optional<Entity> findById(Id id) {
        String queryString =
                "SELECT e " +
                        "FROM " + this.getEntityName() + " e " +
                        "WHERE e.id = :id";

        return Optional.ofNullable(this.entityManager.createQuery(queryString, this.clazz)
                .setParameter("id", id)
                .getSingleResult());
    }

    private String getEntityName() {
        return this.clazz.getSimpleName();
    }
}