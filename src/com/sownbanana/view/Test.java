/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.view;

/**
 *
 * @author Nguyễn Đình Dương
 */
public class Test {
    public static void main(String[] args) {
        int i = 0;
        String wordString = "aloa alo";
        for (int j = 0; j < wordString.length(); j++) {
            char aChar = wordString.charAt(j);
            if((Character.toString(aChar).equals(" " ))){
                i = j;
                break;
            }
        }
        if(i == 0){
            i = wordString.length();
        }
        System.out.println(i-1);
    }
}
