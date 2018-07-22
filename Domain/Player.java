package Domain;
import java.util.ArrayList;
public class Player{
    private ArrayList<Card> table = new ArrayList<>();
    private ArrayList<Card> deck = new ArrayList<>();
    private int sum = 0;
    public int score = 0;
    private String name;
    public boolean stop = false;
    public Player(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void removeCard(int index){
        deck.remove(index);
    }
    public ArrayList<Card> getDeck(){
        return deck;
    }
    public void addDeckCard(Card card){
        deck.add(card);
    }
    public ArrayList<Card> getTable(){
        return table;
    }
    public void addTableCard(Card card){
        table.add(card);
        calculateTotal();
    }
    public int getTableLength(){
        return table.size();
    }
    public int getDeckLength(){
        return deck.size();
    }
    public int getTotal(){
        return sum;
    }
    public void clear(){
        sum = 0;
        stop = false;
        table.clear();
    }
    private void calculateTotal(){
        sum = 0;
        for(Card x: table)
            sum += x.getValue();
    }
    public void restart(){
        deck.clear();
        score = 0;
    }
}
