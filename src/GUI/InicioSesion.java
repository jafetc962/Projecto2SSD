/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;


import logica.ControlUsers;
import logica.Users;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InicioSesion extends JFrame {

    private JTextField usuarioField;
    private JPasswordField passwordField;
    private JButton iniciarSesionButton;
    private JButton cancelarButton;
    private JButton volverButton;
    private ControlUsers manejoUsuarios;
    private JLabel fondo;
    private File archivoUsuario; 
    private Users user;

    public InicioSesion() {
        manejoUsuarios = new ControlUsers();
        manejoUsuarios.CargarUsuarios();
        
        setTitle("Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

       
        fondo = new JLabel();
        fondo.setLayout(new GridBagLayout()); 
        cargarFondo("/imagenes/InicioSesion.gif");
        setContentPane(fondo);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false); 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel tituloLabel = new JLabel("Iniciar Sesion");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 25));
        tituloLabel.setForeground(new Color(102, 102, 255));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(tituloLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Campo de Usuario
        JPanel usuarioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usuarioPanel.setOpaque(false);
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setFont(new Font("Arial", Font.BOLD, 17));
        usuarioLabel.setForeground(Color.LIGHT_GRAY);
        usuarioField = new JTextField(15);
        usuarioField.setFont(new Font("Arial", Font.PLAIN, 17));
        usuarioPanel.add(usuarioLabel);
        usuarioPanel.add(usuarioField);
        mainPanel.add(usuarioPanel);

        // Campo de Contraseña
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setOpaque(false); 
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 17));
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 17));
        passwordLabel.setForeground(Color.LIGHT_GRAY);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel);

        // Botones
        mainPanel.add(Box.createVerticalStrut(20)); 
        mainPanel.add(crearPanelBotones()); 

        fondo.add(mainPanel); 

       
        iniciarSesionButton.addActionListener(e -> {
            String usuario = usuarioField.getText();
            String password = new String(passwordField.getPassword());

            if (usuario.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (manejoUsuarios.ValidarCredenciales(usuario, password)) {
                    
                    ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/imagenes/confirmar.png"));
                    Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                    ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
                    
                    JOptionPane.showMessageDialog(null, "Bienvenido, usuario " + usuario + "!", "Exito", JOptionPane.INFORMATION_MESSAGE, iconoEscalado);

                    try {
                        new PrincipalM(usuario, archivoUsuario).setVisible(true);
                    } catch(IOException ex) {
                        Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelarButton.addActionListener(e -> limpiarCampos());

        volverButton.addActionListener(e -> {
            new InicioM().setVisible(true);
            dispose();
        });
    }

    private JPanel crearPanelBotones() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10)); 
        buttonPanel.setOpaque(false); 

        iniciarSesionButton = crearBoton("Iniciar Sesion");
        cancelarButton = crearBoton("Cancelar");
        volverButton = crearBoton("Volver");

        iniciarSesionButton.setBackground(Color.BLUE);
        cancelarButton.setBackground(Color.BLUE);
        volverButton.setBackground(Color.BLUE);
        // Agregamos los botones al panel
        buttonPanel.add(iniciarSesionButton);
        buttonPanel.add(cancelarButton);
        buttonPanel.add(volverButton);

        return buttonPanel;
    }

    private JButton crearBoton(String texto){
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Consolas", Font.BOLD, 17)); 
        boton.setPreferredSize(new Dimension(130, 40)); 
        boton.setBackground(Color.BLUE);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(0, 122, 204), 2, true));

        // Efecto de hover (cambiar color al pasar el mouse)
        boton.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
                boton.setContentAreaFilled(true);
                boton.setBackground(new Color(0, 0, 0, 50)); 
            }

            public void mouseExited(java.awt.event.MouseEvent evt){
                boton.setBackground(Color.BLUE);
            }
        });
        return boton;
    }

    private void cargarFondo(String ruta) {
        try{
            ImageIcon icon = new ImageIcon(getClass().getResource(ruta));
            Image img = icon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT); 
            fondo.setIcon(new ImageIcon(img));
            fondo.setHorizontalAlignment(SwingConstants.CENTER); 
            fondo.setVerticalAlignment(SwingConstants.CENTER);
        }catch(Exception e){
            System.out.println("No se pudo cargar el fondo: " + ruta);
        }
    }

    private void limpiarCampos() {
        usuarioField.setText("");
        passwordField.setText("");
    }
}
