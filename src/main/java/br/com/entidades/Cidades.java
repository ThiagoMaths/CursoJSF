package br.com.entidades;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Cidades implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Estados estados;

    public Estados getEstados() {
        return estados;
    }

    public void setEstados(Estados estados) {
        this.estados = estados;
    }

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cidades)) return false;
        Cidades cidades = (Cidades) o;
        return Objects.equals(id, cidades.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}
