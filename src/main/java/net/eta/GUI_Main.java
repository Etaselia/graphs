package net.eta;
import javaFX.*;
public class GUI_Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a FileChooser instance
        FileChooser fileChooser = new FileChooser();

        // Set the title for the file chooser dialog
        fileChooser.setTitle("Select a File");

        // Show the file chooser dialog
        fileChooser.showOpenDialog(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}