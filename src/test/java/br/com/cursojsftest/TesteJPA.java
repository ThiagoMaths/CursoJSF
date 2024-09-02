package br.com.cursojsftest;

import jakarta.persistence.Persistence;

public class TesteJPA {

    public static void main(String[] args) {
        Persistence.createEntityManagerFactory("curso-jsf");

    }
}
