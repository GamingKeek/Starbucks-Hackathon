import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.scene.control.Button;
import java.io.File;
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
	private ArrayList<Button> suggestionButtons;
	private String translatedSentence = "I want a tall hot coffee with milk";
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
	private Image img = new Image("C:/Users/kelly/Downloads/1607bd824e0e35d7e88df23a56a24540.png");
  
	//constructor
	public OrderPane() {
		
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
		ImageView view = new ImageView(img);
		view.setFitHeight(16);
		view.setPreserveRatio(true);
		audioButton.setGraphic(view);
		audioFile = "C:/Users/kelly/Downloads/milkSpanish.wav";
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
		updateTextArea();
		
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
		
		//set all components in VBox
		this.getChildren().addAll(topHBox, greetingsCombo, gridPane, liveTranslationLabel, liveTranslationTextArea, suggestions, bottomHBox);
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
		}
	}

	private class SuggestionHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if (e.getSource() == suggestionButtons.get(0)) {
				System.out.println("What kind of milk?");
			}
			else if (e.getSource() == suggestionButtons.get(1)) {
				System.out.println("I reccommend the Mocha Cookie Crumble Frappuccino");
			}
			else {
				System.out.println("Sorry, we're out of...");
			}
		}
	}
	
	private void updateTextArea() {
		liveTranslationTextArea.setText(translatedSentence);
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
