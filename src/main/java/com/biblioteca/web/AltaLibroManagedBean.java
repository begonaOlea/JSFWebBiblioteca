package com.biblioteca.web;

import com.biblioteca.excepciones.DBException;
import com.biblioteca.model.Libro;
import com.biblioteca.servicios.GenerosService;
import com.biblioteca.servicios.LibrosService;
import com.sun.faces.util.MessageFactory;

import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
//import javax.inject.Named;
//import javax.enterprise.context.RequestScoped;

@ManagedBean(name = "altaLibro")
@RequestScoped
public class AltaLibroManagedBean {

    //atributos
    private Libro libroNuevo;
    private Integer idGeneroSel;

    //Logger
    private Logger log = Logger.getLogger("AltaLibroManagedBean");

    public AltaLibroManagedBean() {
        this.libroNuevo = new Libro();
    }

    //getters y setters
    public Libro getLibroNuevo() {
        return libroNuevo;
    }

    public void setLibroNuevo(Libro libroNuevo) {
        this.libroNuevo = libroNuevo;
    }

    public Integer getIdGeneroSel() {
        return idGeneroSel;
    }

    public void setIdGeneroSel(Integer idGeneroSel) {
        this.idGeneroSel = idGeneroSel;
    }

    //validadores
    public void validarAutor(FacesContext fc,
            UIComponent componente,
            Object value) {
        String autor = (String) value;
        if (autor.contains("*")) {
            System.out.println("... no valido ....");
            ((UIInput) componente).setValid(false);
           //import com.sun.faces.util.MessageFactor
           FacesMessage msg =
               MessageFactory
               .getMessage("libros_alta_validacion_autor", null);
            fc.addMessage(componente.getClientId(fc), msg);          
        }
    }

    //acciones 
    public String altaLibro() {
        //libroNuevo ya tiene datos
        LibrosService libroService = new LibrosService();
        GenerosService generoService = new GenerosService();

        libroNuevo.setDisponible(true);
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            libroNuevo.setGenero(generoService.getGeneroPorId(idGeneroSel));
            libroService.altaLibro(libroNuevo);
            log.info("**** Alta libro ok");

            FacesMessage msg = 
                    MessageFactory.getMessage(
                            "libro_alta_ok",
                            libroNuevo.getTitulo());
            ctx.addMessage(null, msg);
            return "lista-libros";
        } catch (DBException ex) {
            log.severe("***No dio de alta libro. " + ex.getMessage());
            FacesMessage msg =
                    MessageFactory.getMessage("libro_alta_error", 
                             libroNuevo.getTitulo(),
                             ex.getMessage());
            ctx.addMessage(null, msg);
            return "alta-libro";
        }
    }

}
