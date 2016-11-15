import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application{


    @Override
    public void init() throws Exception {
        super.init();
        // initialize game components here

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        new AnimationTimer() {
            @Override
            public void handle(long now) {

                // update()

            }
        }.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
