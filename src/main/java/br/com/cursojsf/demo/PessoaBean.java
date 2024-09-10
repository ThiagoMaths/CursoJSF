package br.com.cursojsf.demo;


import br.com.dao.DaoGeneric;
import br.com.entidades.Pessoa;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import br.com.repository.*;

@Named("pessoaBean")
@ViewScoped

    public class PessoaBean implements Serializable {

    private static final long serialVersionUID = 1L;


    private Pessoa pessoa = new Pessoa();
    private  DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
    private List<Pessoa> pessoas = new ArrayList<Pessoa>();
    private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();

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

    public String logar(){

        Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

        if(pessoa != null){ // achou o usuário

            //adicionar usuario na sessão usuarioLogado
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.getSessionMap().put("pessoaUser", pessoaUser);

           return "paginanavegacao.jsf";
        }

        return "index.xhtml";
    }

    public Boolean permiteAcesso(String acesso){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("pessoaUser");

        return pessoaUser.getPerfilUser().equals(acesso);
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


