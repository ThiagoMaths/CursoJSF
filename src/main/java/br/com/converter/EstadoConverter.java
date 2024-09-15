package br.com.converter;


import br.com.entidades.Estados;
import br.com.jpautil.JPAUtil;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.io.Serial;
import java.io.Serializable;

@FacesConverter(forClass = Estados.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable {
    @Serial
    private static final long serialVersionUID = -628943317877875062L;

    @Override /*Retorna objeto inteiro*/
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String codigoEstado) {

        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Estados estado = entityManager.find(Estados.class, Long.parseLong(codigoEstado));


        return estado;
    }

    @Override/*Retorna apenas o código String*/
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object estado) {

        if (estado == null) {
            return null;
        }

        if (estado instanceof Estados) {
            return ((Estados) estado).getId().toString();
        } else {
            return estado.toString();

        }

    }
}

