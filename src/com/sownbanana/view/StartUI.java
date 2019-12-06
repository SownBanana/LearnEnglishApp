/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sownbanana.view;

import com.sownbanana.controller.Config;
import com.sownbanana.controller.WordController;
import com.sownbanana.model.Word;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import com.sownbanana.view.HomeUI;
import static com.sownbanana.view.ListWord.list;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 *
 * @author Nguyễn Đình Dương
 */
public class StartUI extends javax.swing.JFrame {

    /**
     * Creates new form StartUI
     */
    JTextField textFields[];
    Word word;
    String gameLevelString;
    String checkLevelString;
    String modeString;
    List<Word> list;
    JTextField wordTextField = new JTextField();
    JTextField wordTextFieldHint = new JTextField();
    Config config = new Config();
    int freqCount;
    public boolean checkUseAI;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public StartUI(boolean checkUseAIFromHome) {
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getRootPane().getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exit");
        am.put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(rootPane, "Bạn thực sự muốn thoát?", "Chơi", JOptionPane.YES_NO_OPTION) == 0) {
                    dispose();
                }
            }
        });
//        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "submit");
//        am.put("submit", new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                checkLevelString = checkLevel.getSelectedItem().toString();
//                String wordString = word.getWord().replaceAll("[,.:;?!&’'\"]", "");
//                checkTrueFalse(wordString, checkLevelString);
//            }
//        });

        //From config
        gameLevelString = Config.gameLevel;
        checkLevelString = Config.checkLevel;
        gameLevel.setSelectedItem(gameLevelString);
        checkLevel.setSelectedItem(checkLevelString);
        if (Config.isDateFillter) {
            modeString = "Date Modified";
            modeTextFields.setText(Config.date);
            modeComboBox.setSelectedItem("Date Modified");
        } else {
            modeString = "Hashtag";
            modeTextFields.setText(WordController.hashtagFancy(Config.hashtag));
            modeComboBox.setSelectedItem("Hashtag");
        }
//        gameLevelString = gameLevel.getSelectedItem().toString();
        phoneticField.setEditable(false);
        meaningField.setEditable(false);
        typeField.setEditable(false);
        hashtagField.setEditable(false);
        hintField.setEditable(false);
        list = WordController.copyWords();
        modeString = modeComboBox.getSelectedItem().toString();
        switch (modeString) {
            case "Hashtag":
                String[] output = modeTextFields.getText().replaceAll("#", " ").replaceAll("\\s+", " ").trim().split(" ");
                if (modeTextFields.getText().trim().equals("")) {
                    list = WordController.copyWords();
                } else {
                    list = WordController.findHashtag(list, output);
                    System.out.println("Hashtag" + list);
                }
                break;
            case "Date Modified":
                if (modeTextFields.getText().trim().equals("")) {
                    list = WordController.copyWords();
                } else if (modeTextFields.getText().trim().toLowerCase().contains("nay") || modeTextFields.getText().trim().toLowerCase().contains("today") || modeTextFields.getText().trim().toLowerCase().contains("recent")) {
                    list = WordController.findWordAddToday(list);
                } else if (modeTextFields.getText().trim().length() <= 2) {
                    try {
                        list = WordController.findWordByDate("month", Integer.parseInt(modeTextFields.getText().trim()), list);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(rootPane, "Nhập sai định dạng (Số nguyên)");
                    }
                } else if (modeTextFields.getText().trim().length() <= 5) {
                    try {
                        list = WordController.findWordByDate("year", Integer.parseInt(modeTextFields.getText().trim()), list);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(rootPane, "Nhập sai định dạng (Số nguyên)");
                    }
                } else {
                    list = WordController.findWordByDate(modeTextFields.getText(), list);
                }
                break;
        }
        checkUseAI = checkUseAIFromHome;
        System.out.println("check AI " + checkUseAI);
        if (checkUseAI) {
            System.out.println("AI duplicated");
            list = WordController.duplicateWordinList(list);
        }
        displayGame();

    }

    //<editor-fold defaultstate="collapsed" desc=" getter and setter">
    public JTextField[] getTextFields() {
        return textFields;
    }

    public void setTextFields(JTextField[] textFields) {
        this.textFields = textFields;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getGameLevelString() {
        return gameLevelString;
    }

    public void setGameLevelString(String gameLevelString) {
        this.gameLevelString = gameLevelString;
    }

    public String getCheckLevelString() {
        return checkLevelString;
    }

    public void setCheckLevelString(String checkLevelString) {
        this.checkLevelString = checkLevelString;
    }

    public List<Word> getList() {
        return list;
    }

    public void setList(List<Word> list) {
        this.list = list;
    }

    public JButton getRandombtn() {
        return submitButton;
    }

    public void setRandombtn(JButton Randombtn) {
        this.submitButton = Randombtn;
    }

    public JButton getBtnNext() {
        return nextButton;
    }

    public void setBtnNext(JButton btnNext) {
        this.nextButton = btnNext;
    }

    public JComboBox<String> getCheckLevel() {
        return checkLevel;
    }

    public void setCheckLevel(JComboBox<String> checkLevel) {
        this.checkLevel = checkLevel;
    }

    public JComboBox<String> getGameLevel() {
        return gameLevel;
    }

    public void setGameLevel(JComboBox<String> gameLevel) {
        this.gameLevel = gameLevel;
    }

    public JTextArea getHashtagField() {
        return hashtagField;
    }

    public void setHashtagField(JTextArea hashtagField) {
        this.hashtagField = hashtagField;
    }

    public JTextArea getHintField() {
        return hintField;
    }

    public void setHintField(JTextArea hintField) {
        this.hintField = hintField;
    }

    public JLabel getHintLabel() {
        return hintLabel;
    }

    public void setHintLabel(JLabel hintLabel) {
        this.hintLabel = hintLabel;
    }

    public JLabel getImgLable() {
        return imgLable;
    }

    public void setImgLable(JLabel imgLable) {
        this.imgLable = imgLable;
    }

    public JComboBox<String> getjComboBox2() {
        return modeComboBox;
    }

    public void setjComboBox2(JComboBox<String> jComboBox2) {
        this.modeComboBox = jComboBox2;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }

    public void setjLabel1(JLabel jLabel1) {
        this.jLabel1 = jLabel1;
    }

    public JLabel getjLabel2() {
        return jLabel2;
    }

    public void setjLabel2(JLabel jLabel2) {
        this.jLabel2 = jLabel2;
    }

    public JLabel getjLabel3() {
        return jLabel3;
    }

    public void setjLabel3(JLabel jLabel3) {
        this.jLabel3 = jLabel3;
    }

    public JLabel getjLabel4() {
        return jLabel4;
    }

    public void setjLabel4(JLabel jLabel4) {
        this.jLabel4 = jLabel4;
    }

    public JLabel getjLabel7() {
        return jLabel7;
    }

    public void setjLabel7(JLabel jLabel7) {
        this.jLabel7 = jLabel7;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    public void setjScrollPane2(JScrollPane jScrollPane2) {
        this.jScrollPane2 = jScrollPane2;
    }

    public JScrollPane getjScrollPane3() {
        return jScrollPane3;
    }

    public void setjScrollPane3(JScrollPane jScrollPane3) {
        this.jScrollPane3 = jScrollPane3;
    }

    public JScrollPane getjScrollPane4() {
        return jScrollPane4;
    }

    public void setjScrollPane4(JScrollPane jScrollPane4) {
        this.jScrollPane4 = jScrollPane4;
    }

    public JTextArea getMeaningField() {
        return meaningField;
    }

    public void setMeaningField(JTextArea meaningField) {
        this.meaningField = meaningField;
    }

    public JLabel getMeaningLabel() {
        return meaningLabel;
    }

    public void setMeaningLabel(JLabel meaningLabel) {
        this.meaningLabel = meaningLabel;
    }

    public JTextArea getPhoneticField() {
        return phoneticField;
    }

    public void setPhoneticField(JTextArea phoneticField) {
        this.phoneticField = phoneticField;
    }

    public JLabel getPhoneticLabel() {
        return phoneticLabel;
    }

    public void setPhoneticLabel(JLabel phoneticLabel) {
        this.phoneticLabel = phoneticLabel;
    }

    public JButton getPronounceButton() {
        return pronounceButton;
    }

    public void setPronounceButton(JButton pronounceButton) {
        this.pronounceButton = pronounceButton;
    }

    public JPanel getTextPanel() {
        return textPanel;
    }

    public void setTextPanel(JPanel textPanel) {
        this.textPanel = textPanel;
    }

    public JTextField getTypeField() {
        return typeField;
    }

    public void setTypeField(JTextField typeField) {
        this.typeField = typeField;
    }
    //</editor-fold>

    private int indexEndNextWord(String wordString) {
        int i = 0;
        for (int j = 0; j < wordString.length(); j++) {
            char aChar = wordString.charAt(j);
            if ((Character.toString(aChar).equals(" "))) {
                i = j;
                break;
            }
        }
        if (i == 0) {
            i = wordString.length();
        }
        return i - 1;
    }

    private void displayGame() {
        if (list.isEmpty()) {
            list = WordController.duplicateWordinList(WordController.copyWords());
        }
        textFields = null;
        Collections.shuffle(list);
        System.out.println(list);
        word = list.get(0);
        freqCount = word.getFreq();
        System.out.println(word.getWord());
        String wordString = word.getWord().replaceAll("[,.:;?!&’'\"]", "");
        System.out.println(word);
        int lengthWord = wordString.length();
        System.out.println(lengthWord);
        textFields = new JTextField[lengthWord];
        if (gameLevelString != "Legendary") {
            int ver = 0, hor = -1; //biến ver phục vụ cho việc xuống dòng
            for (int i = 0; i < lengthWord; i++) {
                hor++;
                char aString = wordString.charAt(i);
                if ((!Character.toString(aString).equals(" "))) {
                    textFields[i] = new JTextField();
                    textFields[i].setBounds(30 * (hor + 1), 35 * ver, 30, 30);
                    textFields[i].setFont(new Font(".VNArialH", Font.PLAIN, 13));
                    textPanel.add(textFields[i]);
                    textFields[i].setVisible(true);
                    //Xử lý mỗi ô nhập một ký tự
                    if ((isLevelEasyToHard(gameLevelString) && i == 1) || (!isLevelEasyToHard(gameLevelString) && i == 0)) {
                        textFields[i].addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                JTextField jTextField = (JTextField) e.getSource();
                                if (e.getKeyCode() == 8) {
                                    if ("".equals(jTextField.getText())) {

                                    }
                                } else if (e.getKeyCode() == 37 || e.getKeyCode() == 38) {

                                } else if (e.getKeyCode() == 39 || e.getKeyCode() == 40) {
                                    jTextField.transferFocus();
                                } //                                else if (e.getKeyCode() == 10){
                                //                                    System.out.println("Before Text = " + jTextField.getText().trim());
                                //                                    jTextField.setText(jTextField.getText().trim());
                                //                                } 
                                else {
                                    char key = e.getKeyChar();
                                    if (key == ' ') {

                                    } else {
                                        jTextField.setText(String.valueOf(key).substring(0, 0).toUpperCase());
                                        jTextField.setBackground(Color.WHITE);
                                        jTextField.transferFocus();
                                    }
                                }

                            }
                        });
                    } else if ((isLevelEasyToHard(gameLevelString) && i == lengthWord - 2) || (!isLevelEasyToHard(gameLevelString) && i == lengthWord - 1)) {
                        textFields[i].addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                JTextField jTextField = (JTextField) e.getSource();
                                if (e.getKeyCode() == 8) {
                                    if ("".equals(jTextField.getText())) {
                                        jTextField.transferFocusBackward();
                                    }
                                } else if (e.getKeyCode() == 37 || e.getKeyCode() == 38) {
                                    jTextField.transferFocusBackward();
                                } else if (e.getKeyCode() == 39 || e.getKeyCode() == 40) {

                                } else {
                                    char key = e.getKeyChar();
                                    if (key == ' ') {

                                    } else {
                                        jTextField.setText(String.valueOf(key).substring(0, 0).toUpperCase());
                                        jTextField.setBackground(Color.WHITE);
                                    }
                                }

                            }
                        });
                    } else {
                        textFields[i].addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                JTextField jTextField = (JTextField) e.getSource();
                                if (e.getKeyCode() == 8) {
                                    if ("".equals(jTextField.getText())) {
                                        jTextField.transferFocusBackward();
                                    }
                                } else if (e.getKeyCode() == 37 || e.getKeyCode() == 38) {
                                    jTextField.transferFocusBackward();
                                } else if (e.getKeyCode() == 39 || e.getKeyCode() == 40) {
                                    jTextField.transferFocus();
                                } else {
                                    char key = e.getKeyChar();
                                    if (key == ' ') {

                                    } else {
                                        jTextField.setText(String.valueOf(key).substring(0, 0).toUpperCase());
                                        jTextField.setBackground(Color.WHITE);
                                        jTextField.transferFocus();
                                    }
                                }

                            }
                            //<editor-fold defaultstate="collapsed" desc=" another version">
//                    @Override
//                    public void keyPressed(KeyEvent e) {
//                        JTextField jTextField = (JTextField) e.getSource();
//                        if (!(jTextField.getText().length() == 1) && !(jTextField.getText().equals(""))) {
//                            String rs = jTextField.getText().substring(1, 2);
//                            jTextField.setText(rs);
//
//                        }
//                        jTextField.transferFocus();
//                    }
                            //</editor-fold>
                        });

                    }
                } else {
                    int j = indexEndNextWord(wordString.substring(i + 1)) + 1;
                    if (j > 15 - hor) { //1 dòng chỉ được tối đa 15 ký tự
                        ver++;
                        hor = -1;
                    }
                }
            }
            textPanel.revalidate();
            textPanel.repaint();
            switch (gameLevelString) {
                case "Very Easy":
                case "Easy":
                case "Medium":
                case "Hard":
                    textFields[0].setText(Character.toString(wordString.charAt(0)));
                    textFields[lengthWord - 1].setText(Character.toString(wordString.charAt(lengthWord - 1)));
                    textFields[0].setEditable(false);
                    textFields[0].setFocusable(false); 
                    textFields[lengthWord - 1].setEditable(false);                   
                    textFields[0].setBackground(Color.green);
                    textFields[lengthWord - 1].setBackground(Color.green);
                    break;
            }
        } else {
            wordTextField.setBounds(30, 0, 300, 30);
            wordTextField.setFont(new Font(".VNArialH", Font.PLAIN, 13));
            textPanel.add(wordTextField);
        }
        phoneticField.setText(word.getIpa());
        meaningField.setText(word.getMean().replaceAll("%newline%", "\n"));
        hashtagField.setText(word.hashtagFancy());
        hintField.setText(word.getHint().replaceAll("%newline%", "\n"));
        typeField.setText(word.getType());
        dateModified.setText(word.getDateModified().format(formatter));

        File picture = new File(word.getImageURL());
        BufferedImage image;

        try {
            image = ImageIO.read(picture);
            ImageIcon icon;
            icon = new ImageIcon(image.getScaledInstance(imgLable.getWidth(), imgLable.getHeight(), BufferedImage.SCALE_SMOOTH));
            imgLable.setIcon(icon);

        } catch (IOException ex) {
            Logger.getLogger(StartUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        transferFocus();
    }
    private void redisplayGame(Word w) {
        String wordString = w.getWord().replaceAll("[,.:;?!&’'\"]", "");
        System.out.println(w);
        int lengthWord = wordString.length();
        System.out.println(lengthWord);
        textFields = new JTextField[lengthWord];
        if (gameLevelString != "Legendary") {
            int ver = 0, hor = -1; //biến ver phục vụ cho việc xuống dòng
            for (int i = 0; i < lengthWord; i++) {
                hor++;
                char aString = wordString.charAt(i);
                if ((!Character.toString(aString).equals(" "))) {
                    textFields[i] = new JTextField();
                    textFields[i].setBounds(30 * (hor + 1), 35 * ver, 30, 30);
                    textFields[i].setFont(new Font(".VNArialH", Font.PLAIN, 13));
                    textPanel.add(textFields[i]);
                    textFields[i].setVisible(true);
                    //Xử lý mỗi ô nhập một ký tự
                    if ((isLevelEasyToHard(gameLevelString) && i == 1) || (!isLevelEasyToHard(gameLevelString) && i == 0)) {
                        textFields[i].addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                JTextField jTextField = (JTextField) e.getSource();
                                if (e.getKeyCode() == 8) {
                                    if ("".equals(jTextField.getText())) {

                                    }
                                } else if (e.getKeyCode() == 37 || e.getKeyCode() == 38) {

                                } else if (e.getKeyCode() == 39 || e.getKeyCode() == 40) {
                                    jTextField.transferFocus();
                                } //                                else if (e.getKeyCode() == 10){
                                //                                    System.out.println("Before Text = " + jTextField.getText().trim());
                                //                                    jTextField.setText(jTextField.getText().trim());
                                //                                } 
                                else {
                                    char key = e.getKeyChar();
                                    if (key == ' ') {

                                    } else {
                                        jTextField.setText(String.valueOf(key).substring(0, 0).toUpperCase());
                                        jTextField.setBackground(Color.WHITE);
                                        jTextField.transferFocus();
                                    }
                                }

                            }
                        });
                    } else if ((isLevelEasyToHard(gameLevelString) && i == lengthWord - 2) || (!isLevelEasyToHard(gameLevelString) && i == lengthWord - 1)) {
                        textFields[i].addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                JTextField jTextField = (JTextField) e.getSource();
                                if (e.getKeyCode() == 8) {
                                    if ("".equals(jTextField.getText())) {
                                        jTextField.transferFocusBackward();
                                    }
                                } else if (e.getKeyCode() == 37 || e.getKeyCode() == 38) {
                                    jTextField.transferFocusBackward();
                                } else if (e.getKeyCode() == 39 || e.getKeyCode() == 40) {

                                } else {
                                    char key = e.getKeyChar();
                                    if (key == ' ') {

                                    } else {
                                        jTextField.setText(String.valueOf(key).substring(0, 0).toUpperCase());
                                        jTextField.setBackground(Color.WHITE);
                                    }
                                }

                            }
                        });
                    } else {
                        textFields[i].addKeyListener(new KeyAdapter() {
                            @Override
                            public void keyPressed(KeyEvent e) {
                                JTextField jTextField = (JTextField) e.getSource();
                                if (e.getKeyCode() == 8) {
                                    if ("".equals(jTextField.getText())) {
                                        jTextField.transferFocusBackward();
                                    }
                                } else if (e.getKeyCode() == 37 || e.getKeyCode() == 38) {
                                    jTextField.transferFocusBackward();
                                } else if (e.getKeyCode() == 39 || e.getKeyCode() == 40) {
                                    jTextField.transferFocus();
                                } else {
                                    char key = e.getKeyChar();
                                    if (key == ' ') {

                                    } else {
                                        jTextField.setText(String.valueOf(key).substring(0, 0).toUpperCase());
                                        jTextField.setBackground(Color.WHITE);
                                        jTextField.transferFocus();
                                    }
                                }

                            }
                            //<editor-fold defaultstate="collapsed" desc=" another version">
//                    @Override
//                    public void keyPressed(KeyEvent e) {
//                        JTextField jTextField = (JTextField) e.getSource();
//                        if (!(jTextField.getText().length() == 1) && !(jTextField.getText().equals(""))) {
//                            String rs = jTextField.getText().substring(1, 2);
//                            jTextField.setText(rs);
//
//                        }
//                        jTextField.transferFocus();
//                    }
                            //</editor-fold>
                        });

                    }
                } else {
                    int j = indexEndNextWord(wordString.substring(i + 1)) + 1;
                    if (j > 15 - hor) { //1 dòng chỉ được tối đa 15 ký tự
                        ver++;
                        hor = -1;
                    }
                }
            }
            textPanel.revalidate();
            textPanel.repaint();
            switch (gameLevelString) {
                case "Very Easy":
                case "Easy":
                case "Medium":
                case "Hard":
                    textFields[0].setText(Character.toString(wordString.charAt(0)));
                    textFields[lengthWord - 1].setText(Character.toString(wordString.charAt(lengthWord - 1)));
                    textFields[0].setEditable(false);
                    textFields[0].setFocusable(false); 
                    textFields[lengthWord - 1].setEditable(false);                   
                    textFields[0].setBackground(Color.green);
                    textFields[lengthWord - 1].setBackground(Color.green);
                    break;
            }
        } else {
            wordTextField.setBounds(30, 0, 300, 30);
            wordTextField.setFont(new Font(".VNArialH", Font.PLAIN, 13));
            textPanel.add(wordTextField);
        }

        transferFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        submitButton = new javax.swing.JButton();
        gameLevel = new javax.swing.JComboBox<>();
        modeComboBox = new javax.swing.JComboBox<>();
        checkLevel = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        imgLable = new javax.swing.JLabel();
        phoneticLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        meaningField = new javax.swing.JTextArea();
        meaningLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        phoneticField = new javax.swing.JTextArea();
        pronounceButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        hashtagField = new javax.swing.JTextArea();
        hintLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        hintField = new javax.swing.JTextArea();
        textPanel = new javax.swing.JPanel();
        nextButton = new javax.swing.JButton();
        modeTextFields = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        typeField = new javax.swing.JTextField();
        dateModifiedLabel = new javax.swing.JLabel();
        dateModified = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        gameLevel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        gameLevel.setForeground(new java.awt.Color(255, 0, 0));
        gameLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Very Easy", "Easy", "Medium", "Hard", "Very Hard", "Nightmare", "Legendary" }));
        gameLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gameLevelActionPerformed(evt);
            }
        });

        modeComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        modeComboBox.setForeground(new java.awt.Color(0, 153, 255));
        modeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hashtag", "Date Modified" }));
        modeComboBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                modeComboBoxFocusLost(evt);
            }
        });

        checkLevel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        checkLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mức 1", "Mức 2", "Mức 3", "Mức 4" }));
        checkLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkLevelActionPerformed(evt);
            }
        });

        jLabel1.setText("Độ khó Game");

        jLabel2.setText("Độ khó Chấm điểm");

        jLabel3.setText("Chế độ");

        imgLable.setText("Ảnh");

        phoneticLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        phoneticLabel.setText("Phiên âm");

        meaningField.setColumns(20);
        meaningField.setRows(5);
        jScrollPane1.setViewportView(meaningField);

        meaningLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        meaningLabel.setText("Nghĩa");

        phoneticField.setColumns(20);
        phoneticField.setRows(5);
        jScrollPane2.setViewportView(phoneticField);

        pronounceButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        pronounceButton.setText("Phát");
        pronounceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pronounceButtonActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Hashtag");

        hashtagField.setColumns(20);
        hashtagField.setRows(5);
        jScrollPane3.setViewportView(hashtagField);

        hintLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        hintLabel.setText("Gợi ý");

        hintField.setColumns(20);
        hintField.setRows(5);
        jScrollPane4.setViewportView(hintField);

        javax.swing.GroupLayout textPanelLayout = new javax.swing.GroupLayout(textPanel);
        textPanel.setLayout(textPanelLayout);
        textPanelLayout.setHorizontalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        textPanelLayout.setVerticalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 211, Short.MAX_VALUE)
        );

        nextButton.setText("Next");
        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nextButtonMouseClicked(evt);
            }
        });
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        modeTextFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modeTextFieldsActionPerformed(evt);
            }
        });

        jButton1.setText("Lọc");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Từ loại:");

        typeField.setBackground(new java.awt.Color(240, 240, 240));
        typeField.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        typeField.setBorder(null);
        typeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeFieldActionPerformed(evt);
            }
        });

        dateModifiedLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dateModifiedLabel.setText("Ngày chỉnh sửa");

        dateModified.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gameLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(190, 190, 190)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(86, 86, 86)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(modeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(modeTextFields, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(textPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addComponent(imgLable, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(meaningLabel)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(phoneticLabel)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(pronounceButton))
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dateModifiedLabel)
                                .addGap(18, 18, 18)
                                .addComponent(dateModified, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(typeField, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hintLabel)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(imgLable, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(textPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(submitButton, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                            .addComponent(nextButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phoneticLabel)
                            .addComponent(pronounceButton))
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(meaningLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(typeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(dateModifiedLabel)
                                .addComponent(dateModified, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hintLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gameLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkLevel)
                    .addComponent(modeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modeTextFields, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
//        checkLevelString = checkLevel.getSelectedItem().toString();
        String wordString = word.getWord().replaceAll("[,.:;?!&’'\"]", "");
        clearTextFieldHint();
        checkTrueFalse(wordString);
    }//GEN-LAST:event_submitButtonActionPerformed

    private void gameLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gameLevelActionPerformed
//        System.out.println(word.toString());
        gameLevelString = gameLevel.getSelectedItem().toString();
        refresh();
        Config.gameLevel = gameLevelString;
        Config.writeConfigFile();     
    }//GEN-LAST:event_gameLevelActionPerformed

    private void pronounceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pronounceButtonActionPerformed
        WordController.playSound(word);
    }//GEN-LAST:event_pronounceButtonActionPerformed

    private void nextButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nextButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_nextButtonMouseClicked

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        clearTextFields();
        refresh();
        displayGame();
    }//GEN-LAST:event_nextButtonActionPerformed

    private void modeTextFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modeTextFieldsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modeTextFieldsActionPerformed

    private void checkLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkLevelActionPerformed
        // TODO add your handling code here:
        checkLevelString = checkLevel.getSelectedItem().toString();
        Config.checkLevel = checkLevelString;
        Config.writeConfigFile();
    }//GEN-LAST:event_checkLevelActionPerformed

    private void typeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeFieldActionPerformed

    private void modeComboBoxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_modeComboBoxFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_modeComboBoxFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        modeString = modeComboBox.getSelectedItem().toString();
        boolean checkDateFormmat = true;
        switch (modeString) {
            case "Hashtag":
                String[] output = modeTextFields.getText().replaceAll("#", " ").replaceAll("\\s+", " ").trim().split(" ");
                if (modeTextFields.getText().trim().equals("")) {
                    list = WordController.copyWords();
                    Config.hashtag = null;
                } else {
                    list = WordController.findHashtag(WordController.words, output);
                    Config.hashtag = output;
                }
                Config.isDateFillter = false;
                Config.date = " ";
                break;
            case "Date Modified":
                if (modeTextFields.getText().trim().equals("")) {
                    list = WordController.copyWords();
                    Config.date = " ";
                } else if (modeTextFields.getText().trim().toLowerCase().contains("nay") || modeTextFields.getText().trim().toLowerCase().contains("today") || modeTextFields.getText().trim().toLowerCase().contains("recent")) {
                    list = WordController.findWordAddToday(WordController.words);
                    Config.date = modeTextFields.getText().trim();
                } else if (modeTextFields.getText().trim().length() <= 2) {
                    try {
                        list = WordController.findWordByDate("month", Integer.parseInt(modeTextFields.getText().trim()), list);
                        Config.date = modeTextFields.getText().trim();
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(rootPane, "Nhập sai định dạng thời gian (MM)");
                        checkDateFormmat = false;
                        Config.date = " ";
                    }
                } else if (modeTextFields.getText().trim().length() <= 5) {
                    try {
                        list = WordController.findWordByDate("year", Integer.parseInt(modeTextFields.getText().trim()), list);
                        Config.date = modeTextFields.getText().trim();
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(rootPane, "Nhập sai định dạng thời gian (MM/yyyy)");
                        checkDateFormmat = false;
                        Config.date = " ";
                    }
                } else {
                    if (isValidFormat(modeTextFields.getText().trim())) {
                        list = WordController.findWordByDate(modeTextFields.getText(), WordController.words);
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Nhập sai định dạng thời gian (dd/MM/yyyy)");
                        checkDateFormmat = false;
                    }
                    if (!list.isEmpty()) {
                        Config.date = modeTextFields.getText().trim();
                    }
                }
                Config.isDateFillter = true;
                Config.hashtag = null;
                break;
        }
        if (list.isEmpty()) {
            if (checkDateFormmat) {
                JOptionPane.showMessageDialog(rootPane, "Không tìm thấy từ có " + modeString + " là: " + modeTextFields.getText(), "Danh sách rỗng", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            if (checkUseAI) {
                list = WordController.duplicateWordinList(list);
            }
        }
        clearTextFields();
        displayGame();
        Config.writeConfigFile();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void clearTextFields() {
        //1-6
        for (int i = 0; i < word.getWord().replaceAll("[,.:;?!&’'\"]", "").length(); i++) {
            if (textFields[i] != null) {
                textPanel.remove(textFields[i]);
            }
        }
        //7
        wordTextField.setText("");
        wordTextField.setBackground(Color.WHITE);
        textPanel.remove(wordTextField);
        textPanel.remove(wordTextFieldHint);
        
        textPanel.revalidate();
        textPanel.repaint();
    }
    public void clearTextFieldHint(){
        textPanel.remove(wordTextFieldHint);
        textPanel.revalidate();
        textPanel.repaint();
    }

    /**
     * @param args the command line arguments
     */
    private void checkTrueFalse(String wordString) {
        gameLevelString = gameLevel.getSelectedItem().toString();
        checkLevelString = checkLevel.getSelectedItem().toString();
        boolean check = true;
        switch (checkLevelString) {
            case "Mức 1":
                if (gameLevelString == "Legendary") {
                    if (word.getWord().equals(wordTextField.getText().trim().toLowerCase()) || wordString.equals(wordTextField.getText().trim().toLowerCase())) {
                        wordTextField.setBackground(Color.GREEN);
                    } else {
                        wordTextField.setBackground(Color.RED);
                        wordTextFieldHint.setBounds(30, 35, 300, 30);
                        String wordTemp = word.getWord().trim();
                        wordTextFieldHint.setText("Có " + (wordTemp.replaceAll("[ ,.:;?!&’'\"]", "").length()) + " chữ cái cần điền");
                        wordTextFieldHint.enable(false);
                        textPanel.add(wordTextFieldHint);
                        phoneticLabel.setVisible(true);
                        phoneticField.setVisible(true);
                        pronounceButton.setVisible(true);
                        check = false;
                    }
                } else {
                    for (int i = 0; i < wordString.length(); i++) {
                        char aString = wordString.charAt(i);
                        if (" ".equals(Character.toString(aString))) {
                        } else if (textFields[i].getText().equals(Character.toString(aString))) {
                            textFields[i].setBackground(Color.GREEN);
                        } else {
                            textFields[i].setBackground(Color.RED);
                            phoneticLabel.setVisible(true);
                            phoneticField.setVisible(true);
                            pronounceButton.setVisible(true);
                            check = false;
                        }
                    }
                }
                break;
            case "Mức 2":
                if (gameLevelString == "Legendary") {
                    if (word.getWord().equals(wordTextField.getText().trim().toLowerCase()) || wordString.equals(wordTextField.getText().trim().toLowerCase())) {
                        wordTextField.setBackground(Color.GREEN);
                    } else {
                        wordTextField.setBackground(Color.RED);
                        wordTextFieldHint.setBounds(30, 35, 300, 30);
                        String wordTemp = word.getWord().trim();
                        wordTextFieldHint.setText("Các chữ cái đã điền đúng:  " + LCS(word.getWord().replaceAll("[,.:;?!&’'\"]", "").toLowerCase(), wordTextField.getText().trim().toLowerCase()));
                        wordTextFieldHint.enable(false);
                        textPanel.add(wordTextFieldHint);
                        phoneticLabel.setVisible(true);
                        phoneticField.setVisible(true);
                        pronounceButton.setVisible(true);
                        check = false;

                    }
                } else {
                    for (int i = 0; i < wordString.length(); i++) {
                        char aString = wordString.charAt(i);
                        if (" ".equals(Character.toString(aString))) {
                        } else if (textFields[i].getText().equals(Character.toString(aString))) {
                            textFields[i].setBackground(Color.GREEN);
                        } else {
                            textFields[i].setBackground(Color.RED);
                            phoneticLabel.setVisible(true);
                            phoneticField.setVisible(true);
                            pronounceButton.setVisible(true);
                            check = false;
                        }
                    }
                }
                break;
            case "Mức 3":
                if (gameLevelString == "Legendary") {
                    if (word.getWord().equals(wordTextField.getText().trim().toLowerCase()) || wordString.equals(wordTextField.getText().trim().toLowerCase())) {
                        wordTextField.setBackground(Color.GREEN);
                    } else {
                        wordTextField.setBackground(Color.RED);
                        wordTextFieldHint.setBounds(30, 35, 300, 30);
                        String wordTemp = word.getWord().trim();
                        wordTextFieldHint.setText("Các chữ cái đã điền đúng:  " + LCS(word.getWord().replaceAll("[,.:;?!&’'\"]", "").toLowerCase(), wordTextField.getText().trim().toLowerCase()));
                        wordTextFieldHint.enable(false);
//                        phoneticButton.setBounds(400, 10, 100, 30);
//                        phoneticButton.setText("Xem phiên âm");
//                        pronounceJButton.setBounds(400, 35, 100, 30);
//                        pronounceJButton.setText("Nghe phát âm");
                        textPanel.add(wordTextFieldHint);
//                        textPanel.add(phoneticButton);
//                        textPanel.add(pronounceJButton);
                        phoneticLabel.setVisible(true);
                        pronounceButton.setVisible(true);
                        check = false;

                    }
                } else {
                    for (int i = 0; i < wordString.length(); i++) {
                        char aString = wordString.charAt(i);
                        if (" ".equals(Character.toString(aString))) {
                        } else if (textFields[i].getText().equals(Character.toString(aString))) {
                            textFields[i].setBackground(Color.GREEN);
                        } else {
                            textFields[i].setBackground(Color.RED);
                            phoneticLabel.setVisible(true);
                            pronounceButton.setVisible(true);
                            check = false;
                        }
                    }
                }
                break;
            case "Mức 4":
                if (gameLevelString == "Legendary") {
                    if (word.getWord().equals(wordTextField.getText().trim().toLowerCase()) || wordString.equals(wordTextField.getText().trim().toLowerCase())) {
                        wordTextField.setBackground(Color.GREEN);
                        phoneticLabel.setVisible(true);
                        phoneticField.setVisible(true);
                        pronounceButton.setVisible(true);
                    } else {
                        wordTextField.setBackground(Color.RED);
                        check = false;

                    }
                } else {
                    for (int i = 0; i < wordString.length(); i++) {
                        char aString = wordString.charAt(i);
                        if (" ".equals(Character.toString(aString))) {
                        } else if (textFields[i].getText().equals(Character.toString(aString))) {
                            textFields[i].setBackground(Color.GREEN);
                            phoneticLabel.setVisible(true);
                            phoneticField.setVisible(true);
                            pronounceButton.setVisible(true);
                        } else {
                            textFields[i].setBackground(Color.RED);
                            check = false;
                        }
                    }
                }
                break;
        }
        Word w = WordController.findWord(word.getWord());
        int index = WordController.words.indexOf(w);
        if (!check) {
            freqCount += 1;
            list.add(w);
        } else {
            if (freqCount > 1) {
                freqCount -= 1;
            }
            deleteWordWhenTrue();
        }
        w.setFreq(freqCount);
        WordController.words.set(index, w);
        WordController.writeWord(WordController.getDataPath());
    }

    private void deleteWordWhenTrue() {
        if (checkUseAI) {
            List<Word> rm = new ArrayList<Word>();
            for (int i = 0; i < word.getFreq(); i++) {
                rm.add(word);
            }
            list.removeAll(rm);

        } else {
            list.remove(word);
        }
        if (list.size() == 0) {
            JOptionPane.showConfirmDialog(rootPane, "Xin chúc mừng! Bạn đã hoàn thành", "Hoàn thành!", JOptionPane.OK_OPTION);
            list = WordController.copyWords();
            modeTextFields.setText(" ");
            Config.date = " ";
            Config.hashtag = null;
            Config.writeConfigFile();
            clearTextFields();
            displayGame();
        }

    }

    // Đưa ra dãy con dài nhất, dùng Longest common subsequence
    private String LCS(String wordExactly, String wordAnswer) {
        String rs = "";
        wordExactly = "E" + wordExactly;
        wordAnswer = "A" + wordAnswer;
        char[] x = wordExactly.toCharArray();
        char[] y = wordAnswer.toCharArray();
        int k = 1;
        for (int i = 1; i < wordExactly.length(); i++) {
            for (int j = k; j < wordAnswer.length(); j++) {
                if (Character.toString(x[i]).equals(Character.toString(y[j]))) {
                    rs = rs + x[i];
                    k = j + 1;
                    break;
                }
            }
        }

//        String rs = "";
//        wordExactly = "E"+wordExactly;
//        wordAnswer = "A"+wordAnswer;
//        int[][] S = new int[wordExactly.length()][wordAnswer.length()];
//        int[][] S = new int[100][100];
//        char[] x = wordExactly.toCharArray();
//        char[] y = wordExactly.toCharArray();
//        for(int i = 1; i < wordExactly.length(); i++){
//            for(int j = 1; j < wordAnswer.length(); j++){
//                S[i][j] = 0;
//            }
//	}
//	for(int i = 1; i <= wordExactly.length(); i++){
//		for(int j = 1; j <= wordAnswer.length(); j++){
//			if(Character.toString(x[i]).equals(Character.toString(y[j]))){
//				S[i][j] = S[i-1][j-1] + 1;
//                                rs = rs + x[i];
//                                System.out.println("RR "+rs);
//                                System.out.println("x" + x[i]);
//                                break;
//			}
//			else{
//				S[i][j] = Math.max(S[i][j-1],S[i-1][j]);
//			}
//		}
//	}
//        System.out.println("rss = " + rs);
        return rs.toUpperCase();
    }

    boolean isLevelEasyToHard(String gameLevel) {
        switch (gameLevelString) {
            case "Very Easy":
            case "Easy":
            case "Medium":
            case "Hard":
                return true;
            default:
                return false;
        }
    }

    public boolean isValidFormat(String value) {
        LocalDate ldt = null;
        try {
            ldt = LocalDate.parse(value, formatter);
            String result = ldt.format(formatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(value, formatter);
                String result = ld.format(formatter);
                return result.equals(value);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(value, formatter);
                    String result = lt.format(formatter);
                    return result.equals(value);
                } catch (DateTimeParseException e2) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private void refresh() {
        switch (gameLevelString) {
            case "Very Easy":
                hintField.setVisible(true);
                hintLabel.setVisible(true);
                pronounceButton.setVisible(true);
                meaningField.setVisible(true);
                meaningLabel.setVisible(true);
                phoneticLabel.setVisible(true);
                phoneticField.setVisible(true);
                break;
            case "Easy":
                hintField.setVisible(true);
                hintLabel.setVisible(true);
                pronounceButton.setVisible(true);
                meaningField.setVisible(true);
                meaningLabel.setVisible(true);
                phoneticLabel.setVisible(true);
                phoneticField.setVisible(false);
                break;
            case "Medium":
                pronounceButton.setVisible(true);
                meaningField.setVisible(true);
                meaningLabel.setVisible(true);
                phoneticLabel.setVisible(true);
                phoneticField.setVisible(false);
                hintField.setVisible(false);
                hintLabel.setVisible(false);
                break;
            case "Hard":
                pronounceButton.setVisible(true);
                meaningField.setVisible(true);
                meaningLabel.setVisible(true);
                phoneticLabel.setVisible(true);
                phoneticField.setVisible(false);
                hintField.setVisible(false);
                hintLabel.setVisible(false);
                break;
            case "Very Hard":
                pronounceButton.setVisible(false);
                meaningField.setVisible(true);
                meaningLabel.setVisible(true);
                phoneticLabel.setVisible(false);
                phoneticField.setVisible(false);
                hintField.setVisible(false);
                hintLabel.setVisible(false);
                break;
            case "Nightmare":
                pronounceButton.setVisible(false);
                meaningField.setVisible(true);
                meaningLabel.setVisible(true);
                phoneticLabel.setVisible(false);
                phoneticField.setVisible(false);
                hintField.setVisible(false);
                hintLabel.setVisible(false);
                break;
            case "Legendary":
                phoneticLabel.setVisible(false);
                phoneticField.setVisible(false);
                hintField.setVisible(false);
                hintLabel.setVisible(false);
                pronounceButton.setVisible(false);
                meaningField.setVisible(false);
                meaningLabel.setVisible(false);
                break;
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StartUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StartUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StartUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StartUI(false).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> checkLevel;
    private javax.swing.JLabel dateModified;
    private javax.swing.JLabel dateModifiedLabel;
    private javax.swing.JComboBox<String> gameLevel;
    private javax.swing.JTextArea hashtagField;
    private javax.swing.JTextArea hintField;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel imgLable;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea meaningField;
    private javax.swing.JLabel meaningLabel;
    private javax.swing.JComboBox<String> modeComboBox;
    private javax.swing.JTextField modeTextFields;
    private javax.swing.JButton nextButton;
    private javax.swing.JTextArea phoneticField;
    private javax.swing.JLabel phoneticLabel;
    private javax.swing.JButton pronounceButton;
    private javax.swing.JButton submitButton;
    private javax.swing.JPanel textPanel;
    private javax.swing.JTextField typeField;
    // End of variables declaration//GEN-END:variables
}
