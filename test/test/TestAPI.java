/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.sownbanana.controller.Phonetic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author SownBanana
 */
public class TestAPI {
    private static final String GOOGLE_WORD_DEF_URL = "https://www.lexico.com/en/definition/";
    
     public String getPhonetic(String word){
         String[] eachWord = word.split(" ");
//         System.out.println(Arrays.toString(eachWord));
         String fullPhonetic = "";
         for (String w : eachWord) {
             try {
//                 System.out.println(w);
                 String getPhoneticURL = GOOGLE_WORD_DEF_URL + w;
                 URL url = new URL(getPhoneticURL);
                 URLConnection uRLConnection = url.openConnection();
                 uRLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:2.0) Gecko/20100101 Firefox/4.0");
                 InputStream is = uRLConnection.getInputStream();
//<editor-fold defaultstate="collapsed" desc=" HTML ">
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                 StringBuffer sb = new StringBuffer();
                 String s;
                 while ((s = reader.readLine()) != null) {                     
                     sb.append(s);
                 }
                 String rs = sb.toString();
                 String[] output = rs.split("<span class=\"phoneticspelling\">");
                 System.out.println(output.length);
//                 System.out.println("0" + output[0]);
//                 System.out.println("=============================");
//                 System.out.println("1" + output[1]);
//                 System.out.println("=============================");
//                 System.out.println("2" + output[2]);
//                 System.out.println("=============================");
//                 System.out.println("3" + output[3]);
                 String phonetic = "";
                 if(output[2].charAt(0) == '<'){
                     String[] ipa = output[1].split("/");
//                     System.out.println("1. "+ ipa[0] + "\n2. "+ ipa[1] +"\n3. "+ ipa[2]);
                     phonetic = ipa[1];
                 }
                 else{
                     String[] ipa = output[2].split("/");
//                     System.out.println("1. "+ ipa[0] + "\n2. "+ ipa[1] +"\n3. "+ ipa[2]);
                     phonetic = ipa[1];
                 }
                 
//                 String 
                 
//                 System.out.println(sb);
//</editor-fold>                           
                 

//                 JsonReader jsonReader = Json.createReader(is);
//                 JsonArray ja = jsonReader.readArray();
//                 JsonObject json = ja.getJsonObject(0);
//                 jsonReader.close();
//                 String phonetic = json.getString("phonetic").replaceAll("/", "")+ " ";
//                 System.out.println(phonetic);
                 fullPhonetic = fullPhonetic + phonetic + " ";
             } catch (MalformedURLException ex) {
                 Logger.getLogger(Phonetic.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
                 Logger.getLogger(Phonetic.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
//         System.out.println(fullPhonetic);
         return fullPhonetic;
     }
     public static void main(String[] args) {
        TestAPI tapi = new TestAPI();
         System.out.println(tapi.getPhonetic("determinate this shit"));
    }
}
