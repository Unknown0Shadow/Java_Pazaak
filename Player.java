import java.util.ArrayList;
class Player {
    private ArrayList<Card> table = new ArrayList<>();
    private int sum = 0;
    private ArrayList<Card> deck = new ArrayList<>();
    private String name;
    int score = 0;
    boolean stop = false;
    Player(String name) {
        this.name = name;
    }
    String getName() {
        return name;
    }
    void removeCard(int index) {
        deck.remove(index);
    }
    /*void removeCard(Card card) {
        deck.remove(card);
    }*/
    ArrayList<Card> getDeck() {
        return deck;
    }
    void setDeck(Card card) {
        deck.add(card);
    }
    ArrayList<Card> getTable() {
        return table;
    }
    void addCard(Card card) {
        table.add(card);
        calculateSum();
    }
    int getLength() {
        return table.size();
    }
    int deckLenght(){
        return deck.size();
    }
    int getSum() {
        return sum;
    }
    private void calculateSum() {
        sum = 0;
        for (Card x : table)
            sum += x.getValue();
    }
}
