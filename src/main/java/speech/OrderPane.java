package speech;
import speech.Translate;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static speech.Translate.prettify;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.scene.control.Button;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OrderPane extends VBox {

	private static final String YourSubscriptionKey = "25773c8f0dac46439e82ad77deac3e1a";
	private static final String YourServiceRegion = "eastus";

	private  SpeechConfig speechConfig = SpeechConfig.fromSubscription(YourSubscriptionKey, YourServiceRegion);

	private ArrayList<Button> suggestionButtons;
	private String translatedSentence;
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
	private Label liveTranslationLabel;
	private TextArea liveTranslationTextArea;
	private String audioFile;
	private final String[] partnerGreetings = {"Welcome to Starbucks", "Hi, how are you?"};
	private final String[] customerGreetings = {"Bienvenido a Starbucks", "Hola, cómo estás?"};
	private final String[] phoneticGreetings = {"bjɛ̃mbeˈniðo a Starbucks","olaˈkomo ɛsˈtas"};
	private String suggestionTopic;
	private SpeechRecognition speechRecognition = new SpeechRecognition();
  
	//constructor
	public OrderPane() {
		speechConfig.setSpeechRecognitionLanguage("es-ES");

		//"Language Detection: Spanish" is placed into HBox
		topHBox = new HBox();
		languageDetected = new Label("Language Detected: ");
		customerLanguage = new Label("Spanish"); //determined by Azure language detection
		topHBox.getChildren().addAll(languageDetected, customerLanguage);
		topHBox.setPadding(new Insets(10));
		
		//initialize greetings combo box
		greetingsCombo = new ComboBox<String>();
		greetingsCombo.setValue("Greet the Customer!");
		greetingsCombo.getItems().addAll(partnerGreetings);
		greetingsCombo.setOnAction(new GreetingHandler());
		greetingsCombo.setTranslateX(10);
		
		//initialize all nodes to go into middle grid pane
		gridPane = new GridPane();
		partnerLanguageAbbrev = new Label("EN ");
		partnerLanguageAbbrev.setPadding(new Insets(10));
		customerLanguageAbbrev = new Label("SP "); //method
		customerLanguageAbbrev.setPadding(new Insets(10));

		audioButton = new Button();
        ImageView view = new ImageView();
        view.setPreserveRatio(true);
        view.setFitHeight(14);
        FileInputStream input;
		try {
			input = new FileInputStream("C:\\Users\\Movi\\GitHubStarbucksHackathon\\Starbucks-Hackathon-main\\Audio_And_Images\\button.png");
			Image image = new Image(input);
			view.setImage(image);
		} catch (FileNotFoundException e) {
			view.setImage(null);
		}
		audioButton.setGraphic(view);
		audioFile = "C:\\Users\\Movi\\GitHubStarbucksHackathon\\Starbucks-Hackathon-main\\Audio_And_Images\\bienvenidoastarbucks_2022-03-09_214636505.wav";
		audioButton.setOnAction(new ButtonHandler());
		audioButton.setVisible(false);

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
		gridPane.setHgap(10);

		//live translation area
		liveTranslationLabel = new Label("Live Translation ("+ customerLanguageAbbrev.getText() + "to " + partnerLanguageAbbrev.getText() +")");
		liveTranslationLabel.setPadding(new Insets(10));
		liveTranslationTextArea = new TextArea(); //method to display text here
		liveTranslationTextArea.setEditable(false);
		liveTranslationTextArea.setTranslateX(10);
		liveTranslationTextArea.setMaxSize(380, 250);
		
		//suggestion area
		suggestions = new Label("Suggestions for Phrases:");
		suggestions.setPadding(new Insets(10));
		suggestionButtons = new ArrayList<Button>();
		bottomHBox = new HBox();
		Order newOrder = new Order();
		newOrder.setDrink();
		suggestionButtons.add(new Button(newOrder.getCommonTopics().get(0)));
		suggestionButtons.add(new Button("Menu Recommendations"));
		suggestionButtons.add(new Button("Shortages"));
		for (int i = 0; i < suggestionButtons.size(); i++) {
			suggestionButtons.get(i).setOnAction(new SuggestionHandler());
			suggestionButtons.get(i).setTranslateX(10);
			bottomHBox.getChildren().addAll(suggestionButtons.get(i));
		}
		bottomHBox.setAlignment(Pos.CENTER);
		bottomHBox.setSpacing(5);
		bottomHBox.setPadding(new Insets(0, 0, 20, 0));
		
		//set all components in VBox
		this.getChildren().addAll(topHBox, greetingsCombo, gridPane, liveTranslationLabel, liveTranslationTextArea, suggestions, bottomHBox);
	}
	private void updateTextArea() {
		try {
			String jsonTranslation = speechRecognition.recognizeFromMicrophone(speechConfig);
			String [] array = jsonTranslation.split("t\": \"");
			String [] array2 = array[1].split("\",");
			liveTranslationTextArea.setText(array2[0]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	private class GreetingHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			partnerTranslation.setText(greetingsCombo.getValue());
			customerTranslation.setText(customerGreetings[Arrays.asList(partnerGreetings).indexOf(greetingsCombo.getValue())]);
			phoneticTranslation.setText(phoneticGreetings[Arrays.asList(partnerGreetings).indexOf(greetingsCombo.getValue())]);	
			audioButton.setVisible(true);		
		}
	}
	
	private class ButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			playTranslation(audioFile);
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			updateTextArea();
		}
	}

	private class SuggestionHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (e.getSource() == suggestionButtons.get(0)) {
				if (suggestionButtons.get(0).getText().equals("Milk")) {
					suggestionTopic = "Milk";
				}
				else if (suggestionButtons.get(0).getText().equals("speech.Drink Type")) {
					suggestionTopic = "speech.Drink Type";
				}
				else if (suggestionButtons.get(0).getText().equals("Size")) {
					suggestionTopic =  "Size";
				}
			}
		}
	}

	public String getSuggestion() {
		return suggestionTopic;
	}
	
	private void playTranslation(String translationLocation) {
		try {
			File translation = new File(translationLocation);
			if (translation.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(translation);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
			}
			else {
				System.out.print("Can't find file");
			}
		}
		catch(Exception e) {
			e.printStackTrace();	
		}	
	}
}