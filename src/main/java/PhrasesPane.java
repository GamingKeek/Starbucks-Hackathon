import javafx.scene.layout.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javafx.scene.control.Button;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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

public class PhrasesPane extends GridPane
{
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
	private String translatedSentence = "I want a tall hot coffee with milk.";
	
	private final String[] topics = {"milk", "hot or cold", "espresso"};
	private final String[] milkPhrases = {"What type of milk?", "Sorry we are out of..", "I reccomend.."};
	private final String[] customerMilkPhrases = {"Que tipo de leche?", "translation2", "translation3"};
	private final String[] phoneticMilkPhrases = {"ˈkeˈtipo ðeˈleʧe", "phonetic2", "phonetic3"};
	private final String[] tempPhrases = {"hot","cold", "light ice"};
	private final String[] customerTempPhrases = {"Caliente", "Frio", "Translation3"};
	private final String[] phoneticTempPhrases = {"kuh-lee-en-tay", "Free-oh", "phonetic3"};
	private final String[] espressoPhrases = {"How many shots", "phrase 2", "phrase 3"};
	private final String[] customerEspressoPhrases = {"spanish1", "spanish2", "spanish3"};
	private final String[] phoneticEspressoPhrases = {"phonetic1", "phonetic2", "phonetic3"};
	
	private String[][] phraseList = {milkPhrases, tempPhrases, espressoPhrases};
	private String[][] customerPhraseList = {customerMilkPhrases, customerTempPhrases, customerEspressoPhrases};
	private String[][] phoneticPhraseList = {phoneticMilkPhrases, phoneticTempPhrases, phoneticEspressoPhrases};
	
	public PhrasesPane()
	{
		phrases = new ComboBox<String>();
		phrases.setValue("Common Phrases");
		phrases.getItems().addAll(topics);
		phrases.setOnAction(new PhraseHandler());

		nestedPhrases = new ComboBox<String>();
		nestedPhrases.setValue("");
		nestedPhrases.setOnAction(new NestedPhraseHandler());
		
		//translation of selected option in drop down box
		partnerLanguageAbbrev = new Label("EN "); 
		partnerLanguageAbbrev.setPadding(new Insets(10));
		customerLanguageAbbrev = new Label("SP "); //method
		customerLanguageAbbrev.setPadding(new Insets(10));
		
		//audio changes based on who runs the application
		audioButton = new Button("listen");
		audioButton.setOnAction(new ButtonHandler());
		audioFile = "C:\\Users\\Movi\\Downloads\\SBHackathonFiles\\Starbucks-Hackathon-main\\src\\main\\java\\milkSpanish.wav";
		
		partnerTranslation = new Label();
		customerTranslation = new Label();
		phoneticTranslation = new Label();
		
		gridPane = new GridPane();
		gridPane.add(partnerLanguageAbbrev, 0, 0);
		gridPane.add(partnerTranslation, 1, 0);
		gridPane.add(customerLanguageAbbrev, 0, 1);
		gridPane.add(customerTranslation, 1, 1);
		gridPane.add(phoneticTranslation, 1, 2);
		gridPane.add(audioButton, 2, 2);
		
		liveTranslationLabel = new Label("Live Translation ("+ customerLanguageAbbrev.getText() + "to " + partnerLanguageAbbrev.getText() +")");
		liveTranslationLabel.setPadding(new Insets(10));
		liveTranslationTextArea = new TextArea(); //method to display text here
		liveTranslationTextArea.setEditable(false);
		liveTranslationTextArea.setTranslateX(10);
		liveTranslationTextArea.setMaxSize(380, 250);
		liveTranslationTextArea.setText(translatedSentence);
		
		this.add(phrases, 0, 0);
		this.add(liveTranslationLabel, 0, 3);
		this.add(liveTranslationTextArea, 0, 4);
		
			
	}
	public void addNestedPhrases()
	{
		this.add(nestedPhrases,0,1);
	}
	
	public void addTheRest()
	{
		this.add(gridPane, 0, 2);
	}
	
	private class PhraseHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent event)
		{
			if((!(phrases.getValue().equals("Common Phrases"))) && x==0)
			{
				addNestedPhrases();
				x++;
			}
			nestedPhrases.getItems().addAll(phraseList[Arrays.asList(topics).indexOf(phrases.getValue())]);
			
			if(nestedPhrases.getItems().size() > Arrays.asList(milkPhrases).size())
			{
				nestedPhrases.getItems().clear();
				nestedPhrases.getItems().addAll(phraseList[Arrays.asList(topics).indexOf(phrases.getValue())]);
			}
			nestedPhrases.setValue( phrases.getValue()+ " phrases");	
			
		}
	}

	private class NestedPhraseHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent event)
		{
			if((!(nestedPhrases.getValue().equals(phrases.getValue()+ " phrases"))) && y==0)
			{
				addTheRest();
				y++;
			}
			String[] currentEnglishPhraseList = phraseList[Arrays.asList(topics).indexOf(phrases.getValue())];
			int i = Arrays.asList(topics).indexOf(phrases.getValue());
			int j = Arrays.asList(currentEnglishPhraseList).indexOf(nestedPhrases.getValue());
	
			if(!(nestedPhrases.getValue().equals(phrases.getValue() + " phrases")))
			{
				partnerTranslation.setText(nestedPhrases.getValue());
				customerTranslation.setText(customerPhraseList[i][j]);
				phoneticTranslation.setText(phoneticPhraseList[i][j]);
			
			}	
		}
	}
	
	private class ButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			playTranslation(audioFile);
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
	
	private void updateTextArea()
	{
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		liveTranslationTextArea.setText(translatedSentence + " Whole Milk.");
	}
	

}
