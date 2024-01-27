/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.*;


/**
 *
 * @author Pocitac
 * 
 * Kazdy objekt reprezentuje jeden zvuk ktery se muze poustet.
 * Pracuje pouze s .wav soubory, nikoliv mp3
 */
public class SoundFactory {
    
    private AudioInputStream audioInputStream;
    private Clip clip;
    
    public SoundFactory(String name) {
        /*
        try {
            String pathToJarLocation = SoundFactory.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI().getPath() + "sounds/";
            System.out.println("Path to jar location = " + pathToJarLocation);
            //file = new File(pathToJarLocation + "\\"+name+".wav");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        */
        ClassLoader classLoader = SoundFactory.class.getClassLoader();
        URL urlAudioFile = classLoader.getResource("sounds/"+name+".wav");


        System.out.println("Path to resource audio = " + urlAudioFile.getPath());


        //Parse path to correct format
        String pathAudio = urlAudioFile.getPath() + "/" + name + ".wav";
        pathAudio = pathAudio.substring(1).replace("/","\\");
        System.out.println("new Path = " + pathAudio);


        try {
            audioInputStream = AudioSystem.getAudioInputStream(urlAudioFile);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void setInfiniteLooping() {
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }
    
    public void start() {
        try {
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void stop() {
        clip.stop();
    }
}
