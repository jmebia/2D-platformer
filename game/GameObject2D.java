package game;

import javafx.scene.shape.Rectangle;

public class GameObject2D extends Rectangle {

    private double velocityX;
    private double velocityY;
    private double gravity;

    private boolean jumping = false;
    private boolean doubleJump = false;

    public GameObject2D(double x, double y, double w, double h) {
        setX(x);
        setY(y);
        setWidth(w);
        setHeight(h);
    }

    /** ------------------ Getters and Setters ----------------- */

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isDoubleJump() {
        return doubleJump;
    }

    public void setDoubleJump(boolean doubleJump) {
        this.doubleJump = doubleJump;
    }

    /** --------------------------------------------------- */


    boolean isSteppingOn(GameObject2D obj) {

        boolean isStepping = false;

        if (getY() + getWidth() >= obj.getY() && (
                (getX() >= obj.getX() && getX() + getWidth() <= obj.getX() + obj.getWidth()) ||
                (getX() < obj.getX() + obj.getWidth() && getX() + getWidth() > obj.getX() + obj.getWidth()) ||
                (getX() < obj.getX() && getX() + getWidth() > obj.getX()) ))
            isStepping = true;

        return isStepping;
    }

}
