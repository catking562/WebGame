package taewookim.system.game.gameobject;

public class Projectile {

    private double x;
    private double y;
    private double dx;
    private double dy;
    private double r;

    private boolean isEnd = false;

    public Projectile(double x, double y, double dx, double dy, double r) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.r = r;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public double getR() {
        return r;
    }

    public void setEnd() {
        isEnd = true;
    }

    public void update(double deltaTime) {
        x += dx*deltaTime;
        y += dy*deltaTime;
        if(x>1200||x<-120||y>840||y<-120) {
            isEnd = true;
        }
    }

}
