package br.com.converter;


import br.com.entidades.Cidades;
import br.com.jpautil.JPAUtil;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.io.Serial;
import java.io.Serializable;

@FacesConverter(forClass = Cidades.class, value = "cidadeConverter")
public class CidadeConverter implements Converter, Serializable {

    @Serial
    private static final long serialVersionUID = -628943317877875062L;

    @Override /*Retorna objeto inteiro*/
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String codigoCidade) {

        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Cidades cidades = (Cidades) entityManager.find(Cidades.class, Long.parseLong(codigoCidade));


        return cidades;
    }

    @Override/*Retorna apenas o código String*/
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object cidade) {

        if (cidade == null) {
            return null;
        }

        if (cidade instanceof Cidades) {
            return ((Cidades) cidade).getId().toString();
        } else {
            return cidade.toString();

        }
    }
}

