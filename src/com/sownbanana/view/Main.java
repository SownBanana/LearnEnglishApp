/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.view;

import com.sownbanana.controller.Config;
import com.sownbanana.controller.WordController;
import javax.swing.JFrame;

/**
 *
 * @author SownBanana
 */
public class Main {

    public static void main(String[] args) {
        String path = WordController.getDataPath();
        WordController.checkDataFolder();
        System.out.println(WordController.checkConfigFolder());
        System.out.println(WordController.readFile(path));
        System.out.println(WordController.words);
        Config.readConfigFile();
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {

        } catch (InstantiationException ex) {

        } catch (IllegalAccessException ex) {

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {

        }
        //</editor-fold>
        /* Create and display the form */

        HomeUI ui = new HomeUI();
        ui.show();
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setLocationRelativeTo(null);

    }
}
