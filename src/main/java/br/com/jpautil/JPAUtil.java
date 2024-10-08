package br.com.jpautil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("curso-jsf");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static Object getPrimaryKey(Object entity) {
        return emf.getPersistenceUnitUtil().getIdentifier(entity);
    }
}
