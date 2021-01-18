package com.biblioteca.servicios;

import com.biblioteca.excepciones.DBException;
import com.biblioteca.model.DB;
import com.biblioteca.model.Genero;
import java.util.Collection;

public class GenerosService {

    public Collection<Genero> getGeneros(){
        return DB.getGeneros();
    }

    public Genero getGeneroPorId(Integer id) throws DBException{
        return DB.getGeneroPorId(id);
    }
    
    public void altaGenero(Genero genero) throws DBException{
         DB.altaGenero(genero);
    }
    
    public boolean existeYaId(int id){
       return  DB.existeIdGenero(id);
    }
}
