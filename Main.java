package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    // Instantiate classes for martyr list and memory test windows
    MemoryTest m = new MemoryTest();
    WriteMartyr wm = new WriteMartyr();

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Create root layout (BorderPane)
        BorderPane root = new BorderPane();

        // Create container for buttons (VBox)
        VBox vbox = new VBox();

        // Create and label buttons
        Button bt1 = new Button("Create Martyr List Window");
        Button bt2 = new Button("Memory Test Window");

        // Add buttons to the VBox
        vbox.getChildren().addAll(bt1, bt2);

        // Center and space the buttons within the VBox
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        // Set the VBox as the center of the BorderPane
        root.setCenter(vbox);

        // Attach event handlers to buttons
        bt1.setOnAction(e -> {
            // Open the martyr list window
            wm.start(primaryStage);
        });

        bt2.setOnAction(e -> {
            // Open the memory test window
            m.start(primaryStage);
        });

        // Create and set the scene
        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);

        // Set window title
        primaryStage.setTitle("The War on Gaza");

        // Show the window
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
