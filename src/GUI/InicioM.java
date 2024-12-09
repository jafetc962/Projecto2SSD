/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InicioM extends JFrame {
    
    private JPanel panelPrincipal;
    private JLabel fondo;
    private JButton botonCrearUsuario;
    private JButton botonIniciarSesion;
    private JButton botonCerrarAplicacion;

    public InicioM() {
        configurarVentana();
        configurarComponentes();
    }

    private void configurarVentana() {
        setTitle("Menu Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(470, 280); 
        setLocationRelativeTo(null); 
        setResizable(false); 
    }

    private void configurarComponentes() {
       
        panelPrincipal = new JPanel(new BorderLayout());
        setContentPane(panelPrincipal);

        
        fondo = new JLabel();
        fondo.setLayout(new GridBagLayout()); 
        cargarFondo("/imagenes/MenuInicial.gif"); 
        panelPrincipal.add(fondo, BorderLayout.CENTER);

        
        botonCrearUsuario = crearBoton("Crear Usuario");
        botonIniciarSesion = crearBoton("Iniciar Sesion");
        botonCerrarAplicacion = crearBoton("Cerrar Aplicacion");

       
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false); 
        panelBotones.setLayout(new GridLayout(3, 1, 10, 10)); 

        panelBotones.add(botonCrearUsuario);
        panelBotones.add(botonIniciarSesion);
        panelBotones.add(botonCerrarAplicacion);

        fondo.add(panelBotones);
        
        botonCrearUsuario.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e) {
                 
                 CreateUser crear=new CreateUser();
                 crear.setVisible(true);
                 dispose();
                 
             }
            
        });
        
        botonIniciarSesion.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e) {
                 
                 InicioSesion entrar=new InicioSesion();
                 entrar.setVisible(true);
                 dispose();
             }
            
        });
        
        botonCerrarAplicacion.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e) {
                 
                 System.exit(0);
                 
             }
            
        });
        
    }

    private void cargarFondo(String ruta) {
        try {
            ImageIcon gifIcon = new ImageIcon(getClass().getResource(ruta));
            fondo.setIcon(gifIcon); 
        } catch (Exception e) {
            System.out.println("No se pudo cargar el fondo: " + ruta);
        }
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Consolas", Font.BOLD, 16));
        boton.setPreferredSize(new Dimension(200, 50));
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

    
    
   
   
}
