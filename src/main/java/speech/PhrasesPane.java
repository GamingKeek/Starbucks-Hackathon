package speech;

import speech.Translate;
import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


import javafx.scene.control.Button;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import speech.OrderPane;

public class PhrasesPane extends GridPane {
	private static final String YourSubscriptionKey = "25773c8f0dac46439e82ad77deac3e1a";
	private static final String YourServiceRegion = "eastus";

	private  SpeechConfig speechConfig = SpeechConfig.fromSubscription(YourSubscriptionKey, YourServiceRegion);
	private SpeechRecognition speechRecognition = new SpeechRecognition();

	private int x = 0;
	private int y = 0;
	private ComboBox<String> phrases;
	private ComboBox<String> nestedPhrases;
	private GridPane gridPane;
	private Label customerLanguageAbbrev;
	private Label partnerLanguageAbbrev;
	private Label partnerTranslation;
	private Label customerTranslation;
	private Label phoneticTranslation;
	private Button audioButton;
	private String audioFile;
	private Label liveTranslationLabel;
	private TextArea liveTranslationTextArea;
	private String translatedSentence = "";
	private OrderPane orderPaneSuggest;

	private final String[] TOPICS = {"Milk", "Drink Type", "Espresso"};
	private final String[] MILK_PHRASES = {"What type of milk?", "Sorry, we are out of...", "I reccomend.."};
	private final String[] CUSTOMER_MILK_PHRASES = {"Que tipo de leche?", "translation2", "translation3"};
	private final String[] PHOENETIC_MILK_PHRASES = {"Keh tee-poh deh leh-cheh", "phonetic2", "phonetic3"};
	private final String[] TEMP_PHRASES = {"hot","cold", "light ice"};
	private final String[] CUSTOMER_TEMP_PHRASES = {"Caliente", "Frio", "Translation3"};
	private final String[] PHOENETIC_TEMP_PHRASES = {"kuh-lee-en-tay", "Free-oh", "phonetic3"};
	private final String[] ESPRESSO_PHRASES = {"How many shots", "phrase 2", "phrase 3"};
	private final String[] CUSTOMER_ESPRESSO_PHRASES = {"spanish1", "spanish2", "spanish3"};
	private final String[] PHOENETIC_ESPRESSO_PHRASES = {"phonetic1", "phonetic2", "phonetic3"};
	
	private String[][] phraseList = {MILK_PHRASES, TEMP_PHRASES, ESPRESSO_PHRASES };
	private String[][] customerPhraseList = {CUSTOMER_MILK_PHRASES, CUSTOMER_TEMP_PHRASES, CUSTOMER_ESPRESSO_PHRASES};
	private String[][] phoneticPhraseList = {PHOENETIC_MILK_PHRASES, PHOENETIC_TEMP_PHRASES, PHOENETIC_ESPRESSO_PHRASES};
	
	public PhrasesPane() {
		speechConfig.setSpeechRecognitionLanguage("es-ES");

		orderPaneSuggest = new OrderPane();

		phrases = new ComboBox<String>();
		phrases.getItems().addAll(TOPICS);
		phrases.setValue("Common Phrases");
		phrases.setValue(orderPaneSuggest.getSuggestion());
		phrases.setOnAction(new PhraseHandler());
		phrases.setTranslateX(10);
		phrases.setTranslateY(10);

		nestedPhrases = new ComboBox<String>();
		nestedPhrases.setValue("");
		nestedPhrases.setOnAction(new NestedPhraseHandler());
		nestedPhrases.setTranslateX(10);
		nestedPhrases.setTranslateY(15);
		
		//translation of selected option in drop down box
		partnerLanguageAbbrev = new Label("EN "); 
		partnerLanguageAbbrev.setPadding(new Insets(20, 10, 0, 10));
		customerLanguageAbbrev = new Label("SP "); //method
		customerLanguageAbbrev.setPadding(new Insets(20, 10, 0, 10));
		
		//audio changes based on who runs the application
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
		audioButton.setOnAction(new ButtonHandler());
		audioButton.setVisible(false);
		audioButton.setTranslateY(10);
		audioFile = "C:\\Users\\Movi\\GitHubStarbucksHackathon\\Starbucks-Hackathon-main\\Audio_And_Images\\quetipodeleche_2022-03-10_051937984.wav";
		partnerTranslation = new Label();
		partnerTranslation.setPadding(new Insets(20, 10, 0, 10));
		customerTranslation = new Label();
		customerTranslation.setPadding(new Insets(20, 10, 0, 10));
		phoneticTranslation = new Label();
		phoneticTranslation.setPadding(new Insets(20, 10, 0, 10));
		
		gridPane = new GridPane();
		gridPane.add(partnerLanguageAbbrev, 0, 0);
		gridPane.add(partnerTranslation, 1, 0);
		gridPane.add(customerLanguageAbbrev, 0, 1);
		gridPane.add(customerTranslation, 1, 1);
		gridPane.add(phoneticTranslation, 1, 2);
		gridPane.add(audioButton, 2, 2);
		
		liveTranslationLabel = new Label("Live Translation ("+ customerLanguageAbbrev.getText() + "to " + partnerLanguageAbbrev.getText() +")");
		liveTranslationLabel.setPadding(new Insets(20, 10, 10, 10));
		liveTranslationTextArea = new TextArea(); //method to display text here
		liveTranslationTextArea.setEditable(false);
		liveTranslationTextArea.setTranslateX(10);
		liveTranslationTextArea.setMaxSize(380, 150);
		liveTranslationTextArea.setText("Hello, I want a big, hot coffee with milk. ");
		
		this.add(phrases, 0, 0);
		this.add(liveTranslationLabel, 0, 3);
		this.add(liveTranslationTextArea, 0, 4);	
	}
	private void updateTextArea() {
		try {
			String jsonTranslation = speechRecognition.recognizeFromMicrophone(speechConfig);
			String [] array = jsonTranslation.split("t\": \"");
			String [] array2 = array[1].split("\",");
			liveTranslationTextArea.setText("Hello, I want a big, hot coffee with milk. " + array2[0]);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public void addNestedPhrases() {
		this.add(nestedPhrases,0,1);
	}
	
	public void addTheRest() {
		this.add(gridPane, 0, 2);
	}
	
	private class PhraseHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if((!(phrases.getValue().equals("Common Phrases"))) && x==0) {
				addNestedPhrases();
				x++;
			}
			nestedPhrases.getItems().addAll(phraseList[Arrays.asList(TOPICS).indexOf(phrases.getValue())]);
			
			if(nestedPhrases.getItems().size() > Arrays.asList(MILK_PHRASES).size())
			{
				nestedPhrases.getItems().clear();
				nestedPhrases.getItems().addAll(phraseList[Arrays.asList(TOPICS).indexOf(phrases.getValue())]);
			}
			nestedPhrases.setValue( phrases.getValue()+ " phrases");	
			
		}
	}

	private class NestedPhraseHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if((!(nestedPhrases.getValue().equals(phrases.getValue()+ " phrases"))) && y==0) {
				addTheRest();
				y++;
			}
			String[] currentEnglishPhraseList = phraseList[Arrays.asList(TOPICS).indexOf(phrases.getValue())];
			int i = Arrays.asList(TOPICS).indexOf(phrases.getValue());
			int j = Arrays.asList(currentEnglishPhraseList).indexOf(nestedPhrases.getValue());
	
			if(!(nestedPhrases.getValue().equals(phrases.getValue() + " phrases"))) {
				partnerTranslation.setText(nestedPhrases.getValue());
				customerTranslation.setText(customerPhraseList[i][j]);
				phoneticTranslation.setText(phoneticPhraseList[i][j]);
			}
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