/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.model;

import com.sownbanana.controller.WordController;
import java.time.LocalDate;

/**
 *
 * @author SownBanana
 */
public class Word {
    private int id;
    private String word;
    private String mean;
    private String ipa;
    private String type;
    private String imageURL;
    private String voiceURL;
    private String hint;
    private String[] hashtag;
    private LocalDate dateModified;
    private int freq;

    public Word() {
    }

    public Word(String word, String mean, String ipa, String type, String imageURL, String voiceURL, String hint,
                String[] hashtag, LocalDate dateModified, int freq)
    {
        this.id = WordController.total++;
        this.word = word;
        this.mean = mean;
        this.ipa = ipa;
        this.type = type;
        this.imageURL = imageURL;
        this.voiceURL = voiceURL;
        this.hint = hint;
        this.hashtag = hashtag;
        this.dateModified = dateModified;
        this.freq = freq;
    }

    public Word(int id, String word, String mean, String ipa,String type, String imageURL, String voiceURL, String hint,
                String[] hashtag, LocalDate dateModified, int freq)
    {
        this.freq = freq;
        this.id = id;
        this.word = word;
        this.mean = mean;
        this.ipa = ipa;
        this.type = type;
        this.imageURL = imageURL;
        this.voiceURL = voiceURL;
        this.hint = hint;
        this.hashtag = hashtag;
        this.dateModified = dateModified;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getIpa() {
        return ipa;
    }

    public void setIpa(String ipa) {
        this.ipa = ipa;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getVoiceURL() {
        return voiceURL;
    }

    public void setVoiceURL(String voiceURL) {
        this.voiceURL = voiceURL;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String[] getHashtag() {
        return hashtag;
    }

    public void setHashtag(String[] hashtag) {
        this.hashtag = hashtag;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }
    

    @Override
    public String toString() {
        return        id +
                "#" + word +
                "#" + mean +
                "#" + ipa +
                "#" + type +
                "#" + imageURL +
                "#" + voiceURL +
                "#" + hint +
                "#" + WordController.hashtag2String(hashtag) +
                "#" + dateModified +
                "#" + freq +"\n";
    }

}
