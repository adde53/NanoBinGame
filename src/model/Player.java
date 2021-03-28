/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.BLUE;

import javafx.scene.paint.ImagePattern;

/**
 * @author AndreasOlsson
 */
public class Player {
    private int width;
    private int height;

    private double x;
    private double y;
    private double gravity;
    private double velocity;

    private Color color;
    public Image image;

    public Player() {
        this.width = 45;
        this.height = 90;
        this.x = 170;
        this.y = 600 - height;
        this.color = color.RED;
        //this.gravity = (double) 0.6;
        this.velocity = 6;
        this.image = new Image("/nanoBinGreen4.png");
        this.color = null;
    }

    public Player(int diameter, double x, double y, Color color) {
        this.width = diameter;
        this.x = x;
        this.y = y;
        this.color = color;
        this.image = null;
    }

    public Player(int diameter, double x, double y, Image image) {
        this.width = diameter;
        this.x = x;
        this.y = y;
        this.image = image;
        this.color = null;
    }

    /**
     * @return the diameter
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the diameter to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setYaddY(double y) {
        this.y += y;
    }

    public void setXaddX(double x) {
        this.x += x;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setVelocitX(double x) {
        this.x += x;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setVelocityTimes(double velocity) {
        this.velocity *= velocity;
    }

    public double getGravity() {
        return gravity;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void paint(GraphicsContext gc) {
        //gc.setStroke(Color.GREEN);
        if (image == null) {
            gc.setFill(this.color);
            gc.fillRect(getX(), getY(), getWidth(), height);
        }
        if (color == null) {
            gc.setFill(new ImagePattern(image));
            gc.fillRect(getX(), getY(), getWidth(), height);
        }

    }


    public void setHeight(int height) {
        this.y = 600 - height;
        this.height = height;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
