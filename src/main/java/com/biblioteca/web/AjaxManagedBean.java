
package com.biblioteca.web;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "ajaxMB")
@ViewScoped
public class AjaxManagedBean implements Serializable{

    private String nombre;
    private String apellido1;
    
    private int contador = 1;
    
    public AjaxManagedBean() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public int getContador() {
        return contador++;
    }
 
}
