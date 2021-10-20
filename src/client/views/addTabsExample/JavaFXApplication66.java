package client.views.addTabsExample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author blj0011
 */
public class JavaFXApplication66 extends Application
{

    @Override
    public void start(Stage primaryStage)
    {
        Circle background = new Circle();
        background.setRadius(50);
        background.setFill(Color.BLUE);

        Image image = new Image(getClass().getResourceAsStream("chevron-6.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        StackPane root = new StackPane();
        root.getChildren().addAll(background, imageView);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}
