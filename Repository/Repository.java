package Repository;
import Domain.Player;
public class Repository {
    public Player player, computer;
    public Repository(){
        player = new Player("Player");
        computer = new Player("Opponent");
    }
}
