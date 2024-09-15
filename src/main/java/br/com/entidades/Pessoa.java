package br.com.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String sobrenome;
    private int idade;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento = new Date();
    private String sexo;
    private List<String> frameworks;

    private Boolean ativo;

    private String login;
    private String senha;
    private String perfilUser;
    private String nivelExperiencia;

    private String cep;
    private String uf;
    private String logradouro;
    private String bairro;
    private String localidade;

    @ManyToOne
    private Cidades cidades;

    @Transient /*Não fica persistente ou não grava no banco*/
    private Estados estado;

    @Column(columnDefinition ="text") /*tipo text grava arquivos em base 64*/
    private String fotoIconBase64;

    private String extensao;

    @Lob /*Gravar arquivos no anco de dados*/
    @Basic(fetch = FetchType.LAZY)
    private byte[] fotoIconBase64Original;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfilUser() {
        return perfilUser;
    }

    public void setPerfilUser(String perfilUser) {
        this.perfilUser = perfilUser;
    }

    public List<String> getFrameworks() {
        return frameworks;
    }

    public void setFrameworks(List<String> frameworks) {
        this.frameworks = frameworks;
    }

    public String getNivelExperiencia() {
        return nivelExperiencia;
    }

    public void setNivelExperiencia(String nivelExperiencia) {
        this.nivelExperiencia = nivelExperiencia;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public Cidades getCidades() {
        return cidades;
    }

    public void setCidades(Cidades cidades) {
        this.cidades = cidades;
    }

    public String getFotoIconBase64() {
        return fotoIconBase64;
    }

    public void setFotoIconBase64(String fotoIconBase64) {
        this.fotoIconBase64 = fotoIconBase64;
    }

    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    public byte[] getFotoIconBase64Original() {
        return fotoIconBase64Original;
    }

    public void setFotoIconBase64Original(byte[] fotoIconBase64Original) {
        this.fotoIconBase64Original = fotoIconBase64Original;
    }
}
