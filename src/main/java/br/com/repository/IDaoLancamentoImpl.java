package br.com.repository;

import br.com.entidades.Lancamento;
import br.com.jpautil.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class IDaoLancamentoImpl implements IDaoLancamento {
    @Override
    public List<Lancamento> consultar(Long codUser) {

        List<Lancamento> lista = null;

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        lista = (List<Lancamento>) em.createQuery(" from Lancamento where usuario.id = " + codUser).getResultList();

        transaction.commit();
        em.close();
        return lista;
    }
}
