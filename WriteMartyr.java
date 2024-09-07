
package application;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
// This class implements a JavaFX application for writing martyr data to a file.
public class WriteMartyr extends Application {
	// Create an instance of MemoryTest class
	MemoryTest mt = new MemoryTest();
	Stage stage;
	Stage errorapp;
	TextField tf;

	public void start(Stage primaryStage) {

		try {
			stage=new Stage();
			
			errorapp = new Stage();

			// Open a FileOutputStream to write data to the "MartyrFile.dat" file
			FileOutputStream outFile = new FileOutputStream(new File("MartyrFile.dat"),true);
			DataOutputStream dataOutputStream = new DataOutputStream(outFile);
			
			HBox hbox = new HBox();
			BorderPane root = new BorderPane();
			BorderPane border = new BorderPane();
			Label error = new Label("");
			error.setFont(Font.font(15));
			border.setCenter(error);
			Scene scene1 = new Scene(border, 200, 100);
			errorapp.setScene(scene1);
			errorapp.setY(450);
			errorapp.setX(850);
			errorapp.setResizable(false);
			VBox vbox = new VBox();
			Text message = new Text();
			tf = new TextField();
			vbox.getChildren().addAll(tf, message);
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(18, 0, 0, 0));
			Button bt = new Button("Add To File");
			Label l = new Label("Add Martyr (Name date Of Martyrdom)");

			hbox.getChildren().addAll(l, vbox, bt);
			hbox.setAlignment(Pos.CENTER);
			hbox.setSpacing(10);
			root.setCenter(hbox);

			// Handles the add button click events
			bt.setOnAction(e -> {
				message.setText("");
				String token = tf.getText();

				// Split the input into an array using space as a delimiter
				String[] str = token.split(" ");
				tf.setText("");

				try {
					// Parses the date and performs validation
					String s1 = str[1];
					Date d1 = new Date(s1);

					if (d1.compareTo(new Date()) > 0) {
						error.setText("Enter valid date");
						errorapp.show();
					} else if (str.length == 0 || str.length == 1) {
						error.setText("Enter a valid person details");
						errorapp.show();
					} else if (exist(str[0], getData()) == false) {
						try {
							// Write the martyr's data to the file
							dataOutputStream.writeUTF(str[0] + " " + str[1] + "\n");
							message.setText("Added successfully");
							message.setFill(Color.GREEN);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					} else if (exist(str[0], getData()) == true) {
						error.setText("This person already exists");
						errorapp.show();
					}

				} catch (IllegalArgumentException ex) {
					error.setText("Enter Date correctly");
					errorapp.show();
				} catch (ArrayIndexOutOfBoundsException ex1) {
					error.setText("Enter a valid person details");
					errorapp.show();
				}

			});

			Scene scene = new Scene(root, 500, 100);

			stage.setScene(scene);
			stage.show();

			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Check if the martyr with the given name already exists in the data
	public boolean exist(String name, ArrayList<Martyr> arr) {

		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).getMartyrName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	// Retrieve martyr data from the MemoryTest class
	public ArrayList<Martyr> getData() {
		return mt.read();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
