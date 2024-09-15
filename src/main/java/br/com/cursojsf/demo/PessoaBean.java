package br.com.cursojsf.demo;

import br.com.dao.DaoGeneric;
import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;
import br.com.repository.IDaoPessoa;
import br.com.repository.IDaoPessoaImpl;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.html.HtmlSelectOneMenu;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@Named("pessoaBean")
@ViewScoped

public class PessoaBean implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();
    private Pessoa pessoa = new Pessoa();
    private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
    private List<Pessoa> pessoas = new ArrayList<Pessoa>();
    private List<SelectItem> estados;
    private List<SelectItem> cidades;
    private Part arquivoFoto;


    /**
     *
     */

    public PessoaBean() {

    }

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

    public List<SelectItem> getEstados() {
        estados = iDaoPessoa.listaEstados();
        return estados;
    }

    public void setEstados(List<SelectItem> estados) {
        this.estados = estados;
    }

    public List<SelectItem> getCidades() {
        return cidades;
    }

    public void setCidades(List<SelectItem> cidades) {
        this.cidades = cidades;
    }

    public Part getArquivoFoto() {
        return arquivoFoto;
    }

    public void setArquivoFoto(Part arquivoFoto) {
        this.arquivoFoto = arquivoFoto;
    }

    /**
     *
     */


    public String logar() {

        Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

        if (pessoa != null) { // achou o usuário

            //adicionar usuario na sessão usuarioLogado
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.getSessionMap().put("pessoaUser", pessoaUser);

            return "paginanavegacao.jsf";
        }

        return "index.xhtml";
    }

    public String deslogar() {

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.getSessionMap().remove("pessoaUser");

        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        request.getSession().invalidate();


        return "index.xhtml";
    }

    public Boolean permiteAcesso(String acesso) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("pessoaUser");

        return pessoaUser.getPerfilUser().equals(acesso);
    }

    public String salvar() {

        pessoa = daoGeneric.merge(pessoa);
        carregarPessoas();
        mostrarMsg("Cadastro com  sucesso!");
        return "";
    }

    public String novo() {

        pessoa = new Pessoa();
        return "";
    }

    public String Limpar() {
        //Carregar um processo antes de limpar
        pessoa = new Pessoa();
        return "";
    }

    public String excluir() {

        daoGeneric.excluirPorID(pessoa);
        pessoa = new Pessoa();
        carregarPessoas();
        mostrarMsg("Excluído com  sucesso!");

        return "";
    }

    public void editar() {
        if (pessoa.getCidades() != null) {
            Estados estado = pessoa.getCidades().getEstados();
            pessoa.setEstado(estado);

            List<Cidades> cidades = JPAUtil.getEntityManager().createQuery(" from Cidades where estados.id = " + estado.getId()).getResultList();

            List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();

            for (Cidades cidade : cidades) {
                selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
            }
            setCidades(selectItemsCidade);

        }
    }

    /**
     *
     */

    @PostConstruct
    public void carregarPessoas() {
        pessoas = daoGeneric.getListEntity(Pessoa.class);
    }

    private void mostrarMsg(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(msg);
        context.addMessage(null, message);

    }

    public void pesquisaCep(AjaxBehaviorEvent event) {

        try {
            URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep() + "/json/");
            URLConnection conn = url.openConnection();
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String cep = "";
            StringBuilder bJson = new StringBuilder();

            while ((cep = reader.readLine()) != null) {
                bJson.append(cep);
            }

            Pessoa gsonAux = new Gson().fromJson(bJson.toString(), Pessoa.class);

            pessoa.setCep(gsonAux.getCep());
            pessoa.setLogradouro(gsonAux.getLogradouro());
            pessoa.setLocalidade(gsonAux.getLocalidade());
            pessoa.setUf(gsonAux.getUf());
            pessoa.setBairro(gsonAux.getBairro());


        } catch (Exception e) {
            e.printStackTrace();
            mostrarMsg("Erro ao pesquisar cep");
        }
    }


    public void carregaCidades(AjaxBehaviorEvent event) {

        Estados estado = (Estados) ((HtmlSelectOneMenu) event.getSource()).getValue();


        if (estado != null) {
            pessoa.setEstado(estado);
            List<Cidades> cidades = JPAUtil.getEntityManager().createQuery(" from Cidades where estados.id = " + estado.getId()).getResultList();

            List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();

            for (Cidades cidade : cidades) {
                selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
            }
            setCidades(selectItemsCidade);
        }
    }

    /*Método que converte inputstream para array de bytes*/
    private byte[] getByte(InputStream is) throws IOException {

        int len;
        int size = 1024;
        byte[] buf = null;
        if (is instanceof ByteArrayInputStream) {
            size = is.available();
            buf = new byte[size];
            len = is.read(buf, 0, size);
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            buf = new byte[size];

            while ((len = is.read(buf, 0, size)) != -1) {
                baos.write(buf, 0, len);
            }

            baos.toByteArray();
        }
        return buf;
    }

}





