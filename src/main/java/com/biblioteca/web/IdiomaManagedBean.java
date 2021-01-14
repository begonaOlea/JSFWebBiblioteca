package com.biblioteca.web;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@Named(value = "idiomaMB")
@SessionScoped
public class IdiomaManagedBean implements Serializable {

    private String idioma = "es";
    
    public IdiomaManagedBean() {
    }

    public String getIdioma() {
        return idioma;
    }
    
    //acción
    public String cambiarIdioma(String nuevo){
        this.idioma = nuevo;
        return null;
    }
   
}
