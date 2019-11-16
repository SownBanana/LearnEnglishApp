/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author SownBanana
 */
public class DataController {
    
    public boolean writeMp3(InputStream voice, String word) throws FileNotFoundException, IOException{
        File destFile = new File(WordController.getVoiceFolder() + word.replaceAll("\\s+", "") + ".mp3");
        FileOutputStream fos = new FileOutputStream(destFile);
        byte[] a = new byte[256];
        int length;
        while ((length = voice.read(a)) > 0) {            
            fos.write(a, 0, length);
        }
        fos.close();
        return true;
    }
    public boolean writeJpg(InputStream img, String word) throws FileNotFoundException, IOException{
        File destFile = new File(WordController.getImageFolder() + word.replaceAll("\\s+", "") + ".jpg");
        FileOutputStream fos = new FileOutputStream(destFile);
        byte[] a = new byte[1024];
        int length;
        while ((length = img.read(a)) > 0) {            
            fos.write(a, 0, length);
        }
        fos.close();
        return true;
    }
}
