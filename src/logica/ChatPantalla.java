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
public class ChatPantalla extends JFrame{
    
    private static final String ARCHIVO_CHAT = "historial_chat.dat";
    private  String usuarioEnSesion;
    private JPanel panelMensajes;
    private JTextField campoMensaje;
    private File archivosHistorial;
    private File archivosUSUARIO; 
    private Users user;

    public ChatPantalla(String usuarioEnSesion) throws IOException {
        this.usuarioEnSesion = usuarioEnSesion;

        File CarpetasHistorial = new File("UsuariosGestion" + File.separator + usuarioEnSesion + File.separator + "MiChatHistorial");

        if (!CarpetasHistorial.exists() || !CarpetasHistorial.isDirectory()) {
            throw new IOException("La carpeta 'MiChatHistorial' no existe para el usuario: " + usuarioEnSesion);
        }

        
        this.archivosHistorial = new File(CarpetasHistorial, usuarioEnSesion + "_historial.dat");

        if (!archivosHistorial.exists()) {
            archivosHistorial.createNewFile(); 
        }

        configurarVentana();
        CargarMensajes();
    }

    private void configurarVentana() {
        setTitle("Discord del Usuario "+usuarioEnSesion);
        setSize(900,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        
       
        panelMensajes=new JPanel();
        panelMensajes.setLayout(new BoxLayout(panelMensajes,BoxLayout.Y_AXIS));
        panelMensajes.setBackground(Color.WHITE);
        JScrollPane scrollPane=new JScrollPane(panelMensajes);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Mensajes"));
        add(scrollPane,BorderLayout.CENTER);
        
        
        JPanel Panelinferior=new JPanel(new BorderLayout());
        campoMensaje=new JTextField();
        
        JButton botonEnviar = crearBoton("Enviar", "/imagenes/enviar.png");
        JButton botonVolver = crearBoton("Volver", "/imagenes/regresar.png");
        JButton botonHistorial = crearBoton("Historial", "/imagenes/historial.png");
        JButton botonChatPrivado=crearBoton("Chats Privados","/imagenes/chatPriv.png");
        
         
        botonEnviar.addActionListener(e-> {
            try {
                EnviarMensaje();
            } catch (IOException ex) {
                Logger.getLogger(ChatPantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        botonVolver.addActionListener(e-> {
            try {
                volverAlMenu();
            } catch (IOException ex) {
                Logger.getLogger(ChatPantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        botonHistorial.addActionListener(e-> {
            try {
                mostrarHistorial();
            } catch (IOException ex) {
                Logger.getLogger(ChatPantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        botonChatPrivado.addActionListener(e->{
            
            try {
                dispose();
                ChatPersonalGUI priv=new ChatPersonalGUI(usuarioEnSesion);
                priv.setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(ChatPantalla.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        
        
        Panelinferior.add(campoMensaje,BorderLayout.CENTER);
        JPanel panelBotones=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(botonVolver);
        panelBotones.add(botonHistorial);
        panelBotones.add(botonEnviar);
        panelBotones.add(botonChatPrivado);
        Panelinferior.add(panelBotones,BorderLayout.EAST);
        add(Panelinferior,BorderLayout.SOUTH);
        
        setVisible(true);
        
        
    }
    
    private JButton crearBoton(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);

        
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaIcono));
            Image img = icono.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); 
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono: " + rutaIcono);
        }

        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.setFont(new Font("Consolas", Font.PLAIN, 12));
        boton.setPreferredSize(new Dimension(100, 100));

        // Transparencia en reposo.
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

        boton.setForeground(Color.black); 

        return boton;
    }
    
    private void EnviarMensaje() throws IOException{
            
        String texto=campoMensaje.getText().trim();
        if(!texto.isEmpty()){
            
            MessageChat mensaje=new MessageChat(texto,usuarioEnSesion);
            
            GuardarMensajeArchivoIndividual(mensaje);
            GuardarMensajeArchivoGeneral(mensaje);
            AgregarMensajePanel(mensaje);
            
            campoMensaje.setText("");
            
        }
        
    }
    
    private void AgregarMensajePanel(MessageChat mensaje){
        
        JLabel Etiquetamensaje=new JLabel(mensaje.toString());
        Etiquetamensaje.setOpaque(true);
        
        if(mensaje.getRemitente().equals(usuarioEnSesion)){
            
            Etiquetamensaje.setBackground(new Color(173, 216, 230)); 
            Etiquetamensaje.setHorizontalAlignment(SwingConstants.RIGHT);
           
        }else{
            
            Etiquetamensaje.setBackground(Color.LIGHT_GRAY);
            Etiquetamensaje.setHorizontalAlignment(SwingConstants.LEFT);
            
        }
        
        Etiquetamensaje.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panelMensajes.add(Etiquetamensaje);
        panelMensajes.revalidate();
        panelMensajes.repaint();
        
    }
    
    private void CargarMensajes() throws IOException{
        
        if(!new File(ARCHIVO_CHAT).exists()){
            new File(ARCHIVO_CHAT).createNewFile();
        }
        
        try(RandomAccessFile cargar=new RandomAccessFile(ARCHIVO_CHAT,"r")){
            
            String linea;
            while((linea=cargar.readLine())!=null){
                
                String[] datos=linea.split("::", 3);
                if(datos.length==3){
                    String timestamp=datos[0];
                    String remitente=datos[1];
                    String mensaje=datos[2];
                    AgregarMensajePanel(new MessageChat(mensaje,remitente,timestamp));
                    
                }
                
            }
            
        }catch(IOException e){
            
            JOptionPane.showMessageDialog(null, "ERROR AL CARGAR LOS CHATS");
            
        }
        
    }
    
    
    private void GuardarMensajeArchivoGeneral(MessageChat mensaje) throws IOException {

        try (RandomAccessFile guardar = new RandomAccessFile(ARCHIVO_CHAT, "rw")) {
            guardar.seek(guardar.length());//vamos al final del archivo
            guardar.writeBytes(mensaje.getTimestamp() + "::" + mensaje.getRemitente() + "::" + mensaje.getMensaje() + "\n");

        }catch (IOException e) {

            JOptionPane.showMessageDialog(null, "ERROR AL GUARDAR EL MENSAJE EN EL CHAT GENERAL");

        }

    }
    
    private void GuardarMensajeArchivoIndividual(MessageChat mensaje) throws IOException{
        
        try(RandomAccessFile individual= new RandomAccessFile(archivosHistorial,"rw")){
            
            individual.seek(individual.length());
            individual.writeBytes(mensaje.getTimestamp()+ "::"+mensaje.getRemitente()+ "::"+mensaje.getMensaje()+"\n");
            
        }catch(IOException e){
            
            JOptionPane.showMessageDialog(null, "ERROR AL GUARDAR EL HISTORIAL ");
            
        }
        
    }
    
    private void mostrarHistorial() throws IOException{
        
        JFrame ventanaHistorial=new JFrame("Historal de mensajes de "+usuarioEnSesion);
        ventanaHistorial.setSize(450,650);
        ventanaHistorial.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaHistorial.setLayout(new BorderLayout());
        ventanaHistorial.setLocationRelativeTo(null);
        
        JTextArea AreaHistorial=new JTextArea();
        AreaHistorial.setEditable(false);
        AreaHistorial.setFont(new Font("Monospaced",Font.PLAIN,12));
        JScrollPane ScrollHistorial=new JScrollPane(AreaHistorial);
        ventanaHistorial.add(ScrollHistorial,BorderLayout.CENTER);
        
        if(!archivosHistorial.exists()){
            archivosHistorial.createNewFile();//si no existe se crea
        }
        
        try(RandomAccessFile mostrar=new RandomAccessFile(archivosHistorial,"r")){
            
            String linea;
            while((linea=mostrar.readLine())!=null){
                
                AreaHistorial.append(linea+ "\n");
                
            }
            
            
        }catch(IOException e){
            
            AreaHistorial.setText("No se pudo cargar el historial");
            
        }
        
        ventanaHistorial.setVisible(true);
    }
    
    private void volverAlMenu() throws IOException {
        dispose(); 
        PrincipalM m =new PrincipalM( usuarioEnSesion,archivosUSUARIO);
        m.setVisible(true);
    }

   
}
