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

public class OrderPane extends BorderPane{
	private Pane pane;
	private Label languageDetected;
	private Label language;
	private ComboBox greetings;
	private Label languageA;
	private Label languageB;
	private Label languageATranslation;
	private Label languageBTranslation;
	private Label phonetic;
	private Button audio;
	private Label liveTranslationLabel;
	private TextArea liveTranslationTextArea;
	private Button translation;
	private String audioFile;
	
	//constructor
	public OrderPane()
	{
		Pane = new Pane
		translation = new Button("translation");
		translation.setOnAction(new ButtonHandler());
		audioFile = "C:\\Users\\Movi\\Documents\\asuStarbucksHackathon\\src\\milkSpanish.wav";
		//create an object for this instance of what type of milk
		setCenter(translation);
	}
	private class ButtonHandler implements EventHandler<ActionEvent> 
	{
		public void handle(ActionEvent event) 
		{
			if(translation.isArmed())
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


