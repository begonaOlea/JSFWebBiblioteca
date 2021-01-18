package com.biblioteca.model;

import com.biblioteca.excepciones.DBException;
import com.biblioteca.servicios.dto.LibrosAlquiladosDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DB {

    private static Map<Integer, Libro> libros;
    private static Set<Usuario> usuarios;
    private static Map<Integer, Genero> generos;

    //colecciona para guardar los libros alquilados por email
    private static Map<String, List<Libro>> librosAlquiladosPorUsuario;

    static {        
        Genero g1 = new Genero(1, "Comedia");
        
        //LIBROS
        libros = new HashMap<Integer, Libro>();
        libros.put(1, new Libro(1, "El Quijote", "Miguel de Cervantes", true, g1 ));
        libros.put(2, new Libro(2, "La Odisea", "Homero", true, g1 ));
        libros.put(3, new Libro(3, "La Divina Comedia", "Dante", true, g1 ));
        libros.put(4, new Libro(4, "La vida es Sueño", "Calderón de la Barca", true, g1));

        //USUARIOS
        usuarios = new HashSet<Usuario>();
        usuarios.add(new Usuario("begona@gmail.com", "1234", "Begoña", "Olea"));
        usuarios.add(new Usuario("laura@gmail.com", "1234", "Laura", "Bilbao"));

        //LIBROS PRESTADOS POR USUARIO
        librosAlquiladosPorUsuario = new HashMap<String, List<Libro>>();
        ArrayList<Libro> alquilados1 = new ArrayList<Libro>();
        ArrayList<Libro> alquilados2 = new ArrayList<Libro>();
        librosAlquiladosPorUsuario.put("begona@gmail.com", alquilados1);
        librosAlquiladosPorUsuario.put("laura@gmail.com", alquilados2);
        
        //GENEROS
        generos = new HashMap<Integer, Genero>();
        generos.put(1, g1 );
        generos.put(2, new Genero(2, "Drama"));
        generos.put(3, new Genero(3, "Histórico"));
    }

    private DB() {
    }

    public synchronized static Collection<Libro> getAllLibros() {
        return libros.values();
    }

    public static Collection<LibrosAlquiladosDTO> getLibrosPrestados() {
        Set<LibrosAlquiladosDTO> prestados = new HashSet<LibrosAlquiladosDTO>();
//        for (Libro l : libros.values()) {
//            if (!l.isDisponible()) {
//                prestados.add(l);
//            }
//        }
         Set<String> emails = librosAlquiladosPorUsuario.keySet(); // emials
         
         for(String email : emails){
            for(Libro libro : librosAlquiladosPorUsuario.get(email)){
                LibrosAlquiladosDTO alq = new LibrosAlquiladosDTO(libro.getId(), 
                                            libro.getTitulo(),
                                            libro.getAutor(),
                                            email);
                prestados.add(alq);
            }
         }
        return prestados;
    }

    public synchronized static void alquilar(int id, String email) {
        //MEJORAR - LANZAR UNA EXCEPCION SI ID NO EXISTE
        // SINO  libros.get(id) returna null y 
        // null.setDiponible(fale) da NullPointerExcpetion    
        Libro alquilado = libros.get(id);
        alquilado.setDisponible(false);
        
        librosAlquiladosPorUsuario.get(email).add(alquilado);
    }
    
    public synchronized  static Collection<LibrosAlquiladosDTO> getLibrosAlquilados(String email){
        Set<LibrosAlquiladosDTO> prestados = new HashSet<LibrosAlquiladosDTO>();
         for(Libro libro : librosAlquiladosPorUsuario.get(email)){
                LibrosAlquiladosDTO alq = new LibrosAlquiladosDTO(libro.getId(), 
                                            libro.getTitulo(),
                                            libro.getAutor(),
                                            email);
                prestados.add(alq);
            }        
        return prestados;
    }
    

    public synchronized static void altaLibro(Libro libro) throws DBException {
        if (libros.containsKey(libro.getId())) {
            throw new DBException("El libro ya existe con el id " + libro.getId());
        }
        libros.put(libro.getId(), libro);
    }

    public synchronized static Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    public synchronized static void altaUsuario(Usuario u) throws DBException {
        boolean seAñade = usuarios.add(u);
        if (!seAñade) {
            throw new DBException("El usuario no pudo ser añadido. El email ya existe.");
        }
    }
 
     public synchronized static Collection<Genero> getGeneros() {
        return generos.values();
    }
     
    public synchronized static Genero getGeneroPorId(Integer id)  throws DBException {
        if( ! generos.containsKey(id)){
            throw new DBException("El género no existe para el id dado");
        }
        return generos.get(id);
    }
     
     public synchronized static void altaGenero(Genero g) throws DBException {
        if (generos.containsKey(g.getId())) {
            throw new DBException("El género no pudo ser añadido. El id ya existe.");
        }
        generos.put(g.getId(), g);
    }
     
     public synchronized static boolean existeIdGenero(int id){
         return generos.containsKey(id);
     }
}
