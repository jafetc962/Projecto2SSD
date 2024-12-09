/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author royum
 */
public class Users implements Serializable {
    
    private static final long serialVersionUID = 1L; 
    private String nombre;
    private String password;
    private boolean esAdmin;
    private ArrayList<Musica> bibliotecaMusical;
    private ArrayList<Juego> bibliotecaJuego;
    private ArrayList<MessageChat> AreaChat;
    private String rutaFotoPerfil; 

    public Users(String nombre, String password, boolean esAdmin) throws IOException {
        this.nombre = nombre;
        this.password = password;
        this.esAdmin = esAdmin;
        this.bibliotecaMusical = new ArrayList<>();
        this.bibliotecaJuego = new ArrayList<>();
        this.AreaChat = new ArrayList<>();
        this.rutaFotoPerfil= "/imagenes/fotoPerfil.png";
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public boolean EsAdmin() {
        return esAdmin;
    }


    public ArrayList<Musica> getBibliotecaMusical() {
        return bibliotecaMusical;
    }

    public ArrayList<Juego> getBibliotecaJuego() {
        return bibliotecaJuego;
    }

    public ArrayList<MessageChat> getAreaChat() {
        return AreaChat;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void recibirMensaje(MessageChat mensaje) {
        this.AreaChat.add(mensaje);
    }
    
      public String getRutaFotoPerfil() {
        return rutaFotoPerfil;
    }

    public void setRutaFotoPerfil(String rutaFotoPerfil) {
        this.rutaFotoPerfil = rutaFotoPerfil;
    }

}
