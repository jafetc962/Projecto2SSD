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


public class CreateUser extends JFrame {


    private JTextField nombreField;
    private JPasswordField passwordField;
    private JCheckBox adminCheckBox;
    private JButton crearUsuario;
    private JButton cancelar;
    private JButton volver;
    private ControlUsers manejoUsuarios;
    private File archivoUsuario; // Para el archivo binario
    private JLabel fondo;

    public CreateUser() {
        manejoUsuarios = new ControlUsers();
        manejoUsuarios.CargarUsuarios();

        setTitle("Crear Usuario");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

       
        fondo = new JLabel();
        fondo.setLayout(new GridBagLayout()); 
        cargarFondo("/imagenes/regstrar.gif");
        setContentPane(fondo); 

        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false); 
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

      
        JLabel tituloLabel = new JLabel("Crear Usuario");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 25));
        tituloLabel.setForeground(Color.LIGHT_GRAY);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(tituloLabel);
        mainPanel.add(Box.createVerticalStrut(20));

      
        JPanel nombrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nombrePanel.setOpaque(false); 
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 17));
        nombreLabel.setForeground(Color.LIGHT_GRAY);
        nombreField = new JTextField(15);
        nombreField.setFont(new Font("Arial", Font.PLAIN, 17));
        nombrePanel.add(nombreLabel);
        nombrePanel.add(nombreField);
        mainPanel.add(nombrePanel);

        // Campo de Contraseña
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setOpaque(false); 
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 17));
        passwordLabel.setForeground(Color.LIGHT_GRAY);
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 17));
        passwordField.setForeground(Color.BLACK);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel);

        
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        adminPanel.setOpaque(false); 
        adminCheckBox = new JCheckBox("¿Es administrador?");
        adminCheckBox.setFont(new Font("Arial", Font.BOLD, 17));
        adminCheckBox.setForeground(Color.LIGHT_GRAY);
        adminCheckBox.setOpaque(false); 
        adminPanel.add(adminCheckBox);
        mainPanel.add(adminPanel);

        // Botones
        mainPanel.add(Box.createVerticalStrut(20)); 
        mainPanel.add(crearPanelBotones()); 

        fondo.add(mainPanel); 

        
        crearUsuario.addActionListener(e -> {
            String nombre = nombreField.getText();
            String password = new String(passwordField.getPassword());
            boolean esAdmin = adminCheckBox.isSelected();

            if (nombre.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (manejoUsuarios.RegistroUsuario(nombre, password, esAdmin)) {
                    JOptionPane.showMessageDialog(null, "Usuario creado exitosamente:\n"
                            + "Nombre: " + nombre + "\nAdministrador: " + (esAdmin ? "Sí" : "No"));
                    limpiarCampos();
                    try {
                        new PrincipalM(nombre, archivoUsuario).setVisible(true);
                    } catch (IOException ex) {
                        Logger.getLogger(CreateUser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelar.addActionListener(e -> limpiarCampos());

        volver.addActionListener(e -> {
            new InicioM().setVisible(true);
            dispose();
        });
    }

    private JPanel crearPanelBotones() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10)); 
        buttonPanel.setOpaque(false); 

        crearUsuario = crearBoton("Crear");
        cancelar = crearBoton("Cancelar");
        volver = crearBoton("Volver");

      
        buttonPanel.add(crearUsuario);
        buttonPanel.add(cancelar);
        buttonPanel.add(volver);

        return buttonPanel;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Consolas", Font.BOLD, 14)); 
        boton.setPreferredSize(new Dimension(130, 40)); 
        boton.setBackground(Color.BLUE);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(0, 122, 204), 2, true));

        
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setContentAreaFilled(true);
                boton.setBackground(new Color(0, 0, 0, 50)); 
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.BLUE);
            }
        });
        return boton;
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

    private void limpiarCampos() {
        nombreField.setText("");
        passwordField.setText("");
        adminCheckBox.setSelected(false);
    }
}
