package com.biblioteca.web;

import com.biblioteca.excepciones.DBException;
import com.biblioteca.model.Genero;
import com.biblioteca.servicios.GenerosService;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named(value = "altaGeneroMB")
@ViewScoped
public class AltaGeneroManagedBean implements Serializable {

    private Genero generoNuevo;
    private GenerosService genService = new GenerosService();
    
    private String mensajeExisteId = "";
    
    @Inject
    private GenerosManagedBean generosMB;
  
    public AltaGeneroManagedBean() {
        this.generoNuevo = new Genero();
    }

    public Genero getGeneroNuevo() {
        return generoNuevo;
    }

    public void setGeneroNuevo(Genero generoNuevo) {
        this.generoNuevo = generoNuevo;
    }

    public String getMensajeExisteId() {
        //bd si existe id 
        
         System.out.println("....¿existe  " + generoNuevo.getId() + "?");
         if( generoNuevo.getId() != null 
                 && genService.existeYaId(generoNuevo.getId())){
             mensajeExisteId = "El id ya existe";
         }else{
             mensajeExisteId = "";
         }
        return mensajeExisteId;
    }
    
 
    
    public String altaGenero(){
        
        try {
            //grabar en bd
            genService.altaGenero(generoNuevo);
            //refrescar
            generosMB.inicializar();            
            return "lista-generos";
        } catch (DBException ex) {
             System.out.println("... no grabo genro " + ex.getMessage());
             return "alta-genero";
        }
    }
    
}
