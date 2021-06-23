package ch.bfh.ti.sed.pm.persistence;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public interface EntityManageable {
    void close();
    <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass);
    <T> T find(Class<T> entityClass, Object primaryKey);
    void persist(Object entity);
    EntityTransaction getTransaction();
    Query createQuery(String qlString);
}
