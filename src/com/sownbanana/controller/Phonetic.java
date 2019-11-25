/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author SownBanana
 */
public class Phonetic {

    private static final String GOOGLE_WORD_DEF_URL = "https://googledictionaryapi.eu-gb.mybluemix.net/?lang=en&define=";

    public String getPhonetic(String word) throws FileNotFoundException, IOException {
        String[] eachWord = word.split(" ");
//         System.out.println(Arrays.toString(eachWord));
        String fullPhonetic = "";
        for (String w : eachWord) {
//                 System.out.println(w);
            String getPhoneticURL = GOOGLE_WORD_DEF_URL + w;
            URL url = new URL(getPhoneticURL);
            URLConnection uRLConnection = url.openConnection();
            uRLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:2.0) Gecko/20100101 Firefox/4.0");
            InputStream is = uRLConnection.getInputStream();
//<editor-fold defaultstate="collapsed" desc=" HTML ">
//                 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//                 StringBuffer sb = new StringBuffer();
//                 String s;
//                 while ((s = reader.readLine()) != null) {                     
//                     sb.append(s);
//                 }
//                 System.out.println(sb);
//</editor-fold>                           

            JsonReader jsonReader = Json.createReader(is);
            JsonArray ja = jsonReader.readArray();
            JsonObject json = ja.getJsonObject(0);
            jsonReader.close();
            String phonetic = json.getString("phonetic").replaceAll("/", "") + " ";
//                 System.out.println(phonetic);
            fullPhonetic = fullPhonetic + phonetic;
        }
//         System.out.println(fullPhonetic);
        return fullPhonetic;
    }

    public static void main(String[] args) throws IOException {
        Phonetic p = new Phonetic();
        p.getPhonetic("test this shit");
    }
}
