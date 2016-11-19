package game.Objects2D;

import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameObject2D extends Rectangle {

    private ArrayList<GameObject2D> ground;

    private double velocityX;
    private double velocityY;
    private double gravity;

    public GameObject2D(double x, double y, double w, double h) {
        setX(x);
        setY(y);
        setWidth(w);
        setHeight(h);

        ground = new ArrayList<>();
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



    /** --------------------------------------------------- */

    public void addGround(GameObject2D obj) {
        ground.add(obj);
    }

    public GameObject2D onGround() {

        GameObject2D g = null;

        for (GameObject2D obj: ground) {

            if (getVelocityY() >= 0) {
                if ( getY() + getHeight() >= obj.getY() && getY() + getHeight() <= obj.getY() + obj.getHeight() / 2 && (
                        ( getX() >= obj.getX() && getX() + getWidth() <= obj.getX() + obj.getWidth() ) ||
                                ( getX() < obj.getX() + obj.getWidth() && getX() + getWidth() > obj.getX() + obj.getWidth() ) ||
                                ( getX() < obj.getX() && getX() + getWidth() > obj.getX() ) )) {
                    g = obj;
                    break;
                }
            }
        }

        return g;
    }

}
