package br.com.repository;

import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class IDaoPessoaImpl implements IDaoPessoa {
    @Override
    public Pessoa consultarUsuario(String login, String senha) {

        Pessoa pessoa = null;

        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        pessoa = (Pessoa) entityManager.createQuery(" select p from Pessoa  p where p.login = '" + login + "' and  p.senha = '" + senha + "'").getSingleResult();


        transaction.commit();
        entityManager.close();
        return pessoa;
    }
}
