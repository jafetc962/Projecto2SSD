/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * s
 *
 * @author royum
 */
public class MusicaReprod extends Application {

    private static Stage stage;

    @Override 
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        //Scene escena = new Scene(new VentanaPrincipal(), 850, 750);
        //stage.setScene(escena);
        stage.show();

    }

    
    public static void main(String[] args) {

        launch(args);

    }

    public static Stage getStage() {

        return stage;

    }

}
