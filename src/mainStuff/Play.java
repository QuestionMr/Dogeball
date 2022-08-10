package mainStuff;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File; 
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;
import miscStuff.Config;
import miscStuff.CustomButton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Dimension;


public class Play extends JFrame implements ActionListener {

    CardLayout card = new CardLayout();
    CardLayout card2 = new CardLayout();
    FlowLayout flow = new FlowLayout();
    BorderLayout border = new BorderLayout();
    BorderLayout border2 = new BorderLayout();
    GridLayout grid = new GridLayout(3, 2, 10, 10);
    GridBagLayout gridBag = new GridBagLayout();
    ImageIcon infoImage = new ImageIcon("assets/power_up.png");


    JPanel startPanel = new JPanel();
    JPanel playPanel = new JPanel();
    JPanel titleScreen = new JPanel();
    JPanel optionPanel = new JPanel();
    JPanel infoPanel = new JPanel();
    JPanel instructionPanel = new JPanel();

    JPanel p1W = new JPanel();
    JPanel p2W = new JPanel();

    ImageIcon str = new ImageIcon("assets/START.png");
    ImageIcon qut = new ImageIcon("assets/QUIT.png");
    ImageIcon info = new ImageIcon("assets/info1.png");
    ImageIcon str2 = imgResizeConvert(str, 250, 100);
    ImageIcon qut2 = imgResizeConvert(qut, 250, 100);
    ImageIcon info2 = imgResizeConvert(info, 10, 10);
    

    CustomButton startButton = new CustomButton(str2, 250, 100, Color.WHITE, Color.BLACK);
    CustomButton quitButton = new CustomButton(qut2, 250, 100, Color.WHITE, Color.BLACK);
    CustomButton infoButton = new CustomButton(info, 50, 50, Color.WHITE, Color.BLACK);
    CustomButton optionButton = new CustomButton("OPTION", 250, 100, Color.WHITE, Color.BLACK);
    CustomButton firstButton = new CustomButton("1", 30, 30, Color.WHITE, Color.BLACK);
    CustomButton secondButton = new CustomButton("2", 30, 30, Color.WHITE, Color.BLACK);
    CustomButton backButton = new CustomButton("BACK", 150, 30, Color.WHITE, Color.BLACK);
    CustomButton backButton2 = new CustomButton("BACK", 150, 30, Color.WHITE, Color.BLACK);
    CustomButton backButton3 = new CustomButton("BACK", 150, 30, Color.WHITE, Color.BLACK);
    CustomButton saveButton = new CustomButton("SAVE", 250, 100, Color.WHITE, Color.BLACK);

    JCheckBox effectCheckBox = new JCheckBox("Effects on");
    JCheckBox difficultyCheckBox = new JCheckBox("Test");

    JLabel numOfPlayerLabel;
    JLabel scoreLabel;

    int numOfPlayer = 1;
    int maxScore = 1;
    Config config = new Config(this);

    MainGameComponent gameM;


    public Play() {
        UIManager.getLookAndFeelDefaults().put("Panel.background", Color.BLACK);
        setTitle("Dogeball");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        this.setSize(1100, 680);
        infoPanel.setLayout(card2);
        p1W.setLayout(new BorderLayout());
        p2W.setLayout(new BorderLayout());

        try {                
            BufferedImage instr1 = ImageIO.read(new File("assets/power_up.png"));
            JLabel instrLabel = new JLabel(new ImageIcon(instr1)); 
            infoPanel.add(instrLabel, "inf1");

            BufferedImage instr2 = ImageIO.read(new File("assets/key_movement.png"));
            JLabel instr2Label = new JLabel(new ImageIcon(instr2)); 
            infoPanel.add(instr2Label, "inf2");

            BufferedImage title = ImageIO.read(new File("assets/title.png"));
            JLabel titleLabel = new JLabel(new ImageIcon(title)); 
            titleScreen.add(titleLabel, "TITLE");

            BufferedImage p1Wins = ImageIO.read(new File("assets/1p wins.png"));
            JLabel p1Label = new JLabel(new ImageIcon(p1Wins)); 
            p1W.add(p1Label);

            BufferedImage p2Wins = ImageIO.read(new File("assets/2p wins.png"));
            JLabel p2Label = new JLabel(new ImageIcon(p2Wins)); 
            p2W.add(p2Label);
          

        } 
        catch (IOException ex) {
              // handle exception...
         }

         firstButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // card.next(getContentPane());
                card2.show(infoPanel, "inf1");
            }
        });

        secondButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // card.next(getContentPane());
                card2.show(infoPanel, "inf2");
            }
        });
        
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // card.next(getContentPane());
                card.show(playPanel, "2");
            }
        });

        backButton2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // card.next(getContentPane());
                card.show(playPanel, "2");
            }
        });

        backButton3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // card.next(getContentPane());
                card.show(playPanel, "2");
            }
        });

        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // card.next(getContentPane());
                gameM = new MainGameComponent();
                gameM.setPlay(returnThis());
                playPanel.add(gameM);

                card.next(playPanel);
                gameM.beginGame();
            }
        });

        quitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        optionButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                card.show(playPanel, "3");
            }
        });

        infoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                card.show(playPanel, "1");
            }
        });

        JPanel flowPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel infoContainer = new JPanel();
        JPanel infoButtonPanel = new JPanel();
        
        startPanel.setLayout(border);
        topPanel.setLayout(border2);
        topPanel.add(infoButton, BorderLayout.EAST);

        flowPanel.add(optionButton);
        flowPanel.add(startButton);
        flowPanel.add(quitButton);

        //START-OPTION-QUIT
        startPanel.add(flowPanel, BorderLayout.SOUTH);
        startPanel.add(topPanel, BorderLayout.NORTH);
        titleScreen.setBackground(Color.BLACK);
        startPanel.add(titleScreen, BorderLayout.CENTER);

        //OPTION
        //optionPanel.add(difficultyCheckBox);
        //optionPanel.add(effectCheckBox);
        radioButtonAdd();
        
        //INFO
        infoButtonPanel.add(firstButton);
        infoButtonPanel.add(secondButton);
        infoButtonPanel.add(backButton);

        infoContainer.add(infoButtonPanel);
        infoContainer.add(infoPanel);

        //WIN - LOSE
        p1W.setBackground(Color.BLACK);
        p2W.setBackground(Color.BLACK);
        JPanel backPanel2 = new JPanel();
        backPanel2.add(backButton2);

        JPanel backPanel3 = new JPanel();
        backPanel3.add(backButton3);
        p1W.add(backPanel2, BorderLayout.SOUTH);
        p2W.add(backPanel3, BorderLayout.SOUTH);


        //PLAY/MAIN
        playPanel.setLayout(card);
        playPanel.setBackground(Color.BLACK);

        infoContainer.setBackground(Color.BLACK);
        startPanel.setBackground(Color.BLACK);

        playPanel.add(p1W, "p1");
        playPanel.add(p2W, "p2");
        playPanel.add(optionPanel, "3");
        playPanel.add(infoContainer, "1");
        playPanel.add(startPanel,"2");
        

        playPanel.setBackground(Color.BLACK);
        add(playPanel);
        card.show(playPanel, "2");

    }
    public int getNumOfPlayer(){
        return numOfPlayer;
    }

   public int getMaxScore(){
    return maxScore;
   }
    private void radioButtonAdd(){
        Font f = new Font("Arial", Font.BOLD, 20);
        optionPanel.setLayout(new BorderLayout());
        numOfPlayerLabel = new JLabel("", JLabel.CENTER);
        numOfPlayerLabel.setSize(350, 100);
        numOfPlayerLabel.setText("Số lượng người chơi:");
        numOfPlayerLabel.setFont(f);

        scoreLabel = new JLabel("", JLabel.CENTER);
        scoreLabel.setSize(350, 100);
        scoreLabel.setText("Số điểm để thắng:");
        scoreLabel.setFont(f);


        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        final JRadioButton oneP = new JRadioButton("Một người chơi", true);
        oneP.setFont(f);

        final JRadioButton twoP = new JRadioButton("Hai người chơi");
        twoP.setFont(f);

        final JRadioButton zeroP = new JRadioButton("Máy vs máy");
        zeroP.setFont(f);

        oneP.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                       if (e.getStateChange() == 1) numOfPlayer = 1;
            }
        });

        twoP.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) numOfPlayer = 0;
            }
        });

        zeroP.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1)
                    numOfPlayer = 2;
            }
        });        

        SpinnerModel model = new SpinnerNumberModel(1, 1, 10, 1);     
        JSpinner spinner = new JSpinner(model);
        Dimension d = new Dimension(100, 100);
        spinner.setPreferredSize(d);
        spinner.setFont(f);

        saveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                maxScore = (Integer) spinner.getValue();
                config.saveConfig();
                card.show(playPanel, "2");
            }
        });

      

  
        ButtonGroup group = new ButtonGroup();
        group.add(oneP);        
        group.add(twoP);
        group.add(zeroP);
        controlPanel.add(oneP);
        controlPanel.add(twoP);
        controlPanel.add(zeroP);
        JPanel spinPanel = new JPanel();
        spinPanel.add(spinner);

        JPanel vertPanel = new JPanel();
        vertPanel.setLayout(new BoxLayout(vertPanel, BoxLayout.Y_AXIS));

        JPanel tempP = new JPanel();
        tempP.add(saveButton);
        vertPanel.setBackground(Color.GRAY);
    
        vertPanel.add(numOfPlayerLabel);
        vertPanel.add(controlPanel);
        vertPanel.add(Box.createVerticalStrut(0));

        vertPanel.add(scoreLabel);
        vertPanel.add(spinPanel);

        optionPanel.add(vertPanel, BorderLayout.CENTER);


        optionPanel.add(tempP, BorderLayout.SOUTH);

    }
    public void returnToGame1(){
        playPanel.remove(gameM);
        card.show(playPanel, "p1");
   }

   public void returnToGame2(){
        playPanel.remove(gameM);
        card.show(playPanel, "p2");
    }

    public void returnToGameMain(){
        playPanel.remove(gameM);
        card.show(playPanel, "2");
    }
    public static void main (String[] args) throws IOException {
        new Play();
    }

    public Play returnThis(){
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
    
    public ImageIcon imgResizeConvert(ImageIcon icon, int w, int h){
        Image iconN = icon.getImage();
        Image newIconN = iconN.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newIconN);
        return icon;
    }
}
