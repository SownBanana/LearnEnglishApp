/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 *
 * @author SownBanana
 */
public class Synthesizer {

    private static final String API_KEY = "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw";
    private static final String GOOGLE_TTS_URL = "https://www.google.com/speech-api/v2/synthesize?enc=mpeg&client=chromium&key=" + API_KEY + "&lang=en-us&text=";

    public Synthesizer() {
    }

    public InputStream getVoiceInputStream(String word) {
//        Thread thread = new Thread(() -> {
            InputStream is = null;
            String text = word;
            try {
                text = URLEncoder.encode(word, "UTF-8");
                String getVoiceURL = GOOGLE_TTS_URL + text;
                URL url = new URL(getVoiceURL);
                URLConnection uRLConnection = url.openConnection();
                uRLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:2.0) Gecko/20100101 Firefox/4.0");
                is = uRLConnection.getInputStream();
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
//            return getVoiceInputStream(word, 1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return is;          
//        });
        
    }

    public InputStream getVoiceInputStream(String word, int count) {
        
        InputStream is = null;
        String text = word;
        try {
            text = URLEncoder.encode(word, "UTF-8");

            String getVoiceURL = GOOGLE_TTS_URL + text;
            URL url = new URL(getVoiceURL);
            URLConnection uRLConnection = url.openConnection();
            uRLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:2.0) Gecko/20100101 Firefox/4.0");
            is = uRLConnection.getInputStream();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            if (count <= 5) {
                return getVoiceInputStream(word, count + 1);
            } else {
                System.out.println("Không có mạng");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return is;
    }

    public void playSound(String filePath) {
//<editor-fold defaultstate="collapsed" desc="voice without thread">
//        try {
//            Player player = new Player(new FileInputStream(new File(filePath)));
//            player.play();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//</editor-fold>
        Thread thread = new Thread(() -> {
            try {

                AdvancedPlayer player = new AdvancedPlayer(new FileInputStream(new File(filePath)));
                player.play();

            } catch (IOException | JavaLayerException e) {

                e.printStackTrace(); 

            }
        });

        thread.setDaemon(false);

        thread.start();
    }

    public void playSound(InputStream voice) {
//<editor-fold defaultstate="collapsed" desc="voice without thread">
//        try {
//            Player player = new Player(voice);
//            player.play();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return voice;
//</editor-fold>
        Thread thread = new Thread(() -> {
            try {

                AdvancedPlayer player = new AdvancedPlayer(voice);
                player.play();

            } catch (JavaLayerException e) {

                e.printStackTrace(); 

            }
        });

        thread.setDaemon(false);

        thread.start();
    }

    public void text2Speech(String word) {
        playSound(getVoiceInputStream(word));
    }

    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        String a = "test this text";
        Synthesizer synthesizer = new Synthesizer();
//        DataController dataController = new DataController();
//        synthesizer.text2Speech(a);
//        dataController.writeMp3(synthesizer.getVoiceInputStream(a), a);
        synthesizer.playSound(synthesizer.getVoiceInputStream(a));
    }
}
