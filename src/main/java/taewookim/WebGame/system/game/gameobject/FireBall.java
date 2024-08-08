package taewookim.WebGame.system.game.gameobject;

public class FireBall extends Projectile {

    private final Player owner;

    public FireBall(double x, double y, double dx, double dy, double r, Player owner) {
        super(x, y, dx, dy, r);
        this.owner = owner;
    }

}
