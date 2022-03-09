import javafx.scene.layout.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javafx.scene.control.Button;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class OrderPane extends VBox{
	private HBox topHBox;
	private HBox bottomHBox;
	private GridPane gridPane;
	private Label suggestions;
	private Label languageDetected;
	private Label customerLanguage;
	private Label customerLanguageAbbrev;
	private ComboBox<String> greetingsCombo;
	private Label partnerLanguageAbbrev;
	private Label partnerTranslation;
	private Label customerTranslation;
	private Label phoneticTranslation;
	private Button audioButton;
	private Button suggestion1;
	private Button suggestion2;
	private Button suggestion3;
	private Label liveTranslationLabel;
	private TextArea liveTranslationTextArea;
	private String audioFile;
	private final String[] partnerGreetings = {"Welcome to Starbucks", "Hi, how are you?"};
	private final String[] customerGreetings = {"Bienvenido a starbucks", "Hola, cómo estás?"};
	private final String[] phoneticGreetings = {"phonetic here","olaˈkomo ɛsˈtas"};
	
	//constructor
	public OrderPane()
	{
		//"Language Detection: Spanish" is placed into HBox
		topHBox = new HBox();
		languageDetected = new Label("Language Detected: ");
		customerLanguage = new Label("Spanish"); //determined by Azure language detection
		topHBox.getChildren().addAll(languageDetected, customerLanguage);
		
		//initialize greetings combo box
		greetingsCombo = new ComboBox<String>();
		greetingsCombo.setValue("Greet the Customer!");
		greetingsCombo.getItems().addAll(partnerGreetings);
		greetingsCombo.setOnAction(new GreetingHandler());
		
		//initialize all nodes to go into middle grid pane
		gridPane = new GridPane();
		partnerLanguageAbbrev = new Label("EN "); 
		customerLanguageAbbrev = new Label("SP "); //method
		audioButton = new Button("listen");
		partnerTranslation = new Label();
		customerTranslation = new Label();
		phoneticTranslation = new Label();
		
		//place nodes in grid pane
		gridPane.add(partnerLanguageAbbrev, 0, 0);
		gridPane.add(partnerTranslation, 1, 0);
		gridPane.add(customerLanguageAbbrev, 0, 1);
		gridPane.add(customerTranslation, 1, 1);
		gridPane.add(phoneticTranslation, 1, 2);
		gridPane.add(audioButton, 2, 2);
		
		//live translation area
		liveTranslationLabel = new Label("Live Translation ("+ customerLanguageAbbrev.getText() + "to " + partnerLanguageAbbrev.getText() +")");
		liveTranslationTextArea = new TextArea(); //method to display text here
		
		//suggestion area
		suggestions = new Label("Suggestions for Phrases:");
		suggestion1= new Button("milk"); //implement kelly's method here
		suggestion2= new Button("size");
		suggestion3= new Button("espresso");
		
		//place buttons in HBox
		bottomHBox = new HBox();
		bottomHBox.getChildren().addAll(suggestion1, suggestion2, suggestion3);
		
		//set all components in VBox
		this.getChildren().addAll(topHBox, greetingsCombo, gridPane, liveTranslationLabel, liveTranslationTextArea, suggestions, bottomHBox);
	
		/*
		audio= new Button("audio"); //speaker icon button hopefully
		audio.setOnAction(new ButtonHandler());
		audioFile = "C:\\Users\\Movi\\Documents\\asuStarbucksHackathon\\src\\milkSpanish.wav";
		//create an object for this instance of what type of milk
		setCenter(audio);
		*/
	}
	
	private class GreetingHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent event)
		{
			partnerTranslation.setText(greetingsCombo.getValue());
			customerTranslation.setText(customerGreetings[Arrays.asList(partnerGreetings).indexOf(greetingsCombo.getValue())]);
			phoneticTranslation.setText(phoneticGreetings[Arrays.asList(partnerGreetings).indexOf(greetingsCombo.getValue())]);			
		}
	}
	
	private class ButtonHandler implements EventHandler<ActionEvent> 
	{
		public void handle(ActionEvent event) 
		{
			if(audioButton.isArmed())
			{
				playTranslation(audioFile);
			}
		}
	}
	
	
	private void playTranslation(String filename)
	{
		InputStream translation;
		try
		{
			translation = new FileInputStream(new File(filename));
			AudioStream audio = new AudioStream(translation);
			AudioPlayer.player.start(audio);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Error");
			
		}	
	}
	

}


