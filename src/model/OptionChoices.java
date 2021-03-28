package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import view.WindowGame;

public class OptionChoices extends Parent {
    private WindowGame background;
    private boolean backgroundChoosen;
    private int nanoBinChoosen;
    private Canvas canvas;
    private Canvas canvasNb;
    ImagePattern pattern;
    ImagePattern patternNb;
    DropShadow ds;

    public OptionChoices() {
    }

    public void openWindow(boolean backgroundChoosen, int nanoBinChoosen) {

        this.nanoBinChoosen = nanoBinChoosen;
        this.backgroundChoosen = backgroundChoosen;
        Pane root = new Pane();
        Button button = new Button("Change background");
        button.setLayoutY(37.5);
        button.setLayoutX(35);

        Button buttonPlayer = new Button("Change Nano-bin");
        buttonPlayer.setLayoutY(72.5);
        buttonPlayer.setLayoutX(42.5);
        canvas = new Canvas(200, 300);
        root.getChildren().add(canvas);
        root.getChildren().add(button);
        root.getChildren().add(buttonPlayer);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        //gc.setFill(new ImagePattern(new Image("/clouds.jpg")));
        //gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //GraphicsContext gcNb = canvasNb.getGraphicsContext2D();
        //patternNb = new ImagePattern(new Image("/NanoBinGreen4.png"));
        Scene scene = new Scene(root, 190, 290);
        if (this.backgroundChoosen) {
            pattern = new ImagePattern(new Image("/clouds.jpg"));
        } else {
            pattern = new ImagePattern(new Image("/grass.jpg"));
        }
        gc.setFill(pattern);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        /*gc.setFill(Color.WHITESMOKE);
        gc.fillRect(45, 2, 100, 25);*/
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Verdana", FontWeight.BLACK, 20));
        //gc.setEffect(ds);
        gc.fillText("Settings", 50, 25, 100);
        //scene.setFill(pattern);

        if (this.nanoBinChoosen == 1) {
            patternNb = new ImagePattern(new Image("/nanoBinBlack.png"));
        } else if (this.nanoBinChoosen == 2) {
            patternNb = new ImagePattern(new Image("/silver.png"));
        } else if (this.nanoBinChoosen == 3) {
            patternNb = new ImagePattern(new Image("/nanoBinGreen4.png"));
        } else if (this.nanoBinChoosen == 4) {
            patternNb = new ImagePattern(new Image("/gold.png"));
        }
        gc.setFill(patternNb);
        gc.fillRect(80, 240, 30, 60);

        Stage secondStage = new Stage();
        secondStage.setScene(scene);
        secondStage.setResizable(false);
        //initMeny(scene);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                changeBackground(gc);
            }
        };
        button.setOnAction(event);

        EventHandler<ActionEvent> eventChangeNb = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                changeNanoBin(gc);
            }
        };
        buttonPlayer.setOnAction(eventChangeNb);

        secondStage.showAndWait();
    }

    public void changeBackground(GraphicsContext gc) {
        System.out.println("Boolean: " + backgroundChoosen);
        if (this.backgroundChoosen) {
            this.backgroundChoosen = false;
            pattern = new ImagePattern(new Image("/grass.jpg"));
            nanoBinBackground(gc, patternNb, pattern);
        } else {
            this.backgroundChoosen = true;
            pattern = new ImagePattern(new Image("/clouds.jpg"));
            nanoBinBackground(gc, patternNb, pattern);

        }
    }

    public void changeNanoBin(GraphicsContext gc) {
        System.out.println("Boolean: " + this.nanoBinChoosen);
        if (this.nanoBinChoosen == 1) {
            this.nanoBinChoosen = 2;
            patternNb = new ImagePattern(new Image("/silver.png"));
            nanoBinBackground(gc, patternNb, pattern);
        } else if (this.nanoBinChoosen == 2) {
            this.nanoBinChoosen = 3;
            patternNb = new ImagePattern(new Image("/nanoBinGreen4.png"));
            nanoBinBackground(gc, patternNb, pattern);
        } else if (this.nanoBinChoosen == 3) {
            this.nanoBinChoosen = 4;
            patternNb = new ImagePattern(new Image("/gold.png"));
            nanoBinBackground(gc, patternNb, pattern);
        } else {
            this.nanoBinChoosen = 1;
            patternNb = new ImagePattern(new Image("/nanoBinBlack.png"));
            nanoBinBackground(gc, patternNb, pattern);
        }
    }

    public void nanoBinBackground(GraphicsContext gc, ImagePattern patternNb, ImagePattern pattern) {
        canvas.getGraphicsContext2D();
        //gc.setFill(new ImagePattern(new Image("/clouds.jpg")));
        //gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(pattern);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(patternNb);
        gc.fillRect(80, 240, 30, 60);
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(45, 2, 100, 25);
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 20));
        gc.setEffect(ds);
        gc.fillText("Settings", 52.5, 20, 100);
    }

    public boolean isBackgroundChoosen() {
        return this.backgroundChoosen;
    }

    public int isNanoBinChoosen() {
        return this.nanoBinChoosen;
    }

}
