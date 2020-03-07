package regapp.repositories.implementations;

import regapp.repositories.GenericRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public abstract class GenericRepositoryImpl<Entity, Id> implements GenericRepository<Entity, Id> {
    private EntityManager entityManager;
    private Class<Entity> clazz;

    public GenericRepositoryImpl(EntityManager entityManager, Class<Entity> clazz) {
        this.clazz = clazz;
        this.entityManager = entityManager;
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

    public void deleteById(Id id) {
        Entity entity = this.findById(id).get();

        this.entityManager.getTransaction().begin();
        this.entityManager.remove(entity);
        this.entityManager.getTransaction().commit();
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    private String getEntityName() {
        return this.clazz.getSimpleName();
    }
}