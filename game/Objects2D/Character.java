package game.Objects2D;


public class Character extends GameObject2D {

    private boolean jumping = false;
    private boolean doubleJump = false;

    public Character(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }


}
