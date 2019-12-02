/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.controller;

import com.sownbanana.model.Word;
import com.sownbanana.view.AddWUI;
import java.awt.Color;
import java.awt.image.BufferedImage;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class WordController {

    public static int total;
    public static List<Word> words;
    public Synthesizer synthesizer = new Synthesizer();

    public static String getPath() {
        return System.getProperty("user.dir");
    }

    public static String getPath(String name) {
        return System.getProperty("user.dir") + name;
    }

    public static String getDataPath() {
        String path = System.getProperty("user.dir") + "\\data\\text.txt";
        return path;
    }

    public static String getDictPath() {
        String path = System.getProperty("user.dir") + "\\data\\dict\\wordsapi_sample.json.txt";
        return path;
    }

    public static String getConfigPath() {
        String path = System.getProperty("user.dir") + "\\data\\cfg\\config.txt";
        return path;
    }
    
    public static String getConfigFolder() {
        String path = System.getProperty("user.dir") + "\\data\\cfg\\";
        return path;
    }
    
    public static String getVoiceFolder() {
        String path = System.getProperty("user.dir") + "\\data\\voice\\";
        return path;
    }
    
    public static String getImageFolder() {
        String path = System.getProperty("user.dir") + "\\data\\img\\";
        return path;
    }
    
    public static boolean checkFolder(String path) {
        boolean check = false;
        File dataFolder = new File(path);
        if (dataFolder.exists()) {
            check = true;
        } else {
            if (dataFolder.mkdirs()) {
                System.out.println("Create " + dataFolder + " Success");
                check = true;
            }
        }
        return check;
    }
    
    public static void checkDataFolder() {
        checkFolder(getVoiceFolder());
        checkFolder(getImageFolder());
        checkFolder(getConfigFolder());
    }
    
    public static boolean createConfigFile() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getConfigPath())));
            String s = "gì đó";
            bufferedWriter.write(s);
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(WordController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static boolean checkConfigFolder() {
        boolean check = false;
        File dataFolder = new File(getPath("\\data\\cfg"));
        if (dataFolder.exists()) {
            check = true;
        } else {
            System.out.println("Chưa có folder cfg");
            if (dataFolder.mkdirs()) {
                System.out.println("Create " + dataFolder + " Success");
                check = createConfigFile();
 //<editor-fold defaultstate="collapsed">               
//                File configFile = new File(getConfigPath());
//                try {
//                    configFile.createNewFile();
//                    System.out.println("Tạo file config thành công");
//                } catch (IOException ex) {
//                    System.out.println("Tạo file config thất bại");
//                    check = false;
//                }
            }
        }        
//                String s = "level Easy\n" +"point 1";
//                FileOutputStream config_text = new FileOutputStream(getConfigPath());
//                config_text.write(s.getBytes());
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(WordController.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(WordController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//</editor-fold>  
        return check;
    }

    public static int writeWord(String path) {
        int rs = -1;
        if (words != null) {
            String s = String.valueOf(total) + "\n";
            for (Word w
                    : words) {
                s += w.toString();
            }
            System.out.println(s);
            try {
//                Writer writer = new FileWriter(path);
//                writer.write(s);
//                writer.close();
//                rs = 1;
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
                writer.write(s);
                writer.close();
                rs = 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    public static boolean readFile(String path) {
        boolean check = true;
        List<Word> wordArrayList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            total = Integer.parseInt(line);
            line = reader.readLine();
            while (line != null) {
                String[] output = line.split("#");
                String[] hashtags = output[8].split("@");
                LocalDate datemdf = LocalDate.parse(output[9]);
                Word word = new Word(Integer.parseInt(output[0]), output[1], output[2], output[3], output[4],
                        output[5], output[6], output[7], hashtags, datemdf, Integer.parseInt(output[10]));
                wordArrayList.add(word);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            check = false;
            e.printStackTrace();
            try {
                File data_text = new File(getDataPath());
                data_text.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(WordController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Tạo text.txt thất bại");
            }
            System.out.println("Tạo text.txt thành công");

        } catch (IOException e) {
            check = false;
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        words = wordArrayList;

        return check;
    }

    public static Word findWord(int id) {
        for (Word w
                : words) {
            if (w.getId() == id) {
                return w;
            }
        }
        return null;
    }

    public static Word findWord(String name) {
        for (Word w
                : words) {
            if (w.getWord().equals(name)) {
                return w;
            }
        }
        return null;
    }

    public static Word findWord(List<Word> ws, String name) {
        for (Word w
                : ws) {
            if (w.getWord().equals(name)) {
                return w;
            }
        }
        return null;
    }

    public static boolean isExistWord(String name) {
        for (Word w
                : words) {
            if (w.getWord().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static Word findMean(String mean) {
        for (Word w
                : words) {
            if (w.getWord().equals(mean)) {
                return w;
            }
        }
        return null;
    }

    public static Word findHashtag(String hashtag) {
        for (Word w
                : words) {
            String[] hl = w.getHashtag();
            for (String h
                    : hl) {
                if (h.equals(hashtag)) {
                    return w;
                }
            }
        }
        return null;
    }

    public static List<Word> findHashtag(String... hashtag) {
        List<Word> rs = new ArrayList<>();
        for (Word w
                : words) {
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

    public static List<Word> findHashtagContainAll(String... hashtag) {
        List<Word> rs = new ArrayList<>();
        words.forEach((w) -> {
            List<String> hl = Arrays.asList(w.getHashtag());
            if (hl.containsAll(Arrays.asList(hashtag))) {
                rs.add(w);
            }
        });
        return rs;
    }

    public static void addWord(Word word) {
        total++;
        word.setId(total);
        words.add(word);
    }
//<editor-fold defaultstate="collapsed">
//    public static void editWord(int id, String word, String mean, String ipa, String type, String imageURL, String voiceURL, String hint,
//                                String[] hashtag, LocalDate dateModified){
//        for (int i = 0; i < words.size(); i++) {
//            if (words.get(i).getId() == id){
//                words.get(i).setWord(word);
//                words.get(i).setMean(mean);
//                words.get(i).setIpa(ipa);
//                words.get(i).setType(type);
//                words.get(i).setImageURL(imageURL);
//                words.get(i).setVoiceURL(voiceURL);
//                words.get(i).setHint(hint);
//                words.get(i).setHashtag(hashtag);
//                words.get(i).setDateModified(dateModified);
//            }
//        }
//    }
//</editor-fold>
    public static void editWord(String inputWord) {
        Word w = WordController.findWord(inputWord);
        System.out.println("w là: " + w);
        AddWUI editUI = new AddWUI();
        editUI.setIsEdit(true);
        editUI.getWordField().setText(w.getWord());
        editUI.getPhoneticField().setText(w.getIpa());
        editUI.getPhoneticField().setForeground(Color.BLACK);
        editUI.getMeanField().setText(w.getMean());
        editUI.getTypeCombo().setSelectedItem(w.getType());
        editUI.getHintTextArea().setText(w.getHint());
        editUI.getHashtagField().setText(WordController.stringArray2String(w.getHashtag(), " "));
        editUI.getHashtagField().setForeground(Color.BLACK);
        editUI.getVoiceFileURL().setText(w.getVoiceURL());
        editUI.setVoiceURL(w.getVoiceURL());
        editUI.getWordField().setEnabled(false);
        //image

        try {
            File img = new File(w.getImageURL());
            BufferedImage image = ImageIO.read(img);
            ImageIcon icon = new ImageIcon(image.getScaledInstance(editUI.getImgLable().getWidth(), editUI.getImgLable().getHeight(), BufferedImage.SCALE_SMOOTH));
            editUI.getImgLable().setIcon(icon);
            editUI.getImgLable().setText("");
            editUI.setImageURL(w.getImageURL());
        } catch (IOException ex) {
            Logger.getLogger(AddWUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        //
        editUI.setVisible(true);
    }

    public static void deleteWord(int id) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getId() == id) {
                words.remove(i);
                break;
            }
        }
    }

    public static String hashtag2String(String[] hashtag) {
        return stringArray2String(hashtag, "@");
    }

    public static String stringArray2String(String[] hashtag, String regex) {
        String rs = "";
        if (hashtag != null) {
            for (String hg
                    : hashtag) {
                rs += hg + regex;
            }
        }
        return rs;
    }
    
    public static void playSound(Word word){
        Synthesizer s = new Synthesizer();
        if("".equals(word.getVoiceURL())){
            s.playSound(s.getVoiceInputStream(word.getWord()));
        }
        else{
            s.playSound(word.getVoiceURL());
        }
    }
    public static void playSound(String url){
        Synthesizer s = new Synthesizer();
        s.playSound(url);
    }

    public static void text2speech(String word) {
        //<editor-fold defaultstate="collapsed" desc=" Free TTS ">
//        Voice voice = VoiceManager.getInstance().getVoice("kevin");
//        voice.allocate();
//        voice.speak(word);
//        System.out.println(voice.getAge());
//        System.out.println(voice.toString());
//</editor-fold>

        Synthesizer s = new Synthesizer();
        s.text2Speech(word);          
    }
    public static void saveVoice(String word){
        Synthesizer s = new Synthesizer();
        DataController dc = new DataController();
        dc.writeMp3(s.getVoiceInputStream(word), word);
    }
    public static String getPhonetic(String word) {
        Phonetic phonetic = new Phonetic();
        return phonetic.getPhonetic(word);
    }
    
    public static void saveImage(File image, String word){
        DataController dataController = new DataController();
        dataController.writeJpg(image, word);
    }
    public static List<Word> coppyWords() {
        List<Word> list = new ArrayList<Word>();
        for (Word word : words) {
            list.add(word);
        }
        return list;
    }
}
