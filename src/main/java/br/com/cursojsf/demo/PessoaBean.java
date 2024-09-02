package br.com.cursojsf.demo;


import dao.DaoGeneric;
import entidades.Pessoa;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Named("pessoaBean")
@ViewScoped

    public class PessoaBean implements Serializable {

    private static final long serialVersionUID = 1L;


    private Pessoa pessoa = new Pessoa();
    private  DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
    private List<Pessoa> pessoas = new ArrayList<Pessoa>();

    public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public DaoGeneric<Pessoa> getDaoGeneric() {
        return daoGeneric;
    }
    public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
        this.daoGeneric = daoGeneric;
    }
    public List<Pessoa> getPessoas() {
        return pessoas;
    }
    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }


    public PessoaBean(){

    }

    public String salvar(){

        pessoa = daoGeneric.merge(pessoa);
        carregarPessoas();
        return "";
    }

    public String novo(){

        pessoa = new Pessoa();
        return "";
    }


    public String excluir(){

       daoGeneric.excluirPorID(pessoa);
       pessoa = new Pessoa();
       carregarPessoas();
       return "";
    }

    @PostConstruct
    public void carregarPessoas(){
        pessoas = daoGeneric.getListEntity(Pessoa.class);
    }


}
