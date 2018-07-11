import java.util.Random;

/**
 * The mechanisms of the game
 * https://github.com/Unknown0Shadow
 */
public class Controller {
    Repository repo;
    private static Random rand = new Random();

    Controller(Repository repo) {
        this.repo = repo;
        setDecks();
    }

    /**
     * Prepares the decks for players
     */
    private void setDecks() {
        for (int i = 0; i < 4; i++) {
            Card card1_player = new Card();
            card1_player.userCard(rand.nextInt(54));
            repo.player.addDeckCard(card1_player);
            Card card_computer = new Card();
            card_computer.userCard(rand.nextInt(54));
            repo.computer.addDeckCard(card_computer);
        }
    }

    /**
     * Dealing cards on the table
     */
    void cardDeal(Player player) {
        Card card = new Card();
        card.tableCards(rand.nextInt(23));
        player.addTableCard(card);
        System.out.println(player.getTable() + " " + player.getName() + "'s table");
        System.out.println(player.getTotal() + "||" + card + " " + player.getName());
    }

    /**
     * Returns the value of a card
     * Used for computer intelligence.
     */
    private int cardValue(Card card, int max) {
        int value = 0;
        if (card.getId() < 6 || (12<=card.getId() && card.getId()<=17))
            value = card.getValue();
        else if (42 <= card.getId() && card.getId() <= 45) {
            int temp1 = card.getValue() / 10;
            int temp2 = card.getValue() % 10;
            for (Card c : repo.computer.getTable()) {
                if (c.getValue() == temp1 || c.getValue() == temp2)
                    value += -c.getValue();
            }
            value *= 2;
        } else if (24 < card.getId() && card.getId() < 29) {
            if (repo.computer.getTotal() > 20)
                value = -card.getValue();
            else value = card.getValue();
        } else if (card.getId() == 41)
            value = repo.computer.getTable().get(repo.computer.getTableLength() - 1).getValue();
        else if (card.getId() == 36) {
            if (repo.player.stop) {
                if (repo.player.getTotal() < repo.computer.getTotal() + 1)
                    value = 1;
                else if (repo.player.getTotal() < repo.computer.getTotal() + 2)
                    value = 2;
                else if (repo.player.getTotal() >= 16) {
                    if (repo.player.getTotal() == repo.computer.getTotal() + 1)
                        value = 1;
                    else if (repo.player.getTotal() == repo.computer.getTotal() + 2)
                        value = 2;
                }
            } else if (repo.computer.getTotal() + 1 == max)
                value = 1;
            else if (repo.computer.getTotal() + 2 == max)
                value = 2;
            else if (repo.computer.getTotal() == 22)
                value = -2;
            else if (repo.computer.getTotal() == 21)
                value = -1;
        }
        return value;
    }

    /**
     * Actual functionality of the card
     */
    void cardAction(Card card, Player player) {
        if (42 <= card.getId() && card.getId() <= 45) {
            int temp1 = card.getValue() / 10;
            int temp2 = card.getValue() % 10;
            for (Card c : player.getTable()) {
                if (Math.abs(c.getValue()) == temp1 || Math.abs(c.getValue()) == temp2) {
                    c.setValue(-c.getValue());
                }
            }
            card.setValue(0);
        } else if (card.getId() == 41)
            card.setValue(player.getTable().get(player.getTableLength() - 1).getValue());
        player.addTableCard(card);
    }

    /**
     * Checks whether the player won, lost, needs to stop or it's a draw
     */
    String check() {
        if (repo.player.getTotal() == 20 || repo.player.getTableLength() == 9)
            repo.player.stop = true;
        if (repo.computer.getTotal() == 20 || repo.computer.getTableLength() == 9)
            repo.computer.stop = true;
        if (repo.player.getTotal() > 20) {
            repo.player.stop = true;
            repo.computer.stop = true;
            return "lost";
        }
        if (repo.computer.getTotal() > 20) {
            repo.computer.stop = true;
            repo.player.stop = true;
            return "win";
        }
        if (repo.player.stop && repo.computer.stop) {
            if (repo.player.getTotal() == repo.computer.getTotal())
                return "draw";
            else if (repo.player.getTotal() > repo.computer.getTotal()) {
                return "win";
            } else {
                return "lost";
            }
        }
        return "";
    }

    /**
     * Computer Intelligence
     */
    int computerAction() {
        if (repo.computer.getTableLength() == 9)
            repo.computer.stop = true;
        else {
            int computer_total = repo.computer.getTotal();
            int player_total = repo.player.getTotal();
            int temp_value;
            boolean actioned = false;
            int choice = 0;
            Card card = null;
            if (computer_total == 20)
                return -1;
            if (repo.player.stop) {
                if (player_total < computer_total && computer_total <= 20) {
                    repo.computer.stop = true;
                    return -1;
                } else if (repo.computer.getDeckLength() > 0 && player_total > 15) {
                    for (int k = 0; k < repo.computer.getDeckLength(); k++) {
                        card = repo.computer.getDeck().get(k);
                        temp_value = computer_total + cardValue(card, 20);
                        if (temp_value > player_total && temp_value <= 20) {
                            if (card.getId() <= 41 || 46 <= card.getId())
                                card.setValue(cardValue(card, 20));
                            actioned = true;
                            choice = k;
                            break;
                        }
                    }
                }
            }
            if (!actioned) {
                if (repo.computer.getDeckLength() > 0) {
                    for (int k = 0; k < repo.computer.getDeckLength(); k++) {
                        card = repo.computer.getDeck().get(k);
                        if (computer_total + cardValue(card, 20) == 20) {
                            if (card.getId() < 41 || 46 < card.getId())
                                card.setValue(cardValue(card, 20));
                            actioned = true;
                            choice = k;
                            break;
                        }
                    }
                    if (!actioned && player_total <= 19) {
                        if (computer_total == 19) {
                            repo.computer.stop = true;
                            return -1;
                        }
                        for (int k = 0; k < repo.computer.getDeckLength(); k++) {
                            card = repo.computer.getDeck().get(k);
                            temp_value = computer_total + cardValue(card, 19);
                            if (temp_value == 19) {
                                if (card.getId() < 41 || 46 < card.getId())
                                    card.setValue(cardValue(card, 19));
                                actioned = true;
                                choice = k;
                                break;
                            }
                        }
                    }
                    if (!actioned && player_total <= 18) {
                        if (computer_total == 18) {
                            repo.computer.stop = true;
                            return -1;
                        }
                        for (int k = 0; k < repo.computer.getDeckLength(); k++) {
                            card = repo.computer.getDeck().get(k);
                            temp_value = computer_total + cardValue(card, 18);
                            if (temp_value == 18) {
                                if (card.getId() < 41 || 46 < card.getId())
                                    card.setValue(cardValue(card, 18));
                                actioned = true;
                                choice = k;
                                break;
                            }
                        }
                    }
                    if (!actioned && computer_total > 20) {
                        for (int k = 0; k < repo.computer.getDeckLength(); k++) {
                            card = repo.computer.getDeck().get(k);
                            if (computer_total + cardValue(card, 20) <= 20) {
                                if (card.getId() < 41 || 46 < card.getId())
                                    card.setValue(cardValue(card, 20));
                                actioned = true;
                                choice = k;
                                break;
                            }
                        }
                    }
                }
            }
            if (actioned && card.getValue() != 0) {
                //repo.computer.addTableCard(card);
                System.out.println(repo.computer.getTotal() + "||" + card.getValue() + " " + repo.computer.getName() + " usercard");
                //repo.computer.removeCard(choice);
                cardAction(repo.computer.getDeck().get(choice), repo.computer);
                repo.computer.removeCard(choice);
                computer_total = repo.computer.getTotal();
                if (17 < computer_total && (player_total <= computer_total || player_total > 20))
                    repo.computer.stop = true;
                return choice;
            }
        }
        return -1;
    }
}