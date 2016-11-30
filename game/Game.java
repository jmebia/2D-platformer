package game;

import game.Objects2D.Character;
import game.Objects2D.GameObject2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Game extends Application{

    // javafx components
    private Group root;
    private Scene scene;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private PerspectiveCamera camera;
    private Label labelFrameRate;
    private Label labelPlayerHeight;

    // game objects
    private Character player;
    private GameObject2D floor;
    private GameObject2D floor2;

    // game variables
    private final double playerHeight = 64;
    private final double playerWidth = 32;
    private final double crouchHeight = 48;
    private GameObject2D ground;

    // input store
    private ArrayList<String> keyboardInput = new ArrayList<>();
    private ArrayList<String> mouseInput = new ArrayList<>();

    // frame rate meter
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0 ;
    private boolean arrayFilled = false ;

    @Override
    public void init() throws Exception {
        super.init();

        // initialize game components here
        root = new Group();
        scene = new Scene(root, 768, 512);
        scene.setFill(Color.BLACK);
        canvas = new Canvas(1000, 1000);
        labelFrameRate = new Label();
        labelPlayerHeight = new Label();
        root.getChildren().addAll(canvas, labelFrameRate);
        graphicsContext = canvas.getGraphicsContext2D();
        labelFrameRate.toFront();

        // set up camera
        camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(20);
        scene.setCamera(camera);

        // instantiate game objects
        player = new Character(256, 224, playerWidth, playerHeight);
        player.setVelocityX(0);
        player.setVelocityY(0);
        player.setGravity(0.45f);
        floor = new GameObject2D(256, 448, 512, 32);
        floor2 = new GameObject2D(256, 512, 300, 32);

        player.addGround(floor);
        player.addGround(floor2);

        // controller
        scene.setOnKeyPressed( e -> {
            String key = e.getCode().toString();
            System.out.println(key);
            if (!keyboardInput.contains(key)) {
                keyboardInput.add(key);
            }
        });

        scene.setOnKeyReleased( e -> {
            String key = e.getCode().toString();
            System.out.println(key);
            if (keyboardInput.contains(key)) {
                keyboardInput.remove(key);
            }
        });

        scene.setOnMousePressed( e -> {
            String button = e.getButton().toString();
            System.out.println(button);
            if (!mouseInput.contains(button)) {
                mouseInput.add(button);
            }
        });

        scene.setOnMouseReleased( e -> {
            String button = e.getButton().toString();
            System.out.println(button);
            if (mouseInput.contains(button)) {
                mouseInput.remove(button);
            }
        });

    }

    /** UPDATE */
    void update(long currentTime) {

        // check frame rate
        long oldFrameTime = frameTimes[frameTimeIndex] ;
        frameTimes[frameTimeIndex] = currentTime ;
        frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
        if (frameTimeIndex == 0) {
            arrayFilled = true ;
        }
        if (arrayFilled) {
            long elapsedNanos = currentTime - oldFrameTime ;
            long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
            double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
            labelFrameRate.setText(String.format("FPS: %.3f", frameRate));
        }

        // movement
        if (!player.isJumping()) {
            if (keyboardInput.contains("D")) {
                if (player.getHeight() == crouchHeight)
                    player.setVelocityX(1f);
                else if (player.getVelocityX() < 4)
                    player.setVelocityX(player.getVelocityX() + 0.5f);
            } else if (keyboardInput.contains("A")) {
                if (player.getHeight() == crouchHeight)
                    player.setVelocityX(-1f);
                else if (player.getVelocityX() > -4)
                    player.setVelocityX(player.getVelocityX() - 0.5f);
            } else
                player.setVelocityX(0);
        }

        // after jump
        if (player.isJumping()) {
            if (player.getVelocityX() > 0) player.setVelocityX(player.getVelocityX() - 0.1f);
            else if (player.getVelocityX() < 0) player.setVelocityX(player.getVelocityX() + 0.1f);
        }

        // fall
        if (player.getHeight() == crouchHeight) {
            if (keyboardInput.contains("SPACE")) {
                keyboardInput.remove("SPACE");
                player.setY(player.getY() + 16);
            }
        } else {
            // jump
            if (keyboardInput.contains("SPACE") && !player.isJumping()) {
                keyboardInput.remove("SPACE");
                player.setJumping(true);
                player.setVelocityY(-9f);
                if (player.getVelocityY() < 0 && player.getVelocityY() != -4)
                    player.setVelocityY(player.getVelocityY() + player.getGravity());
            }
        }

        // check ground
        ground = player.onGround();
        if (ground == null)
            player.setVelocityY(player.getVelocityY() + player.getGravity());
        else {
            player.setVelocityY(0);
            player.setJumping(false);
            player.setY( ground.getY() - player.getHeight() );

            // crouch
            if (keyboardInput.contains("S"))  {
                player.setHeight(crouchHeight);
                player.setY( ground.getY() - player.getHeight() );
            }
            else {
                player.setHeight(playerHeight);
                player.setY( ground.getY() - player.getHeight() );
            }

        }


        if (mouseInput.contains("PRIMARY")) {
            player.setVelocityX(6);
        }

        // update x and y coordinates of player
        player.setX(player.getX() + player.getVelocityX());
        player.setY(player.getY() + player.getVelocityY());

        // update camera
        camera.setTranslateX(player.getX());
        if (player.getVelocityY() != 0)
            camera.setTranslateY(player.getY());

        // move labelFrameRate with camera
        labelFrameRate.setTranslateX(camera.getTranslateX() - 256);
        labelFrameRate.setTranslateY(camera.getTranslateY() - 160);

        // update game screen
        // draw sky
        graphicsContext.setFill(Color.SKYBLUE);
        graphicsContext.fillRect(0, 0, 1000, 1000);

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
        primaryStage.setTitle("2D Platformer"); // title displayed on game window
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
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
