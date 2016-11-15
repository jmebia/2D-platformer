import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Application{

    // javafx components
    Group root;
    Scene scene;
    Canvas canvas;
    GraphicsContext graphicsContext;

    // game objects
    GameObject2D player;
    GameObject2D floor;

    @Override
    public void init() throws Exception {
        super.init();

        // initialize game components here
        root = new Group();
        scene = new Scene(root);
        canvas = new Canvas(512, 512);
        graphicsContext = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        // instantiate game objects
        player = new GameObject2D(0, 0, 32, 32);
        floor = new GameObject2D(0, 160, 300, 32);

        // controller
        scene.setOnKeyPressed( e -> {
            if (e.getCode() == KeyCode.RIGHT)
                player.setX(player.getX() + 2);
            else if (e.getCode() == KeyCode.LEFT)
                player.setX(player.getX() - 2);
        });

    }

    void update(long currentTime) {

        // update variables
        if (!player.isSteppingOn(floor))
            player.setY(player.getY() + 1);

        // update game screen
        // draw sky
        graphicsContext.setFill(Color.SKYBLUE);
        graphicsContext.fillRect(0, 0, 512, 512);

        // draw floor
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillRect(floor.getX(), floor.getY(), floor.getWidth(), floor.getHeight());

        // draw player
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(player.getX(), player.getY(), player.getWidth(), player.getHeight());

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(scene);
        primaryStage.setTitle("Platformer 2D");
        primaryStage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
            }
        }.start();

    }

    public static void main(String[] args) {
        launch(args);
    }



}
