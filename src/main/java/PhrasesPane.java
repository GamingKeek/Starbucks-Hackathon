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

public class PhrasesPane extends VBox
{
	private ComboBox<String> phrases;
	private ComboBox<String> nestedPhrases;
	private GridPane gridPane;
	private Label customerLanguageAbbrev;
	private Label partnerLanguageAbbrev;
	private Label partnerTranslation;
	private Label customerTranslation;
	private Label phoneticTranslation;
	private Button audioButton;
	private Label liveTranslationLabel;
	private TextArea liveTranslationTextArea;
	private String translatedSentence = "I want a tall hot coffee with milk";
	
	private final String[] topics = {"milk", "hot or cold", "espresso"};
	private final String[] milkPhrases = {"What type of milk?", "phrase 2", "phrase 3"};
	private final String[] customerMilkPhrases = {"Que tipo de leche?", "translation2", "translation3"};
	private final String[] tempPhrases = {"hot","cold", "light ice"};
	private final String[] customerTempPhrases = {"Caliente", "Frio", "Translation3"};
	private final String[] espressoPhrases = {"How many shots", "phrase 2", "phrase 3"};
	private final String[] customerEspressoPhrases = {"spanish1", "spanish2", "spanish3"};
	
	private String[][] phraseList = {milkPhrases, tempPhrases, espressoPhrases};
	private String[][] customerPhraseList = {customerMilkPhrases, customerTempPhrases, customerEspressoPhrases};
	
	public PhrasesPane()
	{
		phrases = new ComboBox<String>();
		phrases.setValue("Common Phrases");
		phrases.getItems().addAll(topics);
		phrases.setOnAction(new PhraseHandler());
		nestedPhrases = new ComboBox<String>();
		nestedPhrases.setOnAction(new NestedPhraseHandler());
		
		//translation of selected option in drop down box
		partnerLanguageAbbrev = new Label("EN "); 
		customerLanguageAbbrev = new Label("SP "); //method
		audioButton = new Button("listen");
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
		liveTranslationTextArea = new TextArea(); //method to display text here
		updateTextArea();
		
		this.getChildren().addAll(phrases, nestedPhrases, gridPane, liveTranslationLabel, liveTranslationTextArea);
		
		
			
	}
	
	private class PhraseHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent event)
		{
			nestedPhrases.setValue(phrases.getValue() + " phrases");	
			nestedPhrases.getItems().addAll(phraseList[Arrays.asList(topics).indexOf(phrases.getValue())]);
		}
	}
	
	private class NestedPhraseHandler implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent event)
		{
			String[] currentEnglishPhraseList = phraseList[Arrays.asList(topics).indexOf(phrases.getValue())];
			int i = Arrays.asList(topics).indexOf(phrases.getValue());
			int j = Arrays.asList(currentEnglishPhraseList).indexOf(nestedPhrases.getValue());
	
			if(!(nestedPhrases.getValue().equals(phrases.getValue() + " phrases")))
			{
				partnerTranslation.setText(nestedPhrases.getValue());
				customerTranslation.setText(customerPhraseList[i][j]);
				//customerTranslation.setText(currentSpanishPhraseList[Arrays.asList(phraseList).indexOf(nestedPhrases.getValue())]);
			}
			
			//phoneticTranslation.setText(phoneticGreetings[Arrays.asList(phrasesList).indexOf(nestedPhrases.getValue())]);	
			
		}
	}
	private void updateTextArea()
	{
		liveTranslationTextArea.setText(translatedSentence);
	}
	

}
