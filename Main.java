public class Main {
    public static void main(String[] args) {
        Repository repo = new Repository();
        Controller ctrl = new Controller(repo);
        View gameFrame = new View(ctrl);
        gameFrame.cardDeal(repo.player);
    }
}