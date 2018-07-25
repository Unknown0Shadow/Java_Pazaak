package View;
import Controller.Controller;
import Domain.Card;
import Domain.Player;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
/**
 * The graphics of the game
 * https://github.com/Unknown0Shadow
 */
public class View {
    private Controller ctrl;
    private static final Color LIGHT_BLUE = new Color(35,158,218);
    private static final Color LIGHT_YELLOW = new Color(222,238,17);
    private static final Color DARK_BLUE = new Color(17,81,109);;
    private static final Font MY_FONT = new Font("YD2002",Font.BOLD,30);
    private static final Font MY_FONT_2 = new Font("YD2002",Font.BOLD,20);
    private static final Border BORDER_YELLOW = BorderFactory.createLineBorder(LIGHT_YELLOW,5);
    private static final Border BORDER_BLACK = BorderFactory.createLineBorder(Color.black,5);
    private JFrame window;
    private JLabel playerName, computerName, scorePlayer, scoreComputer, firstInfo, secondInfo;
    private Icon cardIcons[] = new Icon[63];
    private Icon otherIcons[] = new Icon[6];
    private JButton[] playerTable = new JButton[9];
    private JButton[] computerTable = new JButton[9];
    private JButton[] scoreDisplay = new JButton[6];
    private JButton[] round = new JButton[2];
    private JButton endTurnButton = new JButton("End Turn");
    private JButton standButton = new JButton("Stand");
    private JButton restartButton = new JButton("Restart Game");
    private ArrayList<JButton> computerCards = new ArrayList<>();
    private ArrayList<JButton> playerCards = new ArrayList<>();
    private ArrayList<JButton> switchers = new ArrayList<>();
    private MouseAdapter mouseAdapter;
    private CardHandler cardHandler = new CardHandler();
    private SwitchHandler switchHandler = new SwitchHandler();
    private Timer repeater;
    private boolean game = true;
    public View(Controller ctrl){
        this.ctrl = ctrl;
        window = new JFrame();
        try{
            window.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/Board.bmp")))));
            int index = 0;
            BufferedImage a;
            for(int row = 0; row < 9; row++){
                for(int column = 0; column < 7; column++){
                    a = ImageIO.read(new File("Pictures/PazaakCards.png")).getSubimage(column * 90, row * 126, 90, 126);
                    cardIcons[index++] = new ImageIcon(a);
                }
            } index = 0;
            for(int row = 0; row < 4; row++){
                a = ImageIO.read(new File("Pictures/Flip.png")).getSubimage(row * 35, 0, 35, 35);
                otherIcons[index++] = new ImageIcon(a);
            }
            otherIcons[index++] = new ImageIcon(ImageIO.read(new File("Pictures/RedCircle.png")));
            otherIcons[index] = new ImageIcon(ImageIO.read(new File("Pictures/X.png")));
        } catch(IOException e){
            System.out.println("Image not found");
        } scorePlayer = new JLabel("0");
        scoreComputer = new JLabel("0");
        firstInfo = new JLabel();
        secondInfo = new JLabel();
        playerName = new JLabel(ctrl.repo.player.getName());
        computerName = new JLabel(ctrl.repo.computer.getName());
        scorePlayer.setBounds(350,31,50,35);
        scoreComputer.setBounds(430,31,50,35);
        firstInfo.setBounds(20,683,390,45);
        secondInfo.setBounds(20,729,390,45);
        playerName.setBounds(178,31,130,35);
        computerName.setBounds(522,31,130,35);
        scorePlayer.setFont(MY_FONT);
        scoreComputer.setFont(MY_FONT);
        firstInfo.setFont(MY_FONT_2);
        secondInfo.setFont(MY_FONT_2);
        playerName.setFont(MY_FONT_2);
        computerName.setFont(MY_FONT_2);
        scorePlayer.setVerticalAlignment(SwingConstants.BOTTOM);
        scoreComputer.setVerticalAlignment(SwingConstants.BOTTOM);
        scorePlayer.setHorizontalAlignment(SwingConstants.CENTER);
        scoreComputer.setHorizontalAlignment(SwingConstants.CENTER);
        playerName.setHorizontalAlignment(SwingConstants.CENTER);
        computerName.setHorizontalAlignment(SwingConstants.CENTER);
        scorePlayer.setForeground(Color.white);
        scoreComputer.setForeground(Color.white);
        firstInfo.setForeground(LIGHT_BLUE);
        secondInfo.setForeground(LIGHT_BLUE);
        playerName.setForeground(Color.white);
        computerName.setForeground(Color.white);
        window.add(scorePlayer);
        window.add(scoreComputer);
        window.add(firstInfo);
        window.add(secondInfo);
        window.add(playerName);
        window.add(computerName);
        window.setBounds(0, 0, 836, 817); //x+6 y+32
        window.setLocationRelativeTo(null); //centre
        window.setTitle("Pazaak");
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Buttons *********************************************************

        int left_x = 10; int right_x = 430; int temp_y = 504;
        for(int k = 0; k < 4; k++){
            playerCards.add(new JButton(cardIcons[ctrl.repo.player.getDeck().get(k).getId()]));
            computerCards.add(new JButton(cardIcons[62]));
            playerCards.get(k).setBounds(left_x, temp_y, 90, 126);;
            computerCards.get(k).setBounds(right_x, temp_y,90,126);
            right_x += 100; left_x += 100;
            window.add(playerCards.get(k));
            window.add(computerCards.get(k));
            playerCards.get(k).setContentAreaFilled(false);
            computerCards.get(k).setContentAreaFilled(false);
            playerCards.get(k).setFocusPainted(false);
            computerCards.get(k).setFocusPainted(false);
            computerCards.get(k).setBorderPainted(false);
            playerCards.get(k).addActionListener(cardHandler);
        } temp_y = 71;
        for(int k = 0; k < 3; k++){
            scoreDisplay[k] = new JButton();
            scoreDisplay[k + 3] = new JButton();
            scoreDisplay[k].setBounds(50, temp_y,50,50);
            scoreDisplay[k + 3] .setBounds(730, temp_y,50,50);
            temp_y += 60;
            window.add(scoreDisplay[k]);
            window.add(scoreDisplay[k + 3]);
            scoreDisplay[k].setBorderPainted(false);
            scoreDisplay[k + 3].setBorderPainted(false);
            scoreDisplay[k].setContentAreaFilled(false);
            scoreDisplay[k + 3].setContentAreaFilled(false);
        } endTurnButton.setBounds(430,683,190,43);
        standButton.setBounds(630,683,190,43);
        restartButton.setBounds(430,731,390,43);
        left_x = 110; temp_y = 71; right_x = 430;
        for(int k = 0; k < 9; k++){
            playerTable[k] = new JButton();
            computerTable[k] = new JButton();
            playerTable[k].setBounds(left_x, temp_y, 90, 126);
            computerTable[k].setBounds(right_x, temp_y, 90, 126);
            if(k == 2 || k == 5){ // every third
                temp_y += 131; left_x = 110; right_x = 430;
            } else {
                left_x += 100; right_x += 100;
            } playerTable[k].setContentAreaFilled(false);
            computerTable[k].setContentAreaFilled(false);
            playerTable[k].setBorderPainted(false);
            computerTable[k].setBorderPainted(false);
            window.add(playerTable[k]);
            window.add(computerTable[k]);
        } left_x = 16; temp_y = 639;
        int temp_id;
        boolean sign, number;
        sign = number = false;
        for(int k = 0; k < 8; k++){
            switchers.add(new JButton());
            switchers.get(k).setBounds(left_x, temp_y, 35, 35);
            if(k % 2 == 1){
                left_x += 57;
                if(ctrl.repo.player.getDeck().get(k/2).getId() == 36){
                    switchers.get(k).setIcon(otherIcons[1]);
                    number = true;
                }
            } else{
                left_x += 43;
                temp_id = ctrl.repo.player.getDeck().get(k/2).getId();
                if(temp_id == 36 || (24 <= temp_id && temp_id <= 29)){
                    switchers.get(k).setIcon(otherIcons[0]);
                    sign = true;
                }
            } switchers.get(k).addActionListener(switchHandler);
            window.add(switchers.get(k));
            switchers.get(k).setContentAreaFilled(false);
            switchers.get(k).setBorderPainted(false);
            switchers.get(k).setFocusPainted(false);
        } tips(sign, number);
        round[0] = new JButton();
        round[1] = new JButton();
        round[0].setBounds(110, 11, 50, 50);
        round[1].setBounds(670, 11, 50, 50);
        round[0].setContentAreaFilled(false);
        round[1].setContentAreaFilled(false);
        round[0].setBorderPainted(false);
        round[1].setBorderPainted(false);
        window.add(round[0]);
        window.add(round[1]);
        repeater = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(game){
                    String reason = ctrl.check();
                    if(!reason.equals("")){
                        gamePaused(reason);
                        pause(false);
                        clear();
                        if(!ctrl.repo.player.stop)
                            cardDeal(ctrl.repo.player);
                        repeater.stop();
                    } else if(!ctrl.repo.computer.stop){
                        cardDeal(ctrl.repo.computer);
                        int computerChoice = ctrl.computerAction();
                        if(computerChoice != -1){
                            computerCards.get(computerChoice).setIcon(null);
                            syncTable(ctrl.repo.computer);
                        }
                    }
                } else repeater.stop();
            }
        }); mouseAdapter = new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                JButton source = (JButton) e.getSource();
                source.setForeground(DARK_BLUE);
                source.setBorder(BORDER_BLACK);
                if(source.equals(endTurnButton)){
                    pause(true);
                    SwingWorker<Void, String> worker = new SwingWorker<Void, String>(){
                        @Override
                        protected Void doInBackground() throws Exception{
                            String reason = ctrl.check();
                            if(!reason.equals("")){
                                gamePaused(reason);
                                clear();
                            } else if(!ctrl.repo.computer.stop){
                                cardDeal(ctrl.repo.computer);
                                Thread.sleep(800);
                            } return null;
                        }
                        protected void done(){
                            if(!ctrl.repo.computer.stop){
                                int computerChoice = ctrl.computerAction();
                                if(computerChoice != -1){
                                    computerCards.get(computerChoice).setIcon(null);
                                    syncTable(ctrl.repo.computer);
                                } String reason = ctrl.check();
                                if(!reason.equals("")){
                                    gamePaused(reason);
                                    clear();
                                }
                            } if(!ctrl.repo.player.stop){
                                cardDeal(ctrl.repo.player);
                                if(ctrl.repo.player.getTotal() == 20 || ctrl.repo.player.getTableLength() == 9){
                                    ctrl.repo.player.stop = true;
                                    repeater.start();
                                } else pause(false);
                            } else repeater.start();
                        }
                    }; worker.execute();
                } else if(source.equals(standButton)){
                    pause(true);
                    ctrl.repo.player.stop = true;
                    repeater.start();
                } else if(source.equals(restartButton)){
                    pause(true); // quick restart
                    pause(false);
                    game = true;
                    ctrl.repo.player.restart();
                    ctrl.repo.computer.restart();
                    clear();
                    ctrl.setDecks();
                    for(int k = 0; k < 4; k++){
                        playerCards.get(k).setIcon(cardIcons[ctrl.repo.player.getDeck().get(k).getId()]);
                        playerCards.get(k).setEnabled(true);
                        computerCards.get(k).setIcon(cardIcons[62]);
                    }
                    for(JButton point: scoreDisplay)
                        point.setIcon(null);
                    int temp_id;
                    boolean sign, number;
                    sign = number = false;
                    for(int k = 0; k < 8; k++){
                        temp_id = ctrl.repo.player.getDeck().get(k / 2).getId();
                        if(k % 2 == 1 && temp_id == 36){
                            switchers.get(k).setIcon(otherIcons[1]);
                            number = true;
                        } else if(k % 2 == 0 && (temp_id == 36 || (24 <= temp_id && temp_id <= 29))){
                            switchers.get(k).setIcon(otherIcons[0]);;
                            sign = true;
                        } else switchers.get(k).setIcon(null);
                    } tips(sign, number);
                    cardDeal(ctrl.repo.player);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e){
                JButton source = (JButton) e.getSource();
                source.setForeground(LIGHT_YELLOW);
                source.setBorder(BORDER_YELLOW);
            }
            @Override
            public void mouseExited(MouseEvent e){
                JButton source = (JButton) e.getSource();
                source.setForeground(LIGHT_BLUE);
                source.setBorder(BORDER_BLACK);
            }
        }; endTurnButton.addMouseListener(mouseAdapter);
        standButton.addMouseListener(mouseAdapter);
        restartButton.addMouseListener(mouseAdapter);
        endTurnButton.setForeground(LIGHT_BLUE);
        standButton.setForeground(LIGHT_BLUE);
        restartButton.setForeground(LIGHT_BLUE);
        standButton.setFont(MY_FONT);
        endTurnButton.setFont(MY_FONT);
        restartButton.setFont(MY_FONT);
        endTurnButton.setVerticalAlignment(SwingConstants.BOTTOM);
        standButton.setVerticalAlignment(SwingConstants.BOTTOM);
        restartButton.setVerticalAlignment(SwingConstants.BOTTOM);
        window.add(endTurnButton);
        window.add(standButton);
        window.add(restartButton);
        endTurnButton.setContentAreaFilled(false);
        standButton.setContentAreaFilled(false);
        restartButton.setContentAreaFilled(false);
        endTurnButton.setFocusPainted(false);
        standButton.setFocusPainted(false);
        restartButton.setFocusPainted(false);
        JOptionPane.showMessageDialog(window, "                       Welcome to Pazaak!\nThe purpose of the game is to get a higher score\n       than the opponent, without exceeding 20.");
    }
    /**
     * Handling card click events
     */
    private class CardHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            JButton source = (JButton) event.getSource();
            int index = playerCards.indexOf(source);
            ctrl.cardAction(ctrl.repo.player.getDeck().get(index), ctrl.repo.player);
            switchers.get(index * 2).setIcon(null);
            switchers.get(index * 2 + 1).setIcon(null);
            source.setIcon(null);
            source.setEnabled(false);
            for(JButton button: playerCards)
                if(button.getIcon() != null) // if not set, it updates and keeps the picture
                    window.remove(button);
            for(JButton button: switchers)
                if(button.getIcon() != null)
                    window.remove(button);
            syncTable(ctrl.repo.player);
        }
    }
    /**
     * Handling switch click events
     */
    private class SwitchHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            JButton source = (JButton) event.getSource();
            if(source.getIcon() != null){
                int index = switchers.indexOf(source);
                Card card = ctrl.repo.player.getDeck().get(index / 2);
                if(index % 2 == 0){
                    card.setValue(-card.getValue());
                    if(source.getIcon() == otherIcons[0]){
                        source.setIcon(otherIcons[3]);
                        if(card.getId() != 36)
                            playerCards.get(index / 2).setIcon(cardIcons[card.getId() + 6]);
                    } else {
                        source.setIcon(otherIcons[0]);
                        if(card.getId() != 36)
                            playerCards.get(index / 2).setIcon(cardIcons[card.getId()]);
                    }
                } else{
                    if(card.getValue() == 1){
                        card.setValue(2);
                        source.setIcon(otherIcons[2]);
                    } else if(card.getValue() == 2){
                        card.setValue(1);
                        source.setIcon(otherIcons[1]);
                    } else if(card.getValue() == -1){
                        card.setValue(-2);
                        source.setIcon(otherIcons[2]);
                    } else if(card.getValue() == -2){
                        card.setValue(-1);
                        source.setIcon(otherIcons[1]);
                    }
                }
            }
        }
    }
    /**
     * Synchronise the table
     */
    private void syncTable(Player player){
        Card card;
        for(int k = 0; k < player.getTableLength(); k++){
            card = player.getTable().get(k);
            if(player.getName().equals("Player")){
                scorePlayer.setText(String.format("%d", player.getTotal()));
                if(card.getId() == 36){
                    if(0 < card.getValue())
                        playerTable[k].setIcon(cardIcons[36 + card.getValue()]);
                    else playerTable[k].setIcon(cardIcons[36 + Math.abs(card.getValue()) + 2]);
                } else if((((24 <= card.getId() && card.getId() <= 29) || card.getId() < 6) && card.getValue() < 0) || (12 <= card.getId() && card.getId() <= 17 && card.getValue() > 0))
                    playerTable[k].setIcon(cardIcons[card.getId() + 6]);
                else if(46 <= card.getId() && card.getId() <= 51 && card.getValue() < 0)
                    playerTable[k].setIcon(cardIcons[card.getId() + 10]);
                else playerTable[k].setIcon(cardIcons[card.getId()]);
            } else{
                scoreComputer.setText(String.format("%d", player.getTotal()));
                if(card.getId() == 36){
                    if(0 < card.getValue())
                        computerTable[k].setIcon(cardIcons[36 + card.getValue()]);
                    else computerTable[k].setIcon(cardIcons[36 + Math.abs(card.getValue()) + 2]);
                } else if((((24 <= card.getId() && card.getId() <= 29) || card.getId() < 6) && card.getValue() < 0) || (12 <= card.getId() && card.getId() <= 17 && card.getValue() > 0))
                    computerTable[k].setIcon(cardIcons[card.getId() + 6]);
                else if(46 <= card.getId() && card.getId() <= 51 && card.getValue() < 0)
                    computerTable[k].setIcon(cardIcons[card.getId() + 10]);
                else
                    computerTable[k].setIcon(cardIcons[card.getId()]);
            }
        }
    }
    /**
     * Message to user
     */
    private void gamePaused(String reason){
        switch(reason){
            case "draw":
                JOptionPane.showMessageDialog(window,"The set is tied.");
                break;
            case "lost":
                ctrl.repo.computer.score += 1;
                scoreDisplay[ctrl.repo.computer.score + 2].setIcon(otherIcons[4]);
                JOptionPane.showMessageDialog(window,"The opponent wins the set.");
                break;
            case "won":
                ctrl.repo.player.score += 1;
                scoreDisplay[ctrl.repo.player.score - 1].setIcon(otherIcons[4]);
                JOptionPane.showMessageDialog(window,"You win the set.");
                break;
            case "defeat":
                JOptionPane.showMessageDialog(window,"You have been defeated.");
                break;
            case "beat":
                JOptionPane.showMessageDialog(window,"You have defeated your opponent!");
        }
    }
    /**
     * Deals a random card to the player's table
     */
    public void cardDeal(Player player){
        if(player.getName().equals("Player")){
            round[1].setIcon(otherIcons[5]);
            round[0].setIcon(otherIcons[4]);
        } else{
            round[0].setIcon(otherIcons[5]);
            round[1].setIcon(otherIcons[4]);
        } ctrl.cardDeal(player);
        syncTable(player);
        if(player.getTotal() == 20)
            player.stop = true;
    }
    /**
     * Resets the table
     */
    private void clear(){
        if(ctrl.repo.player.score == 3 && game){
            pause(true);
            gamePaused("beat");
            game = false;
            restartButton.addMouseListener(mouseAdapter);
        } else if(ctrl.repo.computer.score == 3 && game){
            pause(true);
            gamePaused("defeat");
            game = false;
            restartButton.addMouseListener(mouseAdapter);
        } else{
            for(int k = 0; k < 9; k++){
                playerTable[k].setIcon(null);
                computerTable[k].setIcon(null);
            } scorePlayer.setText("0");
            scoreComputer.setText("0");
            ctrl.repo.player.clear();
            ctrl.repo.computer.clear();
        }
    }
    /**
     * Sets tips
     */
    private void tips(boolean sign, boolean number){
        firstInfo.setText(null);
        secondInfo.setText(null);
        if(number){
            firstInfo.setText("<html>Click 1 or 2 to change the value of the special card to 1 or 2.</html>");
            secondInfo.setText("<html>Click + or - to change the value of the card in negative or positive.</html>");
        } else if(sign) firstInfo.setText("<html>Click + or - to change the value of the card in negative or positive.</html>");
    }
    /**
     * Disables any event listener for player
     */
    private void pause(boolean condition){
        if(condition){
            endTurnButton.removeMouseListener(mouseAdapter);
            standButton.removeMouseListener(mouseAdapter);
            restartButton.removeMouseListener(mouseAdapter);
            for(JButton button: playerCards)
                window.remove(button);
            for(JButton button: switchers)
                window.remove(button);
        } else{
            endTurnButton.addMouseListener(mouseAdapter);
            standButton.addMouseListener(mouseAdapter);
            restartButton.addMouseListener(mouseAdapter);
            endTurnButton.setForeground(LIGHT_BLUE);
            standButton.setForeground(LIGHT_BLUE);
            restartButton.setForeground(LIGHT_BLUE);
            for(JButton button: playerCards)
                window.add(button);
            for(JButton button: switchers)
                window.add(button);
        }
    }
}