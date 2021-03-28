/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamenbclone;

import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MockGame;
import view.WindowGame;

/**
 *
 * @author AndreasOlsson
 */
public class GameNBClone extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException, ClassNotFoundException{

        MockGame game = new MockGame();

        WindowGame root = new WindowGame(game);

        Scene scene = new Scene(root, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
         
    }

    public static void main(String[] args){
        launch(args);
    }
    
}
