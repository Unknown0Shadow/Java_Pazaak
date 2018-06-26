import java.util.Random;
import java.util.Scanner;
class Mechanisms {
    private static Random r=new Random();
    private static Card userCard;
    private static Scanner input = new Scanner(System.in);

    static void startGame(){
        Player player1 = new Player("Player1");
        Player player2 = new Player("Computer");
        Mechanisms.setDecks(player1,player2);
        while (player1.score<3 && player2.score<3) {
            player1.stop = false;
            player2.stop = false;
            player1.getTable().clear();
            player2.getTable().clear();
            while (!player1.stop || !player2.stop){
                if (!player1.stop) {
                    Mechanisms.mechanism(player1);
                    if (player1.getSum() == 20 || player1.getLength() == 9)
                        player1.stop = true;
                    if (player1.getSum() > 20)
                        break;
                }
                if (!player2.stop) {
                    Mechanisms.mechanism(player2);
                    if (player2.getSum() == 20 || player2.getLength() == 9)
                        player2.stop = true;
                    if (player2.getSum() > 20)
                        break;
                }
            }
            if (player1.getSum() > 20|| (player2.getSum() > player1.getSum()&&player2.getSum()<=20))
            {
                System.out.println(player2.getName() + " won");
                player2.score+=1;
            }
            else if (player2.getSum() > 20 || (player2.getSum() < player1.getSum()&&player1.getSum()<=20))
            {
                System.out.println(player1.getName() + " won");
                player1.score+=1;
            }
            else {
                System.out.println("Draw");
            }
            System.out.println(player1.getTable() + " " + player1.getName()+"'s table");
            System.out.println(player2.getTable() + " " + player2.getName()+"'s table");
            System.out.println(player1.getName()+" "+player1.score+" / "+player2.getName()+" "+player2.score);
        }
        if (player1.score>player2.score){
            System.out.println(player1.getName()+ " won the game");}
        else {
            System.out.println(player2.getName() + " won the game");
        }
    }


    private static void mechanism(Player player){
        Card card = new Card();
        card.tableCards(r.nextInt(23));
        player.addCard(card);
        System.out.println(player.getTable() + " " + player.getName() + "'s table");
        System.out.println(player.getSum() + "||" + card + " " + player.getName());
        if (player.getName().equals("Computer")){
            int total = player.getSum();
            boolean actioned = false;
            int choice = 0;
            if (player.deckLenght() > 0){
                for (int k = 0;k<player.deckLenght();k++){
                    if(total + player.getDeck().get(k).getValue()==20){
                        userCard = player.getDeck().get(k);
                        actioned = true;
                        choice = k;
                        break;
                    }
                }
                if (!actioned){
                    for(int k = 0;k<player.deckLenght();k++){
                        if (total + player.getDeck().get(k).getValue()==19){
                            userCard = player.getDeck().get(k);
                            actioned = true;
                            player.stop = true;
                            choice = k;
                            break;
                        }
                    }
                }
                if (!actioned && total > 20){
                    for(int k = 0;k<player.deckLenght();k++){
                        if(total + player.getDeck().get(k).getValue()<19){
                            userCard = player.getDeck().get(k);
                            actioned = true;
                            if (total + player.getDeck().get(k).getValue()==18 || total + player.getDeck().get(k).getValue()==17)
                                player.stop = true;
                            choice = k;
                            break;
                        }
                    }
                }
                if (actioned){
                    cardMeaning(player,userCard);
                    System.out.println(player.getSum() + "||" + userCard.getValue() + " " + player.getName()+ " usercard");
                    player.removeCard(choice);
                }
            }
        }
        else {
            if (player.getSum() != 20 && player.getLength() < 9) {
                System.out.println(player.getDeck());
                int choice = input.nextInt();
                if (player.deckLenght() >= choice && choice > 0) {
                    userCard = player.getDeck().get(choice - 1);

                    cardMeaning(player,userCard);
                    System.out.println(player.getSum() + "||" + userCard.getValue() + " " + player.getName());
                    player.removeCard(choice-1);
                    if (player.getSum() < 20) {
                        System.out.println("Stand?(5)");
                        choice = input.nextInt();
                    }
                }
                if (choice == 5) player.stop = true;
            }
        }
    }
    static void cardMeaning(Player player, Card card){
        if (card.getValue()==11 || card.getValue() == 22 || card.getValue() == 33 || card.getValue() ==  44 || card.getValue() == 55 || card.getValue() == 66){
            System.out.println("Negative?(-)");
            String sign=input.next();
            card.setValue(card.getValue()%10);
            if (sign.equals("-"))
                card.setValue(-card.getValue());
        }
        else if(card.getValue()==12 || card.getValue()==24 || card.getValue()==36 || card.getValue()==25){
            int temp1=card.getValue()/10;int temp2=card.getValue()%10;
            for (Card x:player.getTable()){
                if(x.getValue()==temp1||x.getValue()==-temp1||x.getValue()==temp2||x.getValue()==-temp2)
                    x.setValue(-x.getValue());
            }
            card.setValue(0);
        }
        else if (card.getValue()==0)
            card=player.getTable().get(player.getLength()-1);
        else if (card.getValue()==102)
        {
            System.out.println("1 or 2?");
            int temp1=input.nextInt();
            System.out.println("Negative?(-)");
            String sign=input.next();
            if(temp1%2==0){
                if (sign.equals("-"))
                    card.setValue(-2);
                else card.setValue(2);
            }
            else if (sign.equals("-")) card.setValue(-1);
            else card.setValue(1);
        }
        player.addCard(card);
    }
    private static void setDecks(Player player1,Player player2){
        int i=0;
        while (i<4){
            Card card1 = new Card();
            card1.userCard(r.nextInt(54));
            player1.setDeck(card1);
            Card card2 = new Card();
            card2.userCard(r.nextInt(54));
            player2.setDeck(card2);
            i++;
        }
    }
}