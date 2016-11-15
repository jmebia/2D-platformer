import javafx.scene.shape.Rectangle;

public class GameObject2D extends Rectangle {

    private double velocity;
    private double gravity;

    public GameObject2D(double x, double y, double w, double h) {
        setX(x);
        setY(y);
        setWidth(w);
        setHeight(h);
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getGravity() {
        return gravity;
    }

    boolean isSteppingOn(GameObject2D obj) {

        boolean isStepping = false;

        if (getY() + getWidth() == obj.getY() && (
                (getX() >= obj.getX() && getX() + getWidth() <= obj.getX() + obj.getWidth()) ||
                (getX() < obj.getX() + obj.getWidth() && getX() + getWidth() > obj.getX() + obj.getWidth()) ||
                (getX() < obj.getX() && getX() + getWidth() > obj.getX()) ))
            isStepping = true;

        return isStepping;
    }

}
