package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Game extends Application{

    // javafx components
    Group root;
    Scene scene;
    Canvas canvas;
    GraphicsContext graphicsContext;

    // game objects
    GameObject2D player;
    GameObject2D floor;
    GameObject2D floor2;

    // game variables
    ArrayList<String> inputX = new ArrayList<>();

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
        player.setVelocityX(0);
        player.setVelocityY(0);
        player.setGravity(0.2f);
        floor = new GameObject2D(0, 160, 300, 32);
        floor2 = new GameObject2D(128, 256, 300, 32);

        // controller
        scene.setOnKeyPressed( e -> {
            String key = e.getCode().toString();
            System.out.println(key);
            if (!inputX.contains(key)) {
                inputX.add(key);
            }
        });

        scene.setOnKeyReleased( e -> {
            String key = e.getCode().toString();
            System.out.println(key);
            if (inputX.contains(key)) {
                inputX.remove(key);
            }
        });

    }

    void update(long currentTime) {
        // update variables
        // movement
        if (inputX.contains("RIGHT")) {
            player.setVelocityX(2f);
        } else if (inputX.contains("LEFT")) {
            player.setVelocityX(-2f);
        } else player.setVelocityX(0f);

        if (!player.isSteppingOn(floor)) {
            player.setY(player.getY() + player.getVelocityY());
            player.setVelocityY(player.getVelocityY() + player.getGravity());
        } else {
            player.setY(floor.getY() - player.getHeight());
        }

        player.setX(player.getX() + player.getVelocityX());


        // update game screen
        // draw sky
        graphicsContext.setFill(Color.SKYBLUE);
        graphicsContext.fillRect(0, 0, 512, 512);

        // draw floor
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillRect(floor.getX(), floor.getY(), floor.getWidth(), floor.getHeight());
        graphicsContext.fillRect(floor2.getX(), floor2.getY(), floor2.getWidth(), floor2.getHeight());

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
