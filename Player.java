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
    ArrayList<Card> getDeck() {
        return deck;
    }
    void addDeckCard(Card card) {
        deck.add(card);
    }
    ArrayList<Card> getTable() {
        return table;
    }
    void addTableCard(Card card) {
        table.add(card);
        calculateTotal();
    }
    int getTableLength() {
        return table.size();
    }
    int getDeckLength(){
        return deck.size();
    }
    int getTotal() {
        return sum;
    }
    void clear(){
        sum = 0;
        stop = false;
        table.clear();
    }
    private void calculateTotal() {
        sum = 0;
        for (Card x : table)
            sum += x.getValue();
    }
}
