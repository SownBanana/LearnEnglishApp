/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.controller;

import com.sun.corba.se.impl.io.IIOPInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SownBanana
 */
public class DataController {

    public void writeMp3(InputStream voice, String word) {
        Thread thread = new Thread(() -> {
            FileOutputStream fos = null;
            try {
                File destFile = new File(WordController.getVoiceFolder() + word.replaceAll("\\s+", "") + ".mp3");
                fos = new FileOutputStream(destFile);
                byte[] a = new byte[256];
                int length;
                while ((length = voice.read(a)) > 0) {
                    fos.write(a, 0, length);
                }
                fos.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        thread.setDaemon(false);
        thread.start();
//        File destFile = new File(WordController.getVoiceFolder() + word.replaceAll("\\s+", "") + ".mp3");
//        FileOutputStream fos = new FileOutputStream(destFile);
//        byte[] a = new byte[256];
//        int length;
//        while ((length = voice.read(a)) > 0) {            
//            fos.write(a, 0, length);
//        }
//        fos.close();

    }

    public void writeJpg(InputStream img, String word) {
        Thread thread = new Thread(() -> {
            FileOutputStream fos = null;
            try {
                File destFile = new File(WordController.getImageFolder() + word.replaceAll("\\s+", "") + ".jpg");
                fos = new FileOutputStream(destFile);
                byte[] a = new byte[1024];
                int length;
                while ((length = img.read(a)) > 0) {
                    fos.write(a, 0, length);
                }
                fos.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fos.close();
                } catch (IOException ex) {
                    Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        thread.setDaemon(false);
        thread.start();
//<editor-fold defaultstate="collapsed" desc="write without thread">
//        FileOutputStream fos = null;
//        try {
//            File destFile = new File(WordController.getImageFolder() + word.replaceAll("\\s+", "") + ".jpg");
//            fos = new FileOutputStream(destFile);
//            byte[] a = new byte[1024];
//            int length;
//            while ((length = img.read(a)) > 0) {
//                fos.write(a, 0, length);
//            }   fos.close();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                fos.close();
//            } catch (IOException ex) {
//                Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

//</editor-fold>   
    }

    public void writeJpg(File image, String word) {
        try {
            InputStream img = new FileInputStream(image);
            writeJpg(img, word);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
