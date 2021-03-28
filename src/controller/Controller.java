/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.WARNING;
import model.GameInterface;
import model.GameState;
import model.Options;
import view.WindowGame;

/**
 *
 * @author AndreasOlsson
 */
public class Controller {
    
    private final WindowGame windowView; // view
    private final GameInterface game; // model

    public Controller(GameInterface game, WindowGame windowView) {
        this.game = game;
        this.windowView = windowView;
    }
    
    public void onOptionSelected(Options mode) {
        String type = null;
                
                switch (mode) {
                    case START:
                        type = "start";
                        if(windowView.getGameState()==GameState.INACTIVE){
                            windowView.resetGame();
                        }
                        windowView.start();
                        //newThread(optionSelected, type);
                        break;
                    case PAUSE:
                        type = "PAUSE";
                        windowView.stop();
                        //newThread(optionSelected, type);
                        break;
                    case QUIT:
                        type = "quit";
                        //newThread(optionSelected, type);
                        break;
                    case HIGHSCORE:
                        type = "highscore";
                        //newThread(optionSelected, type);
                        break;
                    default:
                }
    
    }
}
