/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kachny;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pocitac
 */
public class FileManager {

    File scoreFile;

    private static FileManager filemanager = new FileManager();

    private FileManager() {
        File kachnyDir = new File(System.getenv("APPDATA") + "\\Kachny");
        if (!kachnyDir.exists()) {
            kachnyDir.mkdir();
        }
        scoreFile = new File(System.getenv("APPDATA") + "\\Kachny\\Score.txt");
        if (!scoreFile.exists()) {
            try {
                scoreFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public static FileManager getInstance() {
        return filemanager;
    }

    public void writeToFile(String mesage) {
        try {
            //Vycisteni souboru
            PrintWriter pw = new PrintWriter(scoreFile);
            pw.write("");
            pw.close();
            //---------------
            //Zapis
            FileWriter fw = new FileWriter(scoreFile);
            fw.write(mesage);
            fw.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String readFromFile() {
        FileReader fr;
        try {
            fr = new FileReader(scoreFile);
            char[] text = new char[10];
            fr.read(text);
            if (String.copyValueOf(text).trim().equals("")) {
                return "0".trim();
            }
            return String.copyValueOf(text).trim();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null; //Stane se pokud chceme ziskat highscore poprve

    }

}
