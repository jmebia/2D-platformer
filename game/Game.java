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
    ArrayList<String> input = new ArrayList<>();

    @Override
    public void init() throws Exception {
        super.init();

        // initialize game components here
        root = new Group();
        scene = new Scene(root);
        canvas = new Canvas(768, 512);
        graphicsContext = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        // instantiate game objects
        player = new GameObject2D(256, 256, 32, 32);
        player.setVelocityX(0);
        player.setVelocityY(0);
        player.setGravity(0.5f);
        floor = new GameObject2D(256, 288, 512, 32);
        floor2 = new GameObject2D(256, 224, 300, 32);

        player.addGround(floor);
        player.addGround(floor2);

        // controller
        scene.setOnKeyPressed( e -> {
            String key = e.getCode().toString();
            System.out.println(key);
            if (!input.contains(key)) {
                input.add(key);
            }
        });

        scene.setOnKeyReleased( e -> {
            String key = e.getCode().toString();
            System.out.println(key);
            if (input.contains(key)) {
                input.remove(key);
            }
        });

    }

    void update(long currentTime) {
        // update variables
        // movement
        if (input.contains("D")) {
            player.setVelocityX(2f);
        } else if (input.contains("A")) {
            player.setVelocityX(-2f);
        } else player.setVelocityX(0f);

        if (input.contains("W") && !player.isJumping()) {
            player.setJumping(true);
            player.setVelocityY(-9f);
            if (player.getVelocityY() < 0)
                player.setVelocityY(player.getVelocityY() + player.getGravity());
        }

        GameObject2D ground = player.onGround();

        if (ground == null) {
            player.setVelocityY(player.getVelocityY() + player.getGravity());
        } else {
            player.setY(ground.getY() - player.getHeight());
            player.setJumping(false);
        }

        // update x and y coordinates of player
        player.setX(player.getX() + player.getVelocityX());
        player.setY(player.getY() + player.getVelocityY());


        // update game screen
        // draw sky
        graphicsContext.setFill(Color.SKYBLUE);
        graphicsContext.fillRect(0, 0, 768, 512);

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
        primaryStage.setResizable(false);
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
