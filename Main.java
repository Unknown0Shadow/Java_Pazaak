import Controller.Controller;
import Repository.Repository;
import View.View;

public class Main {
    public static void main(String[]args){
        Repository repo = new Repository();
        Controller ctrl = new Controller(repo);
        View view = new View(ctrl);
        view.cardDeal(repo.player);
    }
}
