package application;


import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MemoryTest extends Application  {



	private ArrayList<Martyr> nameList=new ArrayList<>();
	private Label nameLabels[]= new Label[5];
	private TextField first=new TextField();
	private TextField second=new TextField();
	private Button submit=new Button();
	private Button clear=new Button();
	private Label response=new Label();
	Date date=new Date();
	BorderPane root;
	TilePane tp;
	Stage stage;




	@Override
	public void start(Stage primaryStage) {
		try {
			root =new BorderPane();
			//method to read data from binary file 
			read();
			//show center contents
			setCenter();
			//show Top contents
			setTop();
			//show Bottom contents
			setBottom();

			
			Scene scene=new Scene(root,1600,800);
			primaryStage.setScene(scene);
			primaryStage.setX(200);
			primaryStage.setY(100);
			primaryStage.show();



		} catch(Exception e) {
			System.out.println("Error while runing the app");
		}
	}


	public void setBottom() {
		//initialize vbox
		VBox vbox=new VBox();
		//initialize hboxes
		HBox hbox1=new HBox();
		HBox hbox2=new HBox();
		HBox hbox3=new HBox();
		//initialize textfield
		TextField tf1=new TextField();
		TextField tf2=new TextField();
		hbox1.getChildren().addAll(tf1,nameLabels[3]=new Label("martyred before:"),tf2);
		hbox1.setAlignment(Pos.CENTER);
		hbox1.setPadding(new Insets(15));
		hbox1.setSpacing(15);

		//initialize buttons
		Button submit=new Button("Submit");
		Button clear=new Button("Clear");
		//initialize combo box
		String []colors= {"Red","Green","Blue","Yellow","Default"};
		ComboBox combo_box =new ComboBox(FXCollections.observableArrayList(colors));
		combo_box.getSelectionModel().select(colors[4]);
		hbox2.getChildren().addAll(submit,clear,combo_box);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.setPadding(new Insets(15));
		hbox2.setSpacing(15);

		
		hbox3.setAlignment(Pos.CENTER);
		hbox3.getChildren().add(response);

		vbox.getChildren().addAll(hbox1,hbox2,hbox3);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(15));


		root.setBottom(vbox);
		// Handles the comboBox 
		combo_box.setOnAction(e ->{
			if(combo_box.getSelectionModel().getSelectedItem().equals("Red")) {
				root.setStyle("-fx-background-color:red");
			}
			else if(combo_box.getSelectionModel().getSelectedItem().equals("Green")) {
				root.setStyle("-fx-background-color:green");
			}
			else if(combo_box.getSelectionModel().getSelectedItem().equals("Blue")) {
				root.setStyle("-fx-background-color:blue");
			}
			else if(combo_box.getSelectionModel().getSelectedItem().equals("Yellow")) {
				root.setStyle("-fx-background-color:yellow");
			}
			else if(combo_box.getSelectionModel().getSelectedItem().equals("Default")) {
				root.setStyle("-fx-background-color:f4f4f4");
			}
		});
		// Handles the clear button click events
		clear.setOnAction(e ->{
			tf1.setText(" ");
			tf2.setText(" ");
			response.setText(" ");
		});
		// Handles the submit button click events
		submit.setOnAction(e ->{
			if(tf1.getText().trim().equals("") && tf2.getText().trim().equals("")) {
				response.setText("Enter names in both boxes. Then press Submit");
			}
			else if(tf1.getText().trim().equalsIgnoreCase(tf2.getText().trim())) {
				response.setText("You entered the same names. Try again.");
			}
			else if(inList(tf1.getText().trim()) == true && inList(tf2.getText().trim()) == true ) {

				int i1=martyrId(tf1.getText().trim());
				int i2=martyrId(tf2.getText().trim());

				String s1= nameList.get(i1).getDateOfMartyrdom();
				Date d1=new Date(s1);

				String s2= nameList.get(i2).getDateOfMartyrdom();
				Date d2=new Date(s2);

				if(d1.compareTo(d2) < 0) {
					response.setText("You are Correct");


				}
				else if(d1.compareTo(d2) > 0){
					response.setText("Wrong. Try again.");

				}
			}
			else if(inList(tf1.getText().trim())==true && inList(tf2.getText().trim()) == false ) {
				response.setText("Second entry not in name list – check spelling.");
			}
			else if(inList(tf1.getText().trim())==false && inList(tf2.getText().trim()) == false ) {
				response.setText("Neither entry is in the name list.");
			}
			else {
				response.setText("First entry not in name list – check spelling.");
			}


		});
	}

	public void setTop() {
		VBox vbox=new VBox();
		Font font=new Font("Arial",30);
		nameLabels[0]=new Label("Test your memory");
		nameLabels[0].setFont(Font.font("Arial",FontWeight.BOLD,30));
		nameLabels[0].setWrapText(true);
		nameLabels[1]=new Label("Hey, my friend! Test your memory to see if you remember who was martyred before.");
		nameLabels[1].setFont(Font.font(25));
		nameLabels[1].setWrapText(true);
		nameLabels[2]=new Label("Pick two Martyr names from the following list, enter them in the boxes in the correct order (date of death), and then press the Submit button.");
		nameLabels[2].setFont(Font.font(25));
		nameLabels[2].setWrapText(true);


		vbox.getChildren().addAll(nameLabels[0],nameLabels[1],nameLabels[2]);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(15);
		vbox.setPadding(new Insets(100,15,15,15));
		root.setTop(vbox);
	}

	public void setCenter() {

		tp =new TilePane();
		for(int i=0;i<nameList.size();i++) {
			Label label =new Label(nameList.get(i).getMartyrName());
			label.setFont(Font.font(20));
			tp.getChildren().add(label);
			tp.setVgap(10);
			tp.setHgap(10);
			tp.setPadding(new Insets(0,100,0,100));
			tp.setAlignment(Pos.CENTER);
			root.setCenter(tp);
		}
	}
	//method to read data from binary file
	public ArrayList<Martyr> read(){
		try {
			FileInputStream fis=new FileInputStream("MartyrFile.dat");
			DataInputStream dis=new DataInputStream(fis);
			try {
				while(dis.available()>0) {
					String line = dis.readUTF();
					String []tokens =line.split(" ");
					nameList.add(new Martyr(tokens[0],tokens[1]));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		return nameList;

	}
	//method to return the index for the martyr
	public int martyrId(String name) {
		for(int i=0;i<nameList.size();i++) {
			if(nameList.get(i).getMartyrName().equalsIgnoreCase(name)) {
				return i;
			}

		}
		return -1;
	}
	//method to check if the person is in the binary file
	public boolean inList(String name) {
		for(int i=0;i<nameList.size();i++) {
			if(nameList.get(i).getMartyrName().equalsIgnoreCase(name)) {
				return true;
			}

		}
		return false;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public ArrayList<Martyr> getNameList() {
		return nameList;
	}




}
