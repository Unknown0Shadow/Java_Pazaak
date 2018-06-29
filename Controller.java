import java.util.Random;

/**
 * The mechanisms of the game
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
    int cardValue(Card card) {
        int value = 0;
        if (card.getId() < 13 || (17 < card.getId() && card.getId() < 24))
            value = card.getValue();
        else if (12 < card.getId() && card.getId() < 16) {
            int temp1 = card.getValue() / 10;
            int temp2 = card.getValue() % 10;
            for (Card c : repo.computer.getTable()) {
                if (c.getValue() == temp1 || c.getValue() == temp2) {
                    value += -c.getValue();
                }
            }
            value *= 2;
        } else if (card.getId() == 17)
            value = repo.computer.getTable().get(repo.computer.getTableLength() - 1).getValue();
        return value;
    }

    /**
     * Actual functionality of the card
     */
    void cardAction(Card card, Player player) {
        //if (card.getId() < 13 || (17 < card.getId() && card.getId() < 24))

        if (12 < card.getId() && card.getId() < 17) {
            int temp1 = card.getValue() / 10;
            int temp2 = card.getValue() % 10;
            for (Card c : player.getTable()) {
                if (c.getValue() == temp1 || c.getValue() == temp2) {
                    c.setValue(-c.getValue());
                }
            }
            card.setValue(0);
        } else if (card.getId() == 17)
            card.setValue(player.getTable().get(player.getTableLength() - 1).getValue());
        player.addTableCard(card);
    }

    /**
     * Checks whether the player won, lost, needs to stop or it's a draw
     * Returns False if the game ends
     */
    String check() {
        if (repo.player.getTotal() == 20 || repo.player.getTableLength() == 9)
            repo.player.stop = true;
        if (repo.computer.getTotal() == 20 || repo.computer.getTableLength() == 9)
            repo.computer.stop = true;
        if (repo.player.getTotal() > 20) {
            repo.player.stop = true;
            repo.computer.score += 1;
            return "lost";
        }
        if (repo.computer.getTotal() > 20) {
            repo.computer.stop = true;
            repo.player.score += 1;
            return "win";
        }
        if (repo.player.stop && repo.computer.stop) {
            if (repo.player.getTotal() == repo.computer.getTotal())
                return "draw";
            else if (repo.player.getTotal() > repo.computer.getTotal()) {
                repo.player.score += 1;
                return "win";
            } else {
                repo.computer.score += 1;
                return "lost";
            }
        }
        return "";
    }

    /**
     * Computer Intelligence
     */
    int computerAction() {
        int total = repo.computer.getTotal();
        if(total == 20) {
            repo.computer.stop = true;
            return -1;
        }
        boolean actioned = false;
        int choice = 0;
        Card userCard = null;
        if (repo.computer.getDeckLength() > 0) {
            for (int k = 0; k < repo.computer.getDeckLength(); k++) {
                if (total + repo.computer.getDeck().get(k).getValue() == 20) {
                    userCard = repo.computer.getDeck().get(k);
                    actioned = true;
                    choice = k;
                    break;
                }
            }
            if (!actioned) {
                for (int k = 0; k < repo.computer.getDeckLength(); k++) {
                    if (total + repo.computer.getDeck().get(k).getValue() == 19) {
                        userCard = repo.computer.getDeck().get(k);
                        actioned = true;
                        repo.computer.stop = true;
                        choice = k;
                        break;
                    }
                }
            }
            if (!actioned && total > 20) {
                for (int k = 0; k < repo.computer.getDeckLength(); k++) {
                    if (total + repo.computer.getDeck().get(k).getValue() < 19) {
                        userCard = repo.computer.getDeck().get(k);
                        actioned = true;
                        if (total + repo.computer.getDeck().get(k).getValue() == 18 || total + repo.computer.getDeck().get(k).getValue() == 17)
                            repo.computer.stop = true;
                        choice = k;
                        break;
                    }
                }
            }
            if (actioned) {
                userCard.setValue(cardValue(userCard));
                repo.computer.addTableCard(userCard);
                System.out.println(repo.computer.getTotal() + "||" + userCard.getValue() + " " + repo.computer.getName() + " usercard");
                repo.computer.removeCard(choice);
                return choice;
            }
        }
        return -1;
    }
}