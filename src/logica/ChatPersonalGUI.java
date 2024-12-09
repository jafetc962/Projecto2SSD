/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author royum
 */
public class ChatPersonalGUI extends JFrame{
    
    private String UsuarioEnSesion;
    private ChatsPersonales GestorChats;
    private JList<String> ListaConversaciones;
    private DefaultListModel<String> ModeloLista;
    private ChatPersonalPantalla PANEL_DE_CHAT_PRIVADO;
    private File archivoentrar;
    
    
    public ChatPersonalGUI(String UsuarioEnSesion) throws IOException {

        this.UsuarioEnSesion = UsuarioEnSesion;
        this.GestorChats = new ChatsPersonales();

        ConfigurarVentana();
        CargarConversaciones();

    }
    
    private void ConfigurarVentana() throws IOException{
        
        setTitle("Chats Privados - Usuario: " + UsuarioEnSesion);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        PANEL_DE_CHAT_PRIVADO = new ChatPersonalPantalla(UsuarioEnSesion);

        
        ModeloLista = new DefaultListModel<>();
        ListaConversaciones = new JList<>(ModeloLista);
        ListaConversaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(ListaConversaciones);
        scroll.setBorder(BorderFactory.createTitledBorder("Conversaciones Activas"));

        
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botonAbrir = new JButton("Abrir Chat");
        JButton botonNueva = new JButton("Nueva Conversacion");
        JButton botonVolver=new JButton("Volver");
        panelInferior.add(botonVolver);
        panelInferior.add(botonAbrir);
        panelInferior.add(botonNueva);
        
        
        botonAbrir.addActionListener(this::AbrirChat);
        botonNueva.addActionListener(this::IniciarConversacion);
        botonVolver.addActionListener(e-> {
            
            VolverMenu();
            
        });

        panelPrincipal.add(scroll,BorderLayout.CENTER);
        panelPrincipal.add(panelInferior,BorderLayout.SOUTH);
        
        getContentPane().add(panelPrincipal).

        setVisible(true);


    }
    
    private void CargarConversaciones(){
        
        ArrayList<String> Conversaciones= GestorChats.ObtenerConversaciones(UsuarioEnSesion);
        ModeloLista.clear();
        
        for (String Conversacione : Conversaciones) {
            
            ModeloLista.addElement(Conversacione);
            
        }
        
    }
    
    private void AbrirChat(ActionEvent e){
        
        String Seleccion= ListaConversaciones.getSelectedValue();
        if(Seleccion==null){
            
            JOptionPane.showMessageDialog(null, "xd");
            return;
            
        }
        
        
        String[] Partes = Seleccion.split("_");
        if (Partes.length != 2) {
            JOptionPane.showMessageDialog(null, "El formato de la conversación no es valido.");
            return;
        }
        
        String OtroUsuario = Partes[0].equals(UsuarioEnSesion) ? Partes[1] : Partes[0];
        
        if(OtroUsuario.equalsIgnoreCase(UsuarioEnSesion)){
            
            JOptionPane.showMessageDialog(null, "No puedes abrir una conversacion contigomismo");
            return;
            
        }

        try {
            if (PANEL_DE_CHAT_PRIVADO == null || !PANEL_DE_CHAT_PRIVADO.isVisible()) {

                PANEL_DE_CHAT_PRIVADO = new ChatPersonalPantalla(UsuarioEnSesion);
                PANEL_DE_CHAT_PRIVADO.setSize(500, 600); 
                PANEL_DE_CHAT_PRIVADO.setLocationRelativeTo(null);
            }
            PANEL_DE_CHAT_PRIVADO.actualizarConversacion(OtroUsuario);
            PANEL_DE_CHAT_PRIVADO.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(ChatPersonalGUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ERRROR" +ex.getMessage());
        }

    }

    
    private void IniciarConversacion(ActionEvent e){
        
        ControlUsers manejoUsuarios=new ControlUsers();
        ArrayList<Users> ListaUsuarios=manejoUsuarios.getUsuarios();
        
        
        ArrayList<String> UsuariosDisponibles=new ArrayList<>();
        for (Users usuario : ListaUsuarios) {
            
            if(!usuario.getNombre().equalsIgnoreCase(UsuarioEnSesion)){
                
                UsuariosDisponibles.add(usuario.getNombre());
                
            }
            
        }
        
        String Otrousuario=(String )JOptionPane.showInputDialog(null,"Seleccione un usuario para iniciar la conversacion","Nueva conversacion",JOptionPane.QUESTION_MESSAGE,null,UsuariosDisponibles.toArray(),null);
        
        if(Otrousuario==null){
            
            JOptionPane.showMessageDialog(null, "JUGADOR NO SELECCIONADO");
            return;
            
        }
        
        if(Otrousuario.equalsIgnoreCase(UsuarioEnSesion)){
            
            JOptionPane.showMessageDialog(null, "No podes iniciar una conversacion con vos mismo");
            return;
            
        }
        
        
        try{
            
            GestorChats.GuardarMensajes(UsuarioEnSesion, Otrousuario, "Conversacion Iniciada");
        }catch(IOException ex){
            Logger.getLogger(ChatPersonalGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        CargarConversaciones();
        
    }

    private void VolverMenu() {
      
        dispose();
        try{
            ChatPantalla m=new ChatPantalla(UsuarioEnSesion);
            m.setVisible(true);
        }catch(IOException ex){
            Logger.getLogger(ChatPersonalGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
   
}
