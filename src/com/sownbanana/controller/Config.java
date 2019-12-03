/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.controller;

import static com.sownbanana.controller.WordController.getDataPath;
import static com.sownbanana.controller.WordController.total;
import static com.sownbanana.controller.WordController.words;
import com.sownbanana.model.Word;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SownBanana
 */
public class Config {

    public String gameLevel;
    public String checkLevel;
    public String[] hashtag;
    public String date;

    public boolean readConfigFile() {
        boolean check = true;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(WordController.getConfigPath()));
            String line = reader.readLine();
            String[] output = line.split("#");
            this.gameLevel = output[0];
            this.checkLevel = output[1];
            this.hashtag = output[2].split("@");
            this.date = output[3];
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

    public boolean writeConfigFile() {
        boolean check = false;
        String s = toString();
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

    @Override
    public String toString() {
        return gameLevel + "#" + checkLevel + "#" + WordController.hashtag2String(hashtag) + "#" + date;
    }
    
}
