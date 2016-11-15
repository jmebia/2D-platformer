import javafx.scene.shape.Rectangle;

public class GameObject2D extends Rectangle {

    public GameObject2D(double x, double y, double w, double h) {
        setX(x);
        setY(y);
        setWidth(w);
        setHeight(h);
    }

    boolean isSteppingOn(GameObject2D obj) {

        boolean isStepping = false;

        if (this.getY() + this.getWidth() >= obj.getY()) isStepping = true;

        return isStepping;
    }

}
