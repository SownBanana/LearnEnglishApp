/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.controller;

import com.sownbanana.model.Word;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author SownBanana
 */
public class WordController {
    public static int total;
    public static List<Word> words;

    
    public static String getPath(){
        String path = System.getProperty("user.dir") + "///data/text.txt"; 
        return path;
    }
    public static int writeWord(String path){
        int rs = -1;
        if (words != null){
            String s = String.valueOf(total) + "\n";
            for (Word w:
                 words) {
                s += w.toString();
            }
            System.out.println(s);
            try {
                Writer writer = new FileWriter(path);
                writer.write(s);
                writer.close();
                rs = 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    public static void readFile(String path){
        List<Word> wordArrayList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            total = Integer.parseInt(line);
            line = reader.readLine();
            while (line!=null){
                String[] output = line.split("#");
                String[] hashtags = output[8].split("@");
                LocalDate datemdf = LocalDate.parse(output[9]);
                Word word = new Word(Integer.parseInt(output[0]), output[1], output[2], output[3], output[4],
                        output[5], output[6], output[7], hashtags, datemdf);
                wordArrayList.add(word);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        words = wordArrayList;
    }

    public static Word findWord(int id){
        for (Word w:
             words) {
            if (w.getId() == id) return w;
        }
        return null;
    }

    public static Word findWord(String name){
        for (Word w:
             words) {
            if(w.getWord().equals(name)) return w;
        }
        return null;
    }

    public static Word findMean(String mean){
        for (Word w:
                words) {
            if(w.getWord().equals(mean)) return w;
        }
        return null;
    }

    public static Word findHashtag(String hashtag){
        for (Word w:
                words) {
            String[] hl = w.getHashtag();
            for (String h:
                 hl) {
                if (h.equals(hashtag)) return w;
            }
        }
        return null;
    }
    public static List<Word> findHashtag(String... hashtag){
        List<Word> rs = new ArrayList<>();
        for (Word w:
                words) {
            List<String> hl = Arrays.asList(w.getHashtag());
            for (String htg : hashtag) {
                if (hl.contains(htg)) {
                    rs.add(w);
                    break;
                }
            }
        }
        return rs;
    }
    public static List<Word> findHashtagContainAll(String... hashtag){
        List<Word> rs = new ArrayList<>();
        words.forEach((w) -> {
            List<String> hl = Arrays.asList(w.getHashtag());
            if (hl.containsAll(Arrays.asList(hashtag))) {
                rs.add(w);
            }
        });
        return rs;
    }

    public static void addWord(Word word){
        total++;
        word.setId(total);
        words.add(word);
    }

    public static void editWord(int id, String word, String mean, String ipa, String type, String imageURL, String voiceURL, String hint,
                                String[] hashtag, LocalDate dateModified){
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getId() == id){
                words.get(i).setWord(word);
                words.get(i).setMean(mean);
                words.get(i).setIpa(ipa);
                words.get(i).setType(type);
                words.get(i).setImageURL(imageURL);
                words.get(i).setVoiceURL(voiceURL);
                words.get(i).setHint(hint);
                words.get(i).setHashtag(hashtag);
                words.get(i).setDateModified(dateModified);
            }
        }
    }
    public static void deleteWord(int id){
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getId() == id) {
                words.remove(i);
                break;
            }
        }
    }

    public static String hashtag2String(String[] hashtag){
        String rs = "";
        if (hashtag != null){
            for (String hg:
                    hashtag) {
                rs += hg + "@";
            }
        }
        return rs;
    }
}
