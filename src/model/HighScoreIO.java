/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AndreasOlsson
 */
public class HighScoreIO {

    /**
     * Calls this method after a win is made, to serialize stats to file.
     *
     * @param filename
     * @param stats
     * @throws IOException
     */
    public static void serializeToFile(String filename, ArrayList<Statistics> stats) throws IOException {

        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(
                    new FileOutputStream(filename));
            out.writeObject(stats);
        } finally {
            try {
                if (out != null) out.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Call this method at startup of the application, to deserialize stats
     * from file the specified file.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Statistics> deSerializeFromFile(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(
                    new FileInputStream(filename));
            // readObject returns a reference of type Object, hence the down-cast
            ArrayList<Statistics> stats = (ArrayList<Statistics>) in.readObject();

            return stats;
        } finally {
            try {
                if (in != null) in.close();
            } catch (Exception e) {
            }
        }
    }

}
