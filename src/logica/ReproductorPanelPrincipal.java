/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import GUI.PrincipalM;
import javax.swing.*;
import java.awt.*;
import javafx.scene.layout.BorderPane;

public class ReproductorPanelPrincipal extends BorderPane{
    
    public ReproductorPanelPrincipal(String nombreUsuario, PrincipalM menuprincipal){
        
        setTop(new ParteArriba(nombreUsuario,menuprincipal));
        setCenter(new DatosDeCancion());
        setBottom(new ParteInferior());
        
    }
    
    
}

