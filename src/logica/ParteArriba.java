/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import GUI.PrincipalM;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author royum
 */
public class ParteArriba extends MenuBar {
    private Menu menuArvchivoSistema, menuArchivoUsuario;
    private MenuItem iAbrir, iSalir, AbrirMusicasUsuario,reiniciar;
    private String nombre;
    private PrincipalM menuPrincipal;

    public ParteArriba(String nombreUsuario, PrincipalM menuPrincipal) {
        this.nombre = nombreUsuario;
        this.menuPrincipal = menuPrincipal; 

        menuArvchivoSistema = new Menu("Archivos Computadora");
        menuArchivoUsuario = new Menu("Archivos Usuario " + nombre);
        iAbrir = new MenuItem("Abrir");
        iSalir = new MenuItem("Salir");
        reiniciar=new MenuItem("reiniciar");
        AbrirMusicasUsuario = new MenuItem("Abrir musicas de " + nombre);
        prepararListener();
        prepararMenus();
    }
    
    private void prepararMenus() {

        menuArvchivoSistema.getItems().add(iAbrir);

        menuArchivoUsuario.getItems().add(AbrirMusicasUsuario);

        this.getMenus().addAll(menuArvchivoSistema, menuArchivoUsuario);
    }

    private void prepararListener() {
        try {
            iAbrir.setOnAction(e -> ArchivosMostrar.SeleccionarArchivo());
            iSalir.setOnAction(e -> MusicaReprod.getStage().close());

            reiniciar.setOnAction(e -> {
                
                menuPrincipal.cargarReproductorMusica(menuPrincipal);
            });

            AbrirMusicasUsuario.setOnAction(e -> {
                String nombreUsuario = nombre; 
                UsersArchivos archivosUsuarios = new UsersArchivos(nombreUsuario);

                Stage stage = new Stage();
                archivosUsuarios.abrirFileChooserYReproducir(stage); 
            });
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir un explorador" + e.getMessage());
        }
    }

   
}
