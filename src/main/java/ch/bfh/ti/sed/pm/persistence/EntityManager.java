package ch.bfh.ti.sed.pm.persistence;

import javax.persistence.*;

public class EntityManager implements EntityManageable {
    public static String PERSISTENCE_UNIT_NAME = "healthbit";

    private static EntityManager instance = null;
    private javax.persistence.EntityManager wrappedInstance;

    private EntityManager() {
        this.wrappedInstance = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    }

    public static EntityManager getInstance() {
        if (instance == null) {
            instance = new EntityManager();
            return instance;
        }
        return instance;
    }

    @Override
    public void close() {
        wrappedInstance.close();
        wrappedInstance = null;
        instance = null;
    }

    @Override
    public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
        return wrappedInstance.createQuery(qlString, resultClass);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return wrappedInstance.find(entityClass, primaryKey);
    }

    @Override
    public void persist(Object entity) {
        wrappedInstance.persist(entity);
    }

    @Override
    public EntityTransaction getTransaction() {
        return wrappedInstance.getTransaction();
    }

    @Override
    public Query createQuery(String qlString) {
        return wrappedInstance.createQuery(qlString);
    }
}
