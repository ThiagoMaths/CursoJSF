package br.com.cursojsf.demo;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoLancamento;
import br.com.repository.IDaoLancamentoImpl;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("lancamentoBean")
public class LancamentoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private DaoGeneric<Lancamento> daoGeneric = new DaoGeneric<Lancamento>();
    private Lancamento lancamento = new Lancamento();
    private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
    private IDaoLancamento daoLancamento = new IDaoLancamentoImpl();


    public String salvar() {

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext external = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) external.getSessionMap().get("pessoaUser");

        lancamento.setUsuario(pessoaUser);
        daoGeneric.salvar(lancamento);

        carregarLancamentos();

        return "";
    }


    public String novo() {
        lancamento = new Lancamento();
        return "";
    }

    public String excluir() {
        daoGeneric.excluirPorID(lancamento);
        lancamento = new Lancamento();
        carregarLancamentos();
        return "";
    }


    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    public DaoGeneric<Lancamento> getDaoGeneric() {
        return daoGeneric;
    }

    public void setDaoGeneric(DaoGeneric<Lancamento> daoGeneric) {
        this.daoGeneric = daoGeneric;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public IDaoLancamento getDaoLancamento() {
        return daoLancamento;
    }

    public void setDaoLancamento(IDaoLancamento daoLancamento) {
        this.daoLancamento = daoLancamento;
    }

    @PostConstruct
    private void carregarLancamentos() {

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("pessoaUser");
        lancamentos = daoLancamento.consultar(pessoaUser.getId());


    }
}
