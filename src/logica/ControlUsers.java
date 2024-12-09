/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author royum
 */
public final class ControlUsers implements InterfazControlUsuarios {

    private ArrayList<Users> usuarios; //arhivo binario
    private final String Carpetaraiz="UsuariosGestion";
    private final String archivoUsuarios = "usuarios.dat";//este son archivos temporales que se generan para registrar informacion

    public ControlUsers() {
        this.usuarios = new ArrayList<>();
        new File(Carpetaraiz).mkdir();
        CargarUsuarios();
        System.out.println("usaurio cargados"+usuarios.size());
    }

    @Override
    public boolean RegistroUsuario(String nombre, String password, boolean esAdmin) {

        for (Users usuario : usuarios) {

            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                JOptionPane.showMessageDialog(null, "El usuario ya existe", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        }

        try {
            Users nuevoUsuario;
            if(esAdmin){
                nuevoUsuario=new Administrador(nombre,password);
            }else{
                nuevoUsuario=new Users(nombre,password,false);
            }
            
            usuarios.add(nuevoUsuario);
            GuardarUsuarios();
            
            File CarpetaUsuario=new File(Carpetaraiz,nombre);
            if(!CarpetaUsuario.exists()){
                CarpetaUsuario.mkdir(); 
                new File(CarpetaUsuario,"MisDatos").mkdir();
                new File(CarpetaUsuario,"Juegos").mkdir();
                new File(CarpetaUsuario,"Musica").mkdir();
                new File(CarpetaUsuario,"MiChatHistorial").mkdir();
            }
            
            //archivo binario para los datos del usuario
            File archivosDatos=new File(CarpetaUsuario,"MisDatos"+File.separator+nombre+".dat");
            try(ObjectOutputStream Salida=new ObjectOutputStream(new FileOutputStream(archivosDatos))){
                
                Salida.writeObject(new Users(nombre,password,esAdmin));
                
            }
            
            //archibo binario para el historial del chat del usuario
            File archivoChat=new File(CarpetaUsuario,"MiChatHistorial"+File.separator+nombre+"_historial.dat");
            try(ObjectOutputStream salidaChat=new ObjectOutputStream(new FileOutputStream(archivoChat))){
                
                salidaChat.writeObject(new ArrayList<String>());//se inicia con un archivo vacio
                
            }
            
            JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente", "EXITO", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al crear las carpetas del nuevoUsuario " + e.getMessage());
            return false;
        }

    }

    @Override
    public void GuardarUsuarios() {
        //escirbir los datos de un objeto serializados binarios
        try (ObjectOutputStream datos = new ObjectOutputStream(new FileOutputStream(archivoUsuarios))) {
            datos.writeObject(usuarios);//aqui se guarda la lista
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los usuarios " + e.getMessage());
        }   

    }

    public boolean ValidarCredenciales(String nombre, String password) {

        for (Users usuario : usuarios) {
            if (usuario.getNombre().equals(nombre) && usuario.getPassword().equals(password)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public void CargarUsuarios() {

        File archivo = new File(archivoUsuarios);

        if(archivo.exists()){
            try (ObjectInputStream leer = new ObjectInputStream(new FileInputStream(archivoUsuarios))) {
                usuarios = (ArrayList<Users>) leer.readObject();
                
                for (Users usuario : usuarios) {
                    if(usuario instanceof Administrador){
                        
                        System.out.println("- " + usuario.getNombre() + (usuario.EsAdmin() ? " (Admin)" : ""));
                        
                    }else{
                        System.out.println("cargado usuario normal"+usuario.getNombre());
                    }
                }
                
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar los usuarios " + e.getMessage());
            }
        } else {
            usuarios = new ArrayList<>();//si no existe el archiovo, se inicializa la lsita
            System.out.println("No se encontró el archivo de usuarios. Iniciando con lista vacía.");
        }

    }

   

    public Users ObtenerUsuario(String nombre) {

        for (Users usu : usuarios) {

            if (usu.getNombre().equalsIgnoreCase(nombre)) {
                return usu;
            }

        }
        return null;

    }

    public boolean esAdmin(String nombreUsuario){
        Users usuario = ObtenerUsuario(nombreUsuario);

        if(usuario == null){
            System.out.println("Usuario no encontrado: " + nombreUsuario);
            return false;
        }
        boolean esAdmin = usuario instanceof Administrador;
        System.out.println("Usuario encontrado: " + usuario.getNombre() + " - esAdmin: " + usuario.EsAdmin());
        return esAdmin;
    }
    
    public Users LeerDatosUsuario(String NombreUsuario){
        
        File archivosDatos = new File(Carpetaraiz + File.separator + NombreUsuario + File.separator + "MisDatos" + File.separator + NombreUsuario + ".dat");

        if(!archivosDatos.exists()){
            System.out.println("el archivo  del usuario no existe: " + archivosDatos.getAbsolutePath());
            return null;

        }

        try(ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivosDatos))){
            return (Users) entrada.readObject();
        }catch(IOException | ClassNotFoundException e){
            System.out.println("Error al leer los datos del usuario: " + e.getMessage());
            return null;
        }

    }

    public ArrayList<Users> getUsuarios() {
        return usuarios;
    }

}
