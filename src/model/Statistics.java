/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.io.Serializable;

/**
 * This class represents statistics for a winning player
 *
 * @author AndreasOlsson
 */
public class Statistics implements Comparable<Statistics>, Serializable {

    private String name;
    private int points;

    public Statistics() {
        name = new String();
        points = 0;
    }

    /**
     * @param name
     * @param points
     */
    public Statistics(String name, int points) {
        this.name = name;
        this.points = points;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the winningTime
     */
    public int getWinningPoints() {
        return points;
    }

    /**
     * @param points the winningTime to set
     */
    public void setPoints(int points) {
        this.points = points;
    }

    //@Override
    public String toString() {
        String info = "" + name + "\t\t" + points + "\n";
        return info;
    }

    @Override
    public int compareTo(Statistics player) {
        if (points < player.points) {
            return 1;
        } else if (points > player.points) {
            return -1;
        } else
            return 0;
    }


}
