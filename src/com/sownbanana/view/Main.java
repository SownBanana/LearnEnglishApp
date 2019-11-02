/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.view;

import com.sownbanana.controller.WordController;
import com.sownbanana.model.Word;
import java.awt.FlowLayout;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author SownBanana
 */
public class Main {
    public static void main(String[] args) {
        String path = WordController.getDataPath();
        System.out.println(WordController.checkDataFolder());
        System.out.println(WordController.readFile(path));
        System.out.println(WordController.words);
        
        HomeUI ui = new HomeUI();
        ui.show();
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setLocationRelativeTo(null);
    }
}
