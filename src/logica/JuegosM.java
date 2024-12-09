/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import GUI.PrincipalM;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author royum
 */
public class JuegosM extends JFrame {

    private String usuarioActual; 
    private boolean esAdmin; 
    private JLabel fondo;
    private JPanel panelBotones;
    private File archivoUsuario; 
    private ControlUsers manejoUsuarios;
    private Users user;

    public JuegosM(String usuarioActual, File archivoUsuario) {
        this.usuarioActual = usuarioActual;
        this.archivoUsuario = archivoUsuario;

       
        this.manejoUsuarios = new ControlUsers();

        
        this.esAdmin = manejoUsuarios.esAdmin(usuarioActual);

        setTitle("Menu Steam del Usuario: " + usuarioActual);
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

        JButton btnVerJuegos = crearBoton("Ver Juegos", "/imagenes/ver.png");
        JButton btnAgregarJuegos = crearBoton("Agregar Juegos", "/imagenes/agregar.png");
        JButton btnVolver = crearBoton("Volver", "/imagenes/regresar.png");

        if (!manejoUsuarios.esAdmin(usuarioActual)) {
            btnAgregarJuegos.setVisible(false);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBotones.add(btnVerJuegos, gbc);

        gbc.gridx = 1;
        panelBotones.add(btnAgregarJuegos, gbc);

        gbc.gridx = 2;
        panelBotones.add(btnVolver, gbc);

        
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                fondo.setBounds(0, 0, getWidth(), getHeight());
                cargarFondo("/imagenes/juegosMenu.png");
                panelBotones.setBounds(getWidth() / 4, getHeight() / 3, getWidth() / 2, getHeight() / 3);
            }
        });

        
        btnVerJuegos.addActionListener(e -> verJuegos());
        btnAgregarJuegos.addActionListener(e -> {
            System.out.println("hola salido.");
            agregarJuegos();
        });
        btnVolver.addActionListener(e -> {
            try {
                volver();
            } catch (IOException ex) {
                Logger.getLogger(JuegosM.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        setVisible(true);
    }

    private void cargarFondo(String ruta) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(ruta));
            Image img = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT); 
            fondo.setIcon(new ImageIcon(img));
            fondo.setHorizontalAlignment(SwingConstants.CENTER);
            fondo.setVerticalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el fondo: " + ruta);
        }
    }

    private JButton crearBoton(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);

        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaIcono));
            Image img = icono.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); 
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono: " + rutaIcono);
        }   

        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.setFont(new Font("Consolas", Font.PLAIN, 14));
        boton.setPreferredSize(new Dimension(120, 120));

        
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

    private void verJuegos() {

        BibliotecaSteam juegos = new BibliotecaSteam(usuarioActual);
        juegos.setVisible(true);
        dispose();

    }

    private void agregarJuegos() {

        
        if(!manejoUsuarios.esAdmin(usuarioActual)) {
            System.out.println("Usuario no es administrador.");
            JOptionPane.showMessageDialog(this,
                    "No tienes permisos de administrador. Acceso denegado.",
                    "Acceso Denegado",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Users usuario = manejoUsuarios.ObtenerUsuario(usuarioActual);

        if(usuario != null){

            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/confirmar.png"));
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

            System.out.println("hola");
            JOptionPane.showMessageDialog(this, "Bienvenido, administrador: " + usuarioActual, "Acceso Permitido", JOptionPane.INFORMATION_MESSAGE,iconoEscalado);

            
            JuegosAgre agregarJuegosPanel = new JuegosAgre(usuarioActual);
            agregarJuegosPanel.setVisible(true);
            dispose();
        }else{
            JOptionPane.showMessageDialog(this,"El usuario no existe en el sistema. Verifique la configuracion.","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volver() throws IOException {
        PrincipalM m = new PrincipalM(usuarioActual, archivoUsuario);
        m.setVisible(true);
        dispose(); 
    }
}
