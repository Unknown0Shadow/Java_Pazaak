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
    private static Color LIGHT_BLUE;
    private static Color LIGHT_YELLOW;
    private static Color DARK_BLUE;
    private static final Font MY_FONT = new Font("YD2002", Font.BOLD, 30);
    private static final Font MY_FONT_2 = new Font("YD2002", Font.BOLD, 20);
    private static final Border BORDER_YELLOW = BorderFactory.createLineBorder(Color.yellow, 5);
    private static final Border BORDER_BLACK = BorderFactory.createLineBorder(Color.black, 5);
    private JFrame window;
    private JLabel scorePlayer;
    private JLabel scoreComputer;
    private JLabel playerName;
    private JLabel computerName;
    private Icon cardIcons[] = new Icon[47];
    private Icon otherIcons[] = new Icon[6];
    private JButton[] playerTable = new JButton[9];
    private JButton[] computerTable = new JButton[9];
    private JButton[] scoreDisplay = new JButton[6];
    private JButton[] turn = new JButton[2];
    private JButton endTurnButton;
    private JButton standButton;
    private ArrayList<JButton> computerCards = new ArrayList<>();
    private ArrayList<JButton> playerCards = new ArrayList<>();
    private MouseAdapter mouseAdapter;
    private HandlerClass handler;
    private Timer timer;

    /**
     * Constructor
     */
    View(Controller ctrl) {
        LIGHT_BLUE = new Color(35,158,218);
        LIGHT_YELLOW = new Color(222,238,17);
        DARK_BLUE = new Color(17,81,109);
        this.ctrl = ctrl;
        window = new JFrame();
        try {
            window.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Pictures/Board.bmp")))));
            int index = 0;
            BufferedImage a;
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 6; column++) {
                    a = ImageIO.read(new File("Pictures/PazaakCards.png")).getSubimage(column * 90, row * 126, 90, 126);
                    cardIcons[index++] = new ImageIcon(a);
                    if (index == 47)
                        break; // the picture has a blank spot at the end
                }
            }
            index = 0;
            for (int row = 0; row < 4; row++) {
                a = ImageIO.read(new File("Pictures/Flip.png")).getSubimage(row * 35, 0, 35, 35);
                otherIcons[index++] = new ImageIcon(a);
            }
            otherIcons[index++] = new ImageIcon(ImageIO.read(new File("Pictures/RedCircle.png")));
            otherIcons[index] = new ImageIcon(ImageIO.read(new File("Pictures/X.png")));
        } catch (IOException e) {
            System.out.println("Image not found");
        }

        scorePlayer = new JLabel();
        scoreComputer = new JLabel("0");
        playerName = new JLabel(ctrl.repo.player.getName());
        computerName = new JLabel(ctrl.repo.computer.getName());
        scorePlayer.setBounds(350, 31, 50, 35);
        scoreComputer.setBounds(430, 31, 50, 35);
        playerName.setBounds(178, 31, 130, 35);
        computerName.setBounds(522, 31, 130, 35);
        scorePlayer.setFont(MY_FONT);
        scoreComputer.setFont(MY_FONT);
        playerName.setFont(MY_FONT_2);
        computerName.setFont(MY_FONT_2);
        scorePlayer.setVerticalAlignment(SwingConstants.BOTTOM);
        scorePlayer.setHorizontalAlignment(SwingConstants.CENTER);
        scoreComputer.setVerticalAlignment(SwingConstants.BOTTOM);
        scoreComputer.setHorizontalAlignment(SwingConstants.CENTER);
        playerName.setHorizontalAlignment(SwingConstants.CENTER);
        computerName.setHorizontalAlignment(SwingConstants.CENTER);


        scorePlayer.setForeground(Color.white);
        scoreComputer.setForeground(Color.white);
        playerName.setForeground(Color.white);
        computerName.setForeground(Color.white);
        window.add(scorePlayer);
        window.add(scoreComputer);
        window.add(playerName);
        window.add(computerName);
        scorePlayer.setVisible(true);
        scoreComputer.setVisible(true);
        playerName.setVisible(true);
        computerName.setVisible(true);

        window.setBounds(0, 0, 836, 817); //x + 6  y + 32
        window.setLocationRelativeTo(null);
        window.setTitle("Pazaak");
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Building buttons

        int left_x = 10;
        int right_x = 430;
        int temp_y = 504;
        handler = new HandlerClass();
        for (int k = 0; k < 4; k++) {
            playerCards.add(new JButton(cardIcons[ctrl.repo.player.getDeck().get(k).getId()]));
            computerCards.add(new JButton(cardIcons[ctrl.repo.computer.getDeck().get(k).getId()]));
            playerCards.get(k).setBounds(left_x, temp_y, 90, 126);
            computerCards.get(k).setBounds(right_x, temp_y, 90, 126);
            right_x += 100;
            left_x += 100;
            window.add(playerCards.get(k));
            window.add(computerCards.get(k));
            playerCards.get(k).setContentAreaFilled(false);
            playerCards.get(k).setFocusPainted(false);
            computerCards.get(k).setContentAreaFilled(false);
            computerCards.get(k).setFocusPainted(false);
            playerCards.get(k).addActionListener(handler);
            //computerCards.get(k).addActionListener(handler);
        }

        temp_y = 71;
        for (int k = 0; k < 3; k++) {
            scoreDisplay[k] = new JButton();
            scoreDisplay[k + 3] = new JButton();
            scoreDisplay[k].setBounds(50, temp_y, 50, 50);
            scoreDisplay[k + 3].setBounds(730, temp_y, 50, 50);
            temp_y += 60;
            window.add(scoreDisplay[k]);
            window.add(scoreDisplay[k + 3]);
            scoreDisplay[k].setBorderPainted(false);
            scoreDisplay[k].setContentAreaFilled(false);
            scoreDisplay[k + 3].setBorderPainted(false);
            scoreDisplay[k + 3].setContentAreaFilled(false);

        }

        endTurnButton = new JButton("End Turn");
        standButton = new JButton("Stand");
        endTurnButton.setBounds(430, 683, 190, 43);
        standButton.setBounds(630, 683, 190, 43);

        // Building dynamic screen

        left_x = 110;
        temp_y = 71;
        right_x = 430;
        for (int k = 0; k < 9; k++) {
            playerTable[k] = new JButton();
            computerTable[k] = new JButton();
            playerTable[k].setBounds(left_x, temp_y, 90, 126);
            computerTable[k].setBounds(right_x, temp_y, 90, 126);
            if (k > 0 && k == 2 || k == 5) { // every third
                temp_y += 131;
                left_x = 110;
                right_x = 430;
            } else {
                left_x += 100;
                right_x += 100;
            }

            //placeholders for pictures

            playerTable[k].setContentAreaFilled(false);
            computerTable[k].setContentAreaFilled(false);
            playerTable[k].setBorderPainted(false);
            computerTable[k].setBorderPainted(false);
            window.add(playerTable[k]);
            window.add(computerTable[k]);
        }

        turn[0] = new JButton();
        turn[1] = new JButton();
        turn[0].setBounds(110, 11, 50, 50);
        turn[1].setBounds(670, 11, 50, 50);
        turn[0].setContentAreaFilled(false);
        turn[0].setBorderPainted(false);
        turn[1].setContentAreaFilled(false);
        turn[1].setBorderPainted(false);
        window.add(turn[0]);
        window.add(turn[1]);

        // Button functionality

        timer = new Timer(800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("cow");
                String reason = ctrl.check();
                if (!reason.equals("")) {
                    gamePaused(reason);
                    pause(false);
                    clear();
                    if (!ctrl.repo.player.stop)
                        cardDeal(ctrl.repo.player);
                    timer.stop();
                } else if (!ctrl.repo.computer.stop) {
                    cardDeal(ctrl.repo.computer);
                    int computerChoice = ctrl.computerAction();
                    if (computerChoice != -1) {
                        computerCards.get(computerChoice).setIcon(null);
                        computerCards.remove(computerChoice);
                        syncTable(ctrl.repo.computer);
                    }
                }
            }
        });
        mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                source.setForeground(DARK_BLUE);
                source.setBorder(BORDER_BLACK);
                if (source.equals(endTurnButton)) {
                    pause(true);
                    SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            String reason = ctrl.check();
                            if (!reason.equals("")) {
                                gamePaused(reason);
                                clear();
                            } else if (!ctrl.repo.computer.stop) {
                                cardDeal(ctrl.repo.computer);
                                Thread.sleep(800);
                            }
                            return null;
                        }

                        protected void done() {
                            if (!ctrl.repo.computer.stop) {
                                int computerChoice = ctrl.computerAction();
                                if (computerChoice != -1) {
                                    computerCards.get(computerChoice).setIcon(null);
                                    computerCards.remove(computerChoice);
                                    syncTable(ctrl.repo.computer);
                                }
                                String reason = ctrl.check();
                                if (!reason.equals("")) {
                                    gamePaused(reason);
                                    clear();
                                }
                            }
                            if (!ctrl.repo.player.stop) {
                                cardDeal(ctrl.repo.player);
                                pause(false);
                            }
                            else
                                timer.start();
                        }
                    };
                    worker.execute();
                } else if (source.equals(standButton)) {
                    pause(true);
                    ctrl.repo.player.stop = true;
                    timer.start();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                source.setForeground(LIGHT_YELLOW);
                source.setBorder(BORDER_YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton source = (JButton) e.getSource();
                source.setForeground(LIGHT_BLUE);
                source.setBorder(BORDER_BLACK);
            }
        };

        endTurnButton.addMouseListener(mouseAdapter);
        standButton.addMouseListener(mouseAdapter);

        endTurnButton.setForeground(LIGHT_BLUE);
        standButton.setForeground(LIGHT_BLUE);
        standButton.setFont(MY_FONT);
        endTurnButton.setFont(MY_FONT);

        endTurnButton.setVerticalAlignment(SwingConstants.BOTTOM);
        standButton.setVerticalAlignment(SwingConstants.BOTTOM);
        window.add(endTurnButton);
        window.add(standButton);
        endTurnButton.setContentAreaFilled(false);
        endTurnButton.setFocusPainted(false);
        standButton.setContentAreaFilled(false);
        standButton.setFocusPainted(false);
        JOptionPane.showMessageDialog(window, "                       Welcome to Pazaak!\nThe purpose of the game is to get a higher score\n       than the opponent, without exceeding 20.");
    }

    /**
     * Handling card click events
     */
    private class HandlerClass implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            JButton source = (JButton) event.getSource();
            ctrl.cardAction(ctrl.repo.player.getDeck().get(playerCards.indexOf(source)), ctrl.repo.player);
            ctrl.repo.player.removeCard(playerCards.indexOf(source));
            playerCards.remove(source);
            source.setIcon(null);
            source.setEnabled(false);
            for (JButton button : playerCards) {
                window.remove(button);
            }
            syncTable(ctrl.repo.player);
        }
    }

    /**
     * Synchronise the table
     */
    private void syncTable(Player player) {
        Card card;
        for (int k = 0; k < player.getTableLength(); k++) {
            card = player.getTable().get(k);
            if (player.getName().equals("Player")) {
                scorePlayer.setText(String.format("%d", player.getTotal()));

                if (((18 <= card.getId() && card.getId() <= 23) || card.getId() < 6) && card.getValue() < 0)
                    playerTable[k].setIcon(cardIcons[card.getId() + 6]);
                else if(6<= card.getId() && card.getId() <= 11 && card.getValue()>0)
                    playerTable[k].setIcon(cardIcons[card.getId()-6]);
                else if (30 <= card.getId() && card.getId() <= 35 && card.getValue()<0)
                    playerTable[k].setIcon(cardIcons[card.getId()+10]);
                else
                    playerTable[k].setIcon(cardIcons[card.getId()]);
            } else {
                scoreComputer.setText(String.format("%d", player.getTotal()));

                if (((18 <= card.getId() && card.getId() <= 23) || card.getId() < 6) && card.getValue() < 0)
                    computerTable[k].setIcon(cardIcons[card.getId() + 6]);
                else if(6<= card.getId() && card.getId() <= 11 && card.getValue()>0)
                    computerTable[k].setIcon(cardIcons[card.getId()-6]);
                else if (30 <= card.getId() && card.getId() <= 35 && card.getValue()<0)
                    computerTable[k].setIcon(cardIcons[card.getId()+10]);
                else
                    computerTable[k].setIcon(cardIcons[card.getId()]);
            }
        }
    }

    /**
     * Message to user
     */
    private void gamePaused(String reason) {
        switch (reason) {
            case "draw":
                JOptionPane.showMessageDialog(window, "The set is tied.");
                break;
            case "lost":
                ctrl.repo.computer.score += 1;
                scoreDisplay[ctrl.repo.computer.score + 2].setIcon(otherIcons[4]);
                JOptionPane.showMessageDialog(window, "The opponent wins the set.");
                break;
            case "win":
                ctrl.repo.player.score += 1;
                scoreDisplay[ctrl.repo.player.score - 1].setIcon(otherIcons[4]);
                JOptionPane.showMessageDialog(window, "You win the set.");
                break;
            case "defeat":
                JOptionPane.showMessageDialog(window, "You have been defeated.");
                break;
            case "beat":
                JOptionPane.showMessageDialog(window, "You have defeated your opponent!");
        }
    }

    /**
     * Deals a random card to the player's table
     */
    void cardDeal(Player player) {
        if (player.getName().equals("Player")) {
            turn[1].setIcon(otherIcons[5]);
            turn[0].setIcon(otherIcons[4]);
        } else {
            turn[0].setIcon(otherIcons[5]);
            turn[1].setIcon(otherIcons[4]);
        }
        ctrl.cardDeal(player);
        syncTable(player);
    }

    /**
     * Resets the table
     */
    private void clear() {
        if (ctrl.repo.player.score == 3) {
            pause(true);
            gamePaused("beat");
        } else if (ctrl.repo.computer.score == 3) {
            pause(true);
            gamePaused("defeat");
        } else {
            for (int k = 0; k < 9; k++) {
                playerTable[k].setIcon(null);
                computerTable[k].setIcon(null);
            }
            scorePlayer.setText("0");
            scoreComputer.setText("0");
            ctrl.repo.player.clear();
            ctrl.repo.computer.clear();
        }
    }

    /**
     * Disables any event listener for Player.
     */
    private void pause(Boolean condition){
        if(condition) {
            standButton.removeMouseListener(mouseAdapter);
            endTurnButton.removeMouseListener(mouseAdapter);
            for (JButton button : playerCards) {
                window.remove(button);
            }
        }
        else {
            standButton.addMouseListener(mouseAdapter);
            standButton.setForeground(LIGHT_BLUE);
            endTurnButton.addMouseListener(mouseAdapter);
            endTurnButton.setForeground(LIGHT_BLUE);
            for (JButton button : playerCards) {
                window.add(button);
            }
        }
    }
}