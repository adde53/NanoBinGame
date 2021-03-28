/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * @author AndreasOlsson
 */
public class Cigarettes {
    private int width;
    private int height;
    private double x;
    private double y;
    private Color color;
    private double gravity;
    private double velocity;
    public Image image;

    public Cigarettes() {
        this.width = 20;
        this.height = 60;
        Random rand = new Random();
        this.x = rand.nextInt((380 - width));
        ;
        this.y = -500;
        this.color = color.RED;
        this.gravity = (double) 0.6;
        this.velocity = 2.45;
        this.image = new Image("/cigaretteFimps.png");
        this.color = null;
    }

    public Cigarettes(int width, int height, double x, double y, Image image) {
        this.width = width;
        this.height = height;
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

    /**
     * Call this method when painting color or image
     */
    public void paint(GraphicsContext gc) {
        if (image == null) {
            gc.setFill(this.color);
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }
        if (color == null) {
            gc.setFill(new ImagePattern(image));
            gc.fillRect(getX(), getY(), getWidth(), getHeight());
        }

    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }
}
