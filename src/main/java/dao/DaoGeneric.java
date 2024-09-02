package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jpautil.JPAUtil;

import java.util.List;

public class DaoGeneric<OB> { //OB = objeto

    public void salvar(OB obj) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(obj);

        transaction.commit();
        entityManager.close();

    }

    public OB merge(OB obj) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        OB retorno = entityManager.merge(obj);

        transaction.commit();
        entityManager.close();

        return retorno;
    }

    public void excluir(OB obj) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.remove(obj);

        transaction.commit();
        entityManager.close();
        //Está com erro
    }

    public void excluirPorID(OB obj) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Object id = JPAUtil.getPrimaryKey(obj);
        entityManager.createQuery(" delete from " + obj.getClass().getCanonicalName() + " where id = " + id).executeUpdate();

        transaction.commit();
        entityManager.close();

    }

    public List<OB> getListEntity(Class<OB> obj) {
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        List<OB> retorno = entityManager.createQuery(" from " + obj.getName()).getResultList();

        transaction.begin();
        entityManager.close();

        return retorno;
    }
}
