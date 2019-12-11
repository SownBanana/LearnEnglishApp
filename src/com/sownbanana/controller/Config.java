/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author SownBanana
 */
public class Config {

    public static String gameLevel;
    public static String checkLevel;
    public static String[] hashtag;
    public static String date;
    public static boolean isDateFillter;
    public static boolean checkUseAI;

    public static boolean readConfigFile() {
        boolean check = true;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(WordController.getConfigPath()), "UTF-8"));
            String line = reader.readLine();
            String[] output = line.split("#");
            gameLevel = output[0];
            checkLevel = output[1];
            hashtag = output[2].split("@");
            String[] odate = output[3].split("%");
            date = odate[1];
            if(odate[0].equals("disable")){
                isDateFillter = false;
            }
            else{
                isDateFillter = true;
            }
            if(output[4].equals("disable")){
                checkUseAI = false;
            }
            else{
                checkUseAI = true;
            }
            
        } catch (FileNotFoundException e) {
            check = false;
            e.printStackTrace();
            WordController.createConfigFile();
        } catch (IOException e) {
            check = false;
            e.printStackTrace();
        }
        return check;
    }

    public static boolean writeConfigFile() {
        boolean check = false;
        String s = ConfigtoString();
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(WordController.getConfigPath()), "UTF-8"));
            writer.write(s);
            writer.close();
            check = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return check;
    }

    public static String ConfigtoString() {
        String fillterMode;
        String aImode;
        if(isDateFillter) fillterMode = "enable";
        else fillterMode = "disable";
        if(checkUseAI) aImode = "enable";
        else aImode = "disable";
        return gameLevel + "#" + checkLevel + "#" + WordController.hashtag2String(hashtag) + "#" +fillterMode +"%"+ date + "#"+ aImode;
    }
    
}