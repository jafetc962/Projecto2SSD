/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author HP
 */
import logica.ChatPantalla;
import logica.Administrador;
import logica.ControlUsers;
import logica.Users;
import logica.PerfilManejo;
import logica.PerfilPant;
import logica.DatosDeCancion;
import logica.MusicM;
import logica.Musica;
import logica.ReproductorPanelPrincipal;
import logica.BibliotecaSteam;
import logica.JuegosM;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author royum
 */
public class PrincipalM extends JFrame {

    private JPanel panelPrincipal;
    private JFrame reproductorFrame; 
    private JLabel fondo;
    private JPanel panelBotones;
    private JButton Juegos;
    private JButton Musicas;
    private JButton Discord;
    private JButton Perfil;
    private JButton Cerrar_Sesion;
    private JButton Administracion;
    private String password;
    private ControlUsers manejoUsuarios;
    private static String nombreUsuario;
    private File archivoUsuario;
    private Users usu;
    private Administrador adminsito;
    private BibliotecaSteam juegos;

    public PrincipalM(String nombre,File archivoUsuarios) throws IOException {
        this.nombreUsuario=nombre;
        this.archivoUsuario=archivoUsuarios;
        GUI();
        
        manejoUsuarios = new ControlUsers();
        manejoUsuarios.CargarUsuarios();
        adminsito=new Administrador(nombreUsuario,password);
        adminsito.setListaUsuarios(manejoUsuarios.getUsuarios());
        
    }

    private void GUI() {
        setTitle(" Cuenta de " + nombreUsuario);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        
        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

        
        fondo = new JLabel();
        fondo.setBounds(0, 0, getWidth(), getHeight());
        layeredPane.add(fondo, Integer.valueOf(0)); 

        
        panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false); 
        panelBotones.setBounds(getWidth() / 4, getHeight() / 3, getWidth() / 2, getHeight() / 3);
        layeredPane.add(panelBotones, Integer.valueOf(1)); 

        
        Juegos = crearBoton("StoreGames", "/imagenes/juegosMenu.png");
        Musicas = crearBoton("MusicX", "/imagenes/musicaMenu.png");
        Discord = crearBoton("Chats", "/imagenes/chatsMenu.png");
        Perfil = crearBoton("Perfil", "/imagenes/fotoPerfil.jpg");
        Cerrar_Sesion = crearBoton("Cerrar Sesion", "/imagenes/cerrarSesion.png");
        Administracion = crearBoton("Administrador", "/imagenes/administracion.png");

        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); 
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBotones.add(Juegos, gbc);

        gbc.gridx = 1;
        panelBotones.add(Musicas, gbc);

        gbc.gridx = 2;
        panelBotones.add(Discord, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelBotones.add(Perfil, gbc);

        gbc.gridx = 1;
        panelBotones.add(Cerrar_Sesion, gbc);

        gbc.gridx = 2;
        panelBotones.add(Administracion, gbc);

       
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                fondo.setBounds(0, 0, getWidth(), getHeight());
                cargarFondo("/imagenes/menuPrincipal.gif");
                panelBotones.setBounds(getWidth() / 4, getHeight() / 3, getWidth() / 2, getHeight() / 3);
            }
        });

        Juegos.addActionListener(e -> {

            JuegosM steam=new JuegosM(nombreUsuario,archivoUsuario);
            steam.setVisible(true);
            dispose();
            
        });

        Musicas.addActionListener(e -> {
            
            String[] Opciones={"Entrar a mi reproductor","Entrar al Menu Musica"};
            
              ImageIcon iconoEscalado = null;
            try {
                URL resource = getClass().getResource("/imagenes/fotoPerfil.png");
                if (resource != null) {
                    ImageIcon iconoOriginal = new ImageIcon(resource);
                    Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH); 
                    iconoEscalado = new ImageIcon(imagenEscalada);
                }
            } catch (Exception ex) {
                System.out.println("No se pudo cargar el icono de administracion: " + ex.getMessage());
            }
            
            int opcion=JOptionPane.showOptionDialog(null, "Que desea ver "+nombreUsuario+ " ?", "Opciones Musica", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, iconoEscalado, Opciones, Opciones[0]);
            
            if(opcion==0){
                
                cargarReproductorMusica(this);
                
            }else if(opcion==1){
                
                dispose();
                MusicM music=new MusicM(nombreUsuario,archivoUsuario);
                music.setVisible(true);
                
                
            }
            
           
            
            
        });

        Discord.addActionListener(e -> {

            try {
                ChatPantalla dis=new ChatPantalla(nombreUsuario);
                dis.setVisible(true);
                dispose();
            } catch (IOException ex) {
                Logger.getLogger(PrincipalM.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Perfil.addActionListener(e -> {
            
            String[] opciones = {"Ingresar a mis carpetas","Ingresar a mi perfil"};
            
            ImageIcon iconoEscalado = null;
            try {
                URL resource = getClass().getResource("/imagenes/fotoPerfil.png");
                if (resource != null) {
                    ImageIcon iconoOriginal = new ImageIcon(resource);
                    Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH); 
                    iconoEscalado = new ImageIcon(imagenEscalada);
                }
            } catch (Exception ex) {
                System.out.println("No se pudo cargar el icono de administracion: " + ex.getMessage());
            }
            
            int opcion=JOptionPane.showOptionDialog(null, "Que desea ver "+nombreUsuario + " ?", "Opciones de perfil", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, iconoEscalado, opciones, opciones[0]);
              
            if (opcion == 0) {

                if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                    
                    File carpetaUsuario = new File(System.getProperty("user.dir") + File.separator + "UsuariosGestion" + File.separator + nombreUsuario);
                    if (carpetaUsuario.exists()) {
                        
                        PerfilManejo navegador = new PerfilManejo(nombreUsuario);
                        navegador.setVisible(true);
                        dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "El directorio del usuario no existe: " + carpetaUsuario.getAbsolutePath());
                }
            }else{
                JOptionPane.showMessageDialog(null, "Error: El nombre de usuario es nulo o vacio.");
            }
            
        }else if(opcion==1){
            
                try {
                    PerfilPant m =new PerfilPant(nombreUsuario);
                    dispose();
                    m.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(PrincipalM.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        }

        });

        Administracion.addActionListener(e -> {
            
            String usuarioEnSesion= nombreUsuario;
            
            if(!manejoUsuarios.esAdmin(usuarioEnSesion)){
                JOptionPane.showMessageDialog(null, "No eres admin Acceso denegado!", "Informacion", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String[] opciones = {"Listar Usuarios", "Ingresar carpetas Usuarios","Borrar Usuarios","Ingresar a a Biblioteca de usuarios"};

            
           
            ImageIcon iconoEscalado = null;
            try {
                URL resource = getClass().getResource("/img_menuprin/interrogacion.png");
                if (resource != null) {
                    ImageIcon iconoOriginal = new ImageIcon(resource);
                    Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH); 
                    iconoEscalado = new ImageIcon(imagenEscalada);
                }
            } catch (Exception ex) {
                System.err.println("No se pudo cargar el icono de administración: " + ex.getMessage());
            }

            
            int opcion=JOptionPane.showOptionDialog(null, "Que desea ver admin " +nombreUsuario +" ?","Opciones Administrador",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,iconoEscalado,opciones,opciones[0]);
            
            if(opcion==0){
                
                adminsito.ListarUsuarios();
                
            }else if(opcion==1){
                
                String UsuarioIngresado = JOptionPane.showInputDialog(null, "Ingrese el nombre del usuario para ingresar a sus carpetas","Verificacion Usuario",JOptionPane.PLAIN_MESSAGE);

                if(UsuarioIngresado != null && !UsuarioIngresado.trim().isEmpty()){

                    Users usuario = manejoUsuarios.ObtenerUsuario(UsuarioIngresado);
                    if(usuario != null){

                        JOptionPane.showMessageDialog(null,"Bienvenido a las carpetas de " + UsuarioIngresado + "!");

                        File DirectorioAdmin = new File(System.getProperty("user.dir")
                                + File.separator + "UsuariosGestion"
                                + File.separator + UsuarioIngresado);

                        AdmininistracionP adminPanel = new AdmininistracionP(DirectorioAdmin, this, archivoUsuario);
                        adminPanel.setVisible(true);
                        this.setVisible(false);

                    }else{
                       
                        JOptionPane.showMessageDialog(null,"El usuario ingresado no existe.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "No ingresaste ningun nombre de usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else if (opcion == 2) {

                String Usuarioborrar = JOptionPane.showInputDialog(null, "Ingrese el nombre del usuario que desea eliminar", "ELIMINAR USUARIO", JOptionPane.PLAIN_MESSAGE);
                
                if(Usuarioborrar==null){
                    JOptionPane.showMessageDialog(null, "No existe el usuario");
                    return;
                    
                }
                
                if(Usuarioborrar.equalsIgnoreCase(nombreUsuario)){
                    
                    JOptionPane.showMessageDialog(null, "No podes eliminar tu cuenta porque estas en sesion activa","INFORMACION",JOptionPane.INFORMATION_MESSAGE);
                    return;
                    
                }

                if(Usuarioborrar!= null&&!Usuarioborrar.trim().isEmpty()){

                    Users usu=manejoUsuarios.ObtenerUsuario(Usuarioborrar);
                    if(usu!=null){

                        int Confirmacion = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar al usuario" + Usuarioborrar + " ?\n" + "Esto eliminara todos sus datos y carpetas", "CONFIRMAR ELIMINACION", JOptionPane.YES_NO_OPTION);

                        if (Confirmacion==JOptionPane.YES_OPTION) {

                            adminsito.BorrarUsuario(Usuarioborrar);
                            JOptionPane.showMessageDialog(null, "El usuario "+Usuarioborrar+ "ha sido eliminaod exitsamente","ELIMINACION EXITOSA",JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            try {
                                new PrincipalM(nombreUsuario,archivoUsuario).setVisible(true);
                            } catch (IOException ex) {
                                Logger.getLogger(PrincipalM.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }

                    }else{

                        JOptionPane.showMessageDialog(null,"El usuario ingresado no existe.","Error",JOptionPane.ERROR_MESSAGE);

                    }

                }else{
                    
                    JOptionPane.showMessageDialog(null,"No ingresaste ningun nombre de usuario.","Error",  JOptionPane.ERROR_MESSAGE);

                }

            }else if(opcion==3){
                
                String NombreUsuarioBuscar=JOptionPane.showInputDialog(null,"Ingrese el nombre de usuario para ingresar a su perfil");
                
                if(NombreUsuarioBuscar!=null&&!NombreUsuarioBuscar.trim().isEmpty()){
                    
                    Users usuario=manejoUsuarios.ObtenerUsuario(NombreUsuarioBuscar);
                    if(usuario!=null){
                        if(NombreUsuarioBuscar.equals(nombreUsuario)){
                            
                            int confirmacion = JOptionPane.showConfirmDialog(null,"Estas intentando ver tu propio perfil en vista de administrador. ¿Estas seguro?","Confirmacion",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                            
                            if (confirmacion == JOptionPane.YES_OPTION) {
                                try {
                                    dispose();
                                    AdministracionPerfil p = new AdministracionPerfil(nombreUsuario, usuario.getNombre(), this);
                                    this.setVisible(false);
                                    p.setVisible(true);
                                } catch (IOException ex) {
                                    Logger.getLogger(PrincipalM.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        }else{

                            try{
                                
                                dispose();
                                AdministracionPerfil p = new AdministracionPerfil(nombreUsuario, usuario.getNombre(), this);
                                this.setVisible(false);
                                p.setVisible(true);

                            }catch(IOException ex){

                                Logger.getLogger(PrincipalM.class.getName()).log(Level.SEVERE, null, e);

                            }

                        }
                    }else{
                        
                        JOptionPane.showMessageDialog(null, "El usuario \"" + NombreUsuarioBuscar + "\" no existe.", "Usuario no encontrado",JOptionPane.ERROR_MESSAGE);
                        
                    }
                    
                }else{
                    
                    JOptionPane.showMessageDialog(null, "Debe ingresar un nombre de usuario valido.", "Error", JOptionPane.WARNING_MESSAGE);
                    
                }
                
            }
        });
            

        Cerrar_Sesion.addActionListener(e -> {

                InicioM inicio = new InicioM();
                inicio.setVisible(true);
                dispose();
                
        });
        
        

    }

     private void cargarFondo(String ruta) {
        try{
            
            ImageIcon icon = new ImageIcon(getClass().getResource(ruta));
            Image img = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT); 
            fondo.setIcon(new ImageIcon(img));
            fondo.setHorizontalAlignment(SwingConstants.CENTER); 
            fondo.setVerticalAlignment(SwingConstants.CENTER);
            
        }catch (Exception e) {
            System.out.println("No se pudo cargar el fondo: " + ruta);
        }
    }


    private JButton crearBoton(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);

        
        try{
            
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaIcono));
            Image img = icono.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH); 
            boton.setIcon(new ImageIcon(img));
            
        }catch (Exception e) {
            System.out.println("No se pudo cargar el icono: " + rutaIcono);
        }

        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.setFont(new Font("Consolas", Font.PLAIN, 13));
        boton.setPreferredSize(new Dimension(100, 100));

        
        boton.setContentAreaFilled(false); 
        boton.setOpaque(false);          
        boton.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 50))); 

        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                boton.setContentAreaFilled(true); 
                boton.setBackground(new Color(200, 200, 200, 100)); 
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                boton.setContentAreaFilled(false); 
            }
        });

        boton.setForeground(Color.WHITE); 

        return boton;
    }

    
    public void cargarReproductorMusica(PrincipalM menuPrincipal) { 
        if(reproductorFrame != null){
            reproductorFrame.setVisible(true);
        }else{
         
            JFXPanel jfxPanel = new JFXPanel();
            jfxPanel.setPreferredSize(new Dimension(850, 800));

            Platform.runLater(() -> {
                try{
                    Scene escena = new Scene(new ReproductorPanelPrincipal(nombreUsuario, menuPrincipal), 850, 750);
                    jfxPanel.setScene(escena);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            });

            reproductorFrame = new JFrame("Reproductor de Musica de " + nombreUsuario);
            reproductorFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 

            
            reproductorFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(
                            reproductorFrame,
                            "¿Estas seguro de que deseas cerrar el reproductor? si tienes musica en reproduccion esto detendra la musica.",
                            "Cerrar Reproductor de " + nombreUsuario,
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        detenerMusica();
                        reproductorFrame.setVisible(false); // Ocultar la ventana
                    }
                }
            });

            reproductorFrame.getContentPane().add(jfxPanel);
            reproductorFrame.pack();
            reproductorFrame.setLocationRelativeTo(null);
            reproductorFrame.setVisible(true);
        }
    }


    private void detenerMusica() {
        
        Musica.stop(); 
        
    }

}
