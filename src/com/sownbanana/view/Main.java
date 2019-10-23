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
//        int id = 1;
//        String word = "one";
//        String mean = "một";
//        String ipa = "oăn";
//        String tpye = "Noun";
//        String imageURL = "image url 1";
//        String voiceURL = "voice url 1";
//        String hint = "cái cơ bản vl";
//        String[] hashtag = {"test", "basic", "number"};
//        LocalDate date = LocalDate.now();
//
//        int id2 = 2;
//        String word2 = "two";
//        String mean2 = "hai";
//        String ipa2 = "tu";
//        String tpye2 = "Noun";
//        String imageURL2 = "image url 2";
//        String voiceURL2 = "voice url 2";
//        String hint2 = "cái cơ bản";
//        String[] hashtag2 = {"test2", "basic", "number"};
//        LocalDate date2 = LocalDate.now();
//
//        int id3 = 3;
//        String word3 = "three";
//        String mean3 = "ba";
//        String ipa3 = "thờ ri";
//        String tpye3 = "Noun";
//        String imageURL3 = "image url 3";
//        String voiceURL3 = "voice url 3";
//        String hint3 = "cái cơ bản 3";
//        String[] hashtag3 = {"test3", "notbasic", "number"};
//        LocalDate date3 = LocalDate.now();
//
//        Word thisWord = new Word(id, word, mean, ipa, tpye, imageURL, voiceURL, hint, hashtag, date);
//        Word thisWord2 = new Word(id2, word2, mean2, ipa2, tpye, imageURL2, voiceURL2, hint2, hashtag2, date2);
//        Word thisWord3 = new Word(id3, word3, mean3, ipa3,tpye, imageURL3, voiceURL3, hint3, hashtag3, date3);
//
//        List<Word> words = new ArrayList<>();
//        words.add(thisWord);
//        words.add(thisWord2);
//        words.add(thisWord3);
////
//        WordController.words = words;
//
        String path = System.getProperty("user.dir") + "///data/text.txt";      //lấy path hiện tại
//        WordController.writeWord(path);
        WordController.readFile(path);

        System.out.println(WordController.words);
////        WordController.editWord(2, "two", "hai", "tu","ảnh","anh mỹ", "V", new String[]{"basic", "number", "edited"},LocalDate.now());
////        WordController.deleteWord(2);
//        WordController.addWord(thisWord2);
//        WordController.writeWord();
        System.out.println(WordController.findHashtagContainAll("number", "basic"));
//        Path path = new Path();

        
        HomeUI ui = new HomeUI();
        ui.show();
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ui.setLocationRelativeTo(null);
    }
}
