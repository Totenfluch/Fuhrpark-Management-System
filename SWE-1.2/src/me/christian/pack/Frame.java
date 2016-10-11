package me.christian.pack;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Frame extends Application{
	private static ArrayList<DienstAuto> autos;
	public static TextArea buchungsLog;
	
	static String[] automarken = {"Audi", "BMW", "Porsche", "Ford", "Renault", "VW", "Bentley", "Mercedes", "CLA 45 AMG 4matic"};
	static String[] sekundärnamen = {"Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel", "India", "Juliet", "Kilo", "Lima"};
	
	public static void main(String[] args) {
		autos = new ArrayList<DienstAuto>();
		for(int i = 0; i < 16; i++){
			DienstAuto dienstAuto = new DienstAuto(automarken[new Random().nextInt(automarken.length)] + " " + sekundärnamen[new Random().nextInt(sekundärnamen.length)]);
			autos.add(dienstAuto);
		}
		launch(args);
	}
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane borderPane = new BorderPane();
		GridPane gridPane = new GridPane();
		borderPane.setTop(gridPane);
		Scene scene = new Scene(borderPane, 790, 700);
		buchungsLog = new TextArea("Buchungslog\n");
		buchungsLog.setStyle("-fx-font-family: Consolas;-fx-font-size: 15;-fx-text-fill: #000000;");
		borderPane.setBottom(buchungsLog);
		primaryStage.setTitle("Fuhrpark Management System");

		int autoNummber = 0;
		for(int i = 1; i< 5; i++){
			for(int x = 1; x < 5; x++){
				Label autoName = new Label(autos.get(autoNummber).getName());
				DatePicker DatePicker1 = new DatePicker();
				DatePicker1.setDayCellFactory(autos.get(autoNummber).dayCellFactory);
				DatePicker DatePicker2 = new DatePicker();
				DatePicker2.setDayCellFactory(autos.get(autoNummber).dayCellFactory);
				Button buchenButton = new Button("Buchen");
				
				VBox autoBox = new VBox();
				autoBox.setPadding(new Insets(2.0));
				autoBox.setStyle("-fx-border-color: #AED581;-fx-border-width: 2px;-fx-padding: 10;-fx-spacing: 8;");
				autoBox.getChildren().add(autoName);
				autoBox.getChildren().add(DatePicker1);
				autoBox.getChildren().add(DatePicker2);
				autoBox.getChildren().add(buchenButton);
				autoBox.setAlignment(Pos.BASELINE_CENTER);
				
				final int thisAuto = autoNummber;
				buchenButton.setOnAction(ae -> {
					autos.get(thisAuto).buchen(Timestamp.valueOf(DatePicker1.getValue().atStartOfDay()), Timestamp.valueOf(DatePicker2.getValue().atStartOfDay()));
				});
				
				gridPane.add(autoBox, i, x);
				autoNummber++;
			}
		}

		primaryStage.getIcons().add(new Image("logo.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
