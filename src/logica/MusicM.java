/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import GUI.PrincipalM;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MusicM extends JFrame {
    
    private String Usuarioactual;
    private boolean Esadmin;
    private File Archivoentrar;
    private ControlUsers manejousuarios;
    private Users user;

    public MusicM(String UsuarioActual,File ArchivoUsuario) {
        this.Usuarioactual=UsuarioActual;
        this.Archivoentrar= ArchivoUsuario;
        
        this.manejousuarios=new ControlUsers();
        
        this.Esadmin=manejousuarios.esAdmin(UsuarioActual);
        
        setTitle("Menu de musica del Usuario: "+UsuarioActual);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 

        
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImage = new ImageIcon(getClass().getResource("/imagenes/musicaMenu.jpg")).getImage();
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    System.out.println("No se pudo cargar la imagen de fondo.");
                }
            }
        };
        panelPrincipal.setLayout(new GridBagLayout()); 

        
        JButton btnVolver = crearBoton("Volver", "/imagenes/regresar.png");
        JButton btnAgregarMusica = crearBoton("Agregar Musica", "/imagenes/masMusica.png");
        JButton btnVerMusicas = crearBoton("Ver Musicas", "/imagenes/seeMusic.png");
        
        if(!manejousuarios.esAdmin(UsuarioActual)){
            btnAgregarMusica.setVisible(false);
        }

        
        btnVolver.addActionListener(e -> {
            
            Volver();
            
        });
        btnAgregarMusica.addActionListener(e -> {

            MusicaAgre.SeleccionaryAgregarCancion();
            
        });
        
        btnVerMusicas.addActionListener(e -> {
           
            dispose();
            Musicas m=new Musicas(UsuarioActual);
            m.setVisible(true);
            
        });

       
         GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); 
        gbc.gridy = 0; 
        
        gbc.gridx = 0; 
        panelPrincipal.add(btnVerMusicas, gbc);

        gbc.gridx = 1; 
        panelPrincipal.add(btnAgregarMusica, gbc);

        gbc.gridx = 2; 
        panelPrincipal.add(btnVolver, gbc);

        
        setContentPane(panelPrincipal);
        setVisible(true);
    }

    private JButton crearBoton(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);

        
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaIcono));
            Image img = icono.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
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

  
    

    private void Volver() {
        
        try {
            dispose();
            PrincipalM m=new PrincipalM(Usuarioactual, Archivoentrar);
            m.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(MusicM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}
