/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

/**
 * @author AndreasOlsson
 */
public class WindowGame extends VBox {

    /**
     * Declaring all variables
     */
    private Canvas canvas;
    private Pane root;
    private Stage stage;
    private Scene scene;
    private AnimationTimer update;
    private Text poäng;
    private Text nanoBin;
    private DropShadow ds;

    private Image imageBackground;
    private Image pause;
    private Image start;
    private Image score;
    private Image option;

    private String fileName;
    private String credits;

    private GameState gameState;
    private ListOfTopTen stats;
    private Statistics playerEnd;
    private Cigarettes cigarette[];
    private Garbage garbage;
    private Player player;
    private OptionChoices optionChoices;

    private int canvasX;
    private int canvasY;
    private int rightKey;
    private int leftKey;
    private int count;
    private int points;
    private int gameCount;
    private int screenPressed;
    private int level;
    private int amountOfCigarettes;
    private int garbageAmount;
    private int nanoBinChoosen;
    private double initVelocity;
    private double currentVel;

    private boolean ifPaused;
    private boolean backgroundChoosen;
    private boolean changeBackgroundPaused;
    private boolean garbageFallen;
    private boolean gameNB;

    /**
     * Constructors
     */
    public WindowGame(MockGame game) throws IOException, ClassNotFoundException {
        gameNB = false;
        final Controller controller = new Controller(game, this);
        this.init(controller);
    }

    public WindowGame() {
    }

    /**
     * Call this method to initiate the game
     * including initiation of variables and functions
     */
    private void init(Controller controller) throws IOException, ClassNotFoundException {
        this.nanoBinChoosen = 3;
        this.stats = new ListOfTopTen();
        this.fileName = new String();
        this.fileName = "NBGame.ser";
        this.canvasX = 400;
        this.canvasY = 600;
        this.update = new MoveTime();
        this.optionChoices = new OptionChoices();

        File file = new File("NBGame.ser");
        cigarette = new Cigarettes[21];
        backgroundChoosen = true;
        changeBackgroundPaused = true;
        garbageFallen = false;
        ifPaused = true;
        gameNB = true;
        gameState = GameState.ACTIVE;

        credits = "Credits";
        initVelocity = 6;
        currentVel = initVelocity;
        amountOfCigarettes = 3;
        level = 1;
        garbageAmount = 20;
        screenPressed = 0;
        rightKey = 0;
        leftKey = 0;
        gameCount = 0;
        count = 1;
        points = 0;

        if (file.exists()) {
            stats.addAllStats(HighScoreIO.deSerializeFromFile(fileName));
        } else if (!file.exists()) {
            file.createNewFile();
            HighScoreIO.serializeToFile(fileName, stats.getStats());
        }

        for (int i = 0; i < 20; i++) {
            cigarette[i] = new Cigarettes();
        }

        score = new Image("/score.png");
        pause = new Image("/Pausebutton.png");
        start = new Image("/Playbutton.png");
        option = new Image("/Options.png");
        garbage = new Garbage();
        player = new Player();
        root = new Pane();
        canvas = new Canvas(canvasX, canvasY);
        root.getChildren().add(canvas);
        ds = new DropShadow();
        imageBackground = new Image("/clouds.jpg");
        this.getChildren().addAll(root);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        displayButtons(gc);
        displayStartText(gc);
        displayStartText(gc);
        VBox.setVgrow(root, Priority.ALWAYS);
        initMeny(controller);
    }

    /**
     * Call this method to start the game
     */
    public void start() {
        update.start();
    }

    /**
     * Call this method to stop the game
     */
    public void stop() {
        update.stop();
    }

    /**
     * Call this method to handle where mouse press
     * has occurred on menubuttons
     */
    private void initMeny(Controller controller) {
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                checkMousePressed(controller, event);
            }
        });
    }

    /**
     * Call this method to render game
     */
    public class MoveTime extends AnimationTimer {

        @Override
        public void handle(long arg0) {
            if (gameCount < 4) {
                gameCount++;
            }
            GraphicsContext gc = canvas.getGraphicsContext2D();
            displayButtons(gc);
            gc.setFill(Color.WHITESMOKE);
            gc.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 30));
            gc.setEffect(ds);
            gc.fillText("Level " + level, 150, 250, 250);
            gc.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 30));
            gc.setEffect(ds);
            gc.fillText("Level " + level, 150, 250, 250);

            if ((points / garbageAmount == count) || garbageFallen) {
                garbage.setYaddY(garbage.getVelocity());
                garbage.paint(gc);
                checkGarbage();
            }
            for (int i = 0; i < amountOfCigarettes; i++) {
                if (points > 60 && points < 120) {
                    level = 2;
                    player.setVelocity(7.5);
                }
                if (points >= 120) {
                    level = 3;
                    player.setVelocity(9);
                }
                if (gameCount == 2) {
                    checkCigarettes(1);
                    cigarette[1].setYaddY(cigarette[1].getVelocity());
                    cigarette[1].setY(-400);
                    cigarette[1].paint(gc);
                } else if (gameCount == 3) {
                    checkCigarettes(2);
                    cigarette[2].setYaddY(cigarette[2].getVelocity());
                    cigarette[2].setY(-200);
                    cigarette[2].paint(gc);
                } else {
                    checkCigarettes(i + 1);
                    cigarette[i + 1].setYaddY(cigarette[i + 1].getVelocity());
                    cigarette[i + 1].paint(gc);
                }
            }
            player.paint(gc);
            initKeys();
            if (rightKey == 1) {
                if (player.getX() + player.getWidth() >= canvas.getWidth()) {
                    player.setX(canvas.getWidth() - player.getWidth());
                    rightKey = 0;
                    player.setVelocity(0);

                } else if (player.getX() < canvas.getWidth() - player.getWidth()) {
                    player.setXaddX(player.getVelocity());
                    player.setVelocity(currentVel);
                    //player.setVelocity(currentVel);
                }
            }
            if (leftKey == 1) {
                if (player.getX() <= 0) {
                    player.setX(0);
                    player.setVelocity(0);
                    leftKey = 0;
                } else if (player.getX() > 0) {
                    player.setXaddX(-player.getVelocity());
                    player.setVelocity(currentVel);
                }
            }
        }
    }

    /**
     * Call this method enter name for highscore
     * read to file.
     */
    public void stats() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("YOU WIN! ");
        dialog.setHeaderText("You collected " + points + " cigarettes\nEnter your name:");
        dialog.setContentText("Name:");
        String name = dialog.showAndWait().get();
        playerEnd = new Statistics(name, points);
        stats.addStats(playerEnd);

        try {
            HighScoreIO.serializeToFile(fileName, stats.getStats());
        } catch (IOException ex) {
            Logger.getLogger(WindowGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        resetGame();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        displayButtons(gc);
        displayStartText(gc);
        displayStartText(gc);
    }

    /**
     * Call this method handle keys
     */
    public void initKeys() {
        {
            getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {

                    if (event.getCode().equals(KeyCode.SPACE) && gameState != GameState.GAMEOVER) {
                        if (ifPaused) {
                            update.start();
                            ifPaused = false;
                            changeBackgroundPaused = true;
                        } else if (!ifPaused) {

                            //Pause game on space
                            ifPaused = true;
                            changeBackgroundPaused = false;
                            GraphicsContext gc = canvas.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.setFont(Font.font("Verdana", FontWeight.BLACK, 100));
                            gc.setEffect(ds);
                            gc.fillText("GAME IS PAUSED", 65, 350, 260);
                            update.stop();
                        }

                        //update.start();
                    }
                    if (event.getCode().equals(KeyCode.LEFT)) {
                        player.setX(player.getX());
                        player.setXaddX(0);
                        if (leftKey == 0) {
                            leftKey = 1;
                        }
                    }
                    if (event.getCode().equals(KeyCode.RIGHT)) {
                        if (rightKey == 0) {
                            rightKey = 1;
                        }
                    }

                }
            });
            getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {

                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.RIGHT)) {
                        rightKey = 0;
                        player.setXaddX(player.getVelocity());
                    }
                    if (event.getCode().equals(KeyCode.LEFT)) {
                        leftKey = 0;
                        player.setXaddX(-player.getVelocity());
                    }
                }
            });
        }

    }

    /**
     * Call this method to check cigarettes position
     * and amount of cigarettes collected
     */
    public void checkCigarettes(int i) {
        if (cigarette[i].getY() + cigarette[i].getHeight() > player.getY()
                &&
                player.getX() < cigarette[i].getX() + cigarette[i].getWidth()
                &&
                player.getX() + player.getWidth() > cigarette[i].getX()
        ) {
            Random rand = new Random();
            Random rand2 = new Random();
            int newX = rand.nextInt(400 - cigarette[i].getWidth());
            double newVeclocity = 0;
            if (level == 1)
                newVeclocity = rand.nextDouble() + rand2.nextDouble() + 1.8;
            else if (level == 2)
                newVeclocity = rand.nextDouble() + rand2.nextDouble() + level;
            else
                newVeclocity = rand.nextDouble() + rand2.nextDouble() + level;
            System.out.println("Hastighet: " + newVeclocity);
            cigarette[i] = new Cigarettes();
            cigarette[i].setY(-100);
            cigarette[i].setX(newX);
            cigarette[i].setVelocity(newVeclocity);

            points++;
            if ((points / garbageAmount == count) && !garbageFallen) {
                garbage = new Garbage();
            }

            System.out.println("Poäng:" + points);
        }

        if (cigarette[i].getY() > canvas.getHeight()) {
            /*for (int s = 0; s < 20; s++) {
                cigarette[s].setY(-100);
            }*/
            gameState = GameState.GAMEOVER;
            update.stop();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFill(Color.WHITESMOKE);
            gc.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 30));
            gc.fillText("Tryck på skärmen för\natt lägga till highscore!", 65, 280, 300);
            gc.setEffect(ds);
            gc.setFill(Color.WHITESMOKE);
            gc.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 30));
            gc.fillText("Tryck på skärmen för\natt lägga till highscore!", 65, 280, 300);
            gc.setEffect(ds);
        }
    }

    /**
     * Call this method display all graphic
     * including buttons, background etc.
     */
    public void displayButtons(GraphicsContext gc) {
        gc.setFill(new ImagePattern(imageBackground));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITESMOKE);
        gc.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 50));
        gc.setEffect(ds);
        gc.fillText("NANO-BIN", 65, 380, 300);
        gc.setFill(Color.WHITESMOKE);
        gc.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 50));
        gc.setEffect(ds);
        gc.fillText("NANO-BIN", 65, 380, 300);
        gc.setFill(Color.WHITESMOKE);
        gc.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 50));
        gc.setEffect(ds);

        if (points < 10)
            gc.fillText("" + points, 175, 155);
        else if (points < 100)
            gc.fillText("" + points, 165, 155);
        else if (points < 1000)
            gc.fillText("" + points, 155, 155);
        gc.setFill(new ImagePattern(pause));
        gc.setEffect(ds);
        gc.fillRect(canvas.getWidth() - 40, 0, 40, 40);

        gc.setFill(new ImagePattern(option));
        gc.setEffect(ds);
        gc.fillRect(40, 0, 40, 40);

        gc.setFill(new ImagePattern(start));
        gc.setEffect(ds);
        gc.fillRect(0, 0, 40, 40);

        gc.setFill(new ImagePattern(score));
        gc.setEffect(ds);
        gc.fillRect(canvas.getWidth() - 100, 0, 60, 40);

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Verdana", FontWeight.BLACK, 25));
        gc.fillText(credits, 140, 30, 260);

    }

    /**
     * Call this method to check if user catches
     * garbage or not
     */
    public void checkGarbage() {
        if (garbage.getY() + garbage.getHeight() > player.getY()
                &&
                player.getX() < garbage.getX() + garbage.getWidth()
                &&
                player.getX() + player.getWidth() > garbage.getX()
        ) {
            garbageFallen = false;
            points++;
            garbage.setY(0 - garbage.getHeight());
            garbage.setVelocity(0);
            currentVel = currentVel + 0.25;
            count++;
            if (amountOfCigarettes < 10) {
                amountOfCigarettes = amountOfCigarettes + 1;
                System.out.println("amountOfCigarettes: " + amountOfCigarettes);
            }
        } else if (garbage.getY() > canvas.getHeight()) {
            garbageFallen = false;
            garbage.setY(0 - garbage.getHeight());// = new Garbage();
            garbage.setVelocity(0);
            count++;
            currentVel = currentVel - 0.25;
            if (amountOfCigarettes < 10) {
                amountOfCigarettes = amountOfCigarettes + 1;
                System.out.println("amountOfCigarettes: " + amountOfCigarettes);
            }
        } else {
            garbageFallen = true;
        }
        System.out.println("PlayerVelocity: " + currentVel);
    }

    /**
     * Call this method to reset the game
     */
    public void resetGame() {
        cigarette = new Cigarettes[21];
        for (int i = 0; i < 20; i++) {
            cigarette[i] = new Cigarettes();
            if (i < amountOfCigarettes) {
                cigarette[i].setY(-(100));
            }
        }
        rightKey = 0;
        leftKey = 0;
        gameCount = 0;
        currentVel = 6;
        player.setVelocity(initVelocity);
        count = 1;
        points = 0;
        level = 1;
        amountOfCigarettes = 3;
        gameState = GameState.ACTIVE;
        screenPressed = 0;
        player = new Player();
        if (this.nanoBinChoosen == 1) {
            player.setImage(new Image("/nanoBinBlack.png"));
        } else if (this.nanoBinChoosen == 2) {
            player.setImage(new Image("/silver.png"));
        } else if (this.nanoBinChoosen == 3) {
            player.setImage(new Image("/nanoBinGreen4.png"));
        } else if (this.nanoBinChoosen == 4) {
            player.setImage(new Image("/gold.png"));
        }
        ifPaused = true;
        garbage.setY(0 - garbage.getHeight());
        garbage.setVelocity(0);
        //init(controller);
    }

    /**
     * Call this method to get gamestate
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Call this method to check where mouse click
     * occurred
     */
    public void checkMousePressed(Controller controller, MouseEvent event) {
        /*
                Staring game
                 */

        if ((int) (event.getX()) < 40 && (int) (event.getY()) < 40) {
            if (gameState == GameState.GAMEOVER) {
                resetGame();
                Options optionsMode = Options.START;
                controller.onOptionSelected(optionsMode);
                ifPaused = false;

            } else {
                Options optionsMode = Options.START;
                ifPaused = false;
                changeBackgroundPaused = true;
                controller.onOptionSelected(optionsMode);
            }
        }

                /*
                Pausing game
                 */

        if ((int) (event.getX()) > canvasX - 40 && (int) (event.getY()) < 40) {
            if (!ifPaused) {
                Options optionsMode = Options.PAUSE;// = searchModeBox.getValue();
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.BLACK);
                gc.setFont(Font.font("Verdana", FontWeight.BLACK, 100));
                gc.setEffect(ds);
                gc.fillText("GAME PAUSED", 65, 350, 260);
                controller.onOptionSelected(optionsMode);
                ifPaused = true;
                changeBackgroundPaused = false;
            }
        }

                /*
                Adding score to scoreboard
                 */

        if (screenPressed == 0) {
            if ((int) (event.getX()) > (0) && (int) (event.getX()) < (canvasX)
                    && (int) (event.getY()) < canvasY
                    && (int) (event.getY()) > 40) {
                if (gameState == GameState.GAMEOVER) {
                    ifPaused = true;
                    screenPressed = 1;
                    stats();
                }
            }
        }

                /*
                Showing scoreboard
                 */

        if (((int) (event.getX()) > canvasX - 100 && (int) (event.getX()) < canvasX - 60 && (int) (event.getY()) < 40)
                && ifPaused) {
            String message = new String();
            message = "\tName\t\tScore" + stats.toString();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
            alert.setHeaderText("Scoreboard");
            alert.showAndWait();
        }
        /*
        Showing credits
         */
        if (((int) (event.getX()) > 140 && (int) (event.getX()) < canvasX - 150 && (int) (event.getY()) < 40)
                && ifPaused) {
            String message = new String();
            message = "All the background pictures were found at https://pixabay.com/sv/\n" +
                    "All the pictures of Nano-bin were found at http://nano-bin.se/sv/\n" +
                    "All the icons were found at https://icons8.com/\n" +
                    "\nDeveloper behind the game is Andreas Olsson\n" +
                    "\n\tGame was finished 2020-05-18";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
            alert.setHeaderText("\t\t\t\t\tCredits");
            alert.showAndWait();
        }

                /*
                Option button
                 */

        if (((int) (event.getX()) > 40 && (int) (event.getX()) < 80 && (int) (event.getY()) < 40)
                && ifPaused && (gameState == GameState.ACTIVE) && changeBackgroundPaused) {
            //OptionChoices option = new OptionChoices();
            GraphicsContext gc = canvas.getGraphicsContext2D();
            System.out.println("nanoBinChoosen: " + nanoBinChoosen);
            optionChoices.openWindow(backgroundChoosen, nanoBinChoosen);
            nanoBinChoosen = optionChoices.isNanoBinChoosen();
            backgroundChoosen = optionChoices.isBackgroundChoosen();
            System.out.println("Tja: " + nanoBinChoosen);
            if (this.backgroundChoosen) {
                imageBackground = new Image("/clouds.jpg");
                //backgroundChoosen = false;
            } else {
                imageBackground = new Image("/grass.jpg");
                //backgroundChoosen = true;
            }

            //this.nanoBinChoosen = optionChoices.isNanoBinChoosen();
            if (this.nanoBinChoosen == 1) {
                player.setImage(new Image("/nanoBinBlack.png"));
            } else if (this.nanoBinChoosen == 2) {
                player.setImage(new Image("/silver.png"));
            } else if (this.nanoBinChoosen == 3) {
                player.setImage(new Image("/nanoBinGreen4.png"));
            } else if (this.nanoBinChoosen == 4) {
                player.setImage(new Image("/gold.png"));
            }
            displayButtons(gc);
            displayStartText(gc);
            displayStartText(gc);
            //System.out.println("Tja");
        }
    }

    /**
     * Call this method to display how to start
     * a new game
     */
    public void displayStartText(GraphicsContext gc) {
        gc.setFill(Color.WHITESMOKE);
        gc.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 30));
        gc.fillText(" Tryck på play för\n     att starta!", 65, 280, 300);
        gc.setEffect(ds);
    }

}
