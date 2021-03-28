/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents a list of statistics
 *
 * @author AndreasOlsson
 */
public class ListOfTopTen {

    private ArrayList<Statistics> stats;

    public ListOfTopTen() {
        stats = new ArrayList<>(10);
    }

    /**
     * Adds a player to the list
     *
     * @param player
     */
    public void addStats(Statistics player) {

        if (stats.size() < 10) {
            stats.add(player);

        } else if (stats.size() == 10) {
            if (checkTopList(player)) {
                stats.remove(stats.size() - 1);
                stats.add(player);
            }
        }
        Collections.sort(stats);
    }

    /**
     * Checks if lates winning players time is better
     * than number ten in top ten list
     *
     * @param player
     * @return
     */
    public boolean checkTopList(Statistics player) {
        //stats.add(player);//[nrOfPlayers] = player;
        if (player.compareTo(stats.get(stats.size() - 1)) == -1 || player.compareTo(stats.get(stats.size() - 1)) == 0) {
            return true;
        }
        return false;
    }

    /**
     * Adds all the stats in top ten list to the list when read from file
     *
     * @param stats
     * @throws IllegalArgumentException
     */
    public void addAllStats(ArrayList<Statistics> stats)
            throws IllegalArgumentException {
        if (stats == null) {
            throw new IllegalArgumentException("Incorrect argument!!!");
        }
        for (int i = 0; i < stats.size(); i++) {
            this.stats.add(stats.get(i));
        }
    }

    /**
     * @return arrylist stats
     */
    public ArrayList<Statistics> getStats() {
        return stats;
    }

    @Override
    public String toString() {
        String info = "\n";
        int temp = 0;
        for (int i = 0; i < stats.size(); i++) {
            temp = i + 1;
            info += temp + ".\t";
            info += stats.get(i).toString();
        }
        return info;
    }
}
