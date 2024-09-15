package br.com.repository;

import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;
import jakarta.faces.model.SelectItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<SelectItem> listaEstados() {

        List<SelectItem> items = new ArrayList<>();

        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = JPAUtil.getEntityManager().getTransaction();
        transaction.begin();

        List<Estados> estados = entityManager.createQuery(" from Estados ").getResultList();

        for (Estados estado : estados) {

            items.add(new SelectItem(estado, estado.getNome()));

        }
        return items;
    }
}
