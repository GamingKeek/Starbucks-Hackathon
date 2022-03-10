import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class TotalPane extends VBox {
    private VBox screen;
    private Label order;
    private Label item;
    private Label quantity;
    private Label price;
    private Label hotCoffee;
    private Label one;
    private Label coffeePrice;
    private Label wholeMilk;
    private Label milkPrice;
    private Label orderVerification;
    private Label partnerQuestion;
    private Label customerQuestion;
    private HBox languageInfo;
    private Label phoeneticResponse;
    private Button responseListen;
    private String audioFile;
    private Image img = new Image("C:/Users/kelly/Downloads/1607bd824e0e35d7e88df23a56a24540.png");
    private Label totalConfirmation;
    private Label partnerTotal;
    private Label customerTotal;
    private HBox languageTotal;
    private Label phoeneticTotal;
    private Button listenTotal;
    private String audioFile2;
    private Image img2 = new Image("C:/Users/kelly/Downloads/1607bd824e0e35d7e88df23a56a24540.png");
    private Label liveTranslationLabel;
	private TextArea liveTranslationTextArea;
    private String translatedSentence = "Hi I want a hot coffee with milk. Whole milk. No, that's it. Thank you.";
    
    public TotalPane() {
        screen = new VBox();
        order = new Label("Order");
        order.setPadding(new Insets(10, 0, 0, 10));
        order.setFont(Font.font("Ariel", FontWeight.BOLD, 12));
        GridPane orderGrid = new GridPane();
        orderGrid.setVgap(2);

        item = new Label("Item");
        item.setPadding(new Insets(0, 0, 0, 15));
        quantity = new Label("Qty");
        quantity.setPadding(new Insets(0, 0, 0, 15));
        price = new Label("Price");
        price.setPadding(new Insets(0, 0, 0, 15));
        hotCoffee = new Label("Hot Coffee");
        hotCoffee.setPadding(new Insets(0, 0, 0, 15));
        one = new Label("1");
        one.setPadding(new Insets(0, 0, 0, 15));
        coffeePrice = new Label("$3.50");
        coffeePrice.setPadding(new Insets(0, 0, 0, 15));
        wholeMilk = new Label("Whole Milk");
        wholeMilk.setFont(Font.font("Ariel", FontPosture.ITALIC, 12));
        wholeMilk.setPadding(new Insets(0, 0, 0, 25));
        milkPrice = new Label("$0.00");
        milkPrice.setPadding(new Insets(0, 0, 0, 15));

        orderGrid.add(item, 0, 0);
        orderGrid.add(quantity, 1, 0);
        orderGrid.add(price, 2, 0);
        orderGrid.add(hotCoffee, 0, 1);
        orderGrid.add(one, 1, 1);
        orderGrid.add(coffeePrice, 2, 1);
        orderGrid.add(wholeMilk, 0, 3);

        orderVerification = new Label("Ask the customer if they want anything else");
        orderVerification.setFont(Font.font("Ariel", FontWeight.BOLD, 12));
        orderVerification.setPadding(new Insets(10, 0, 0, 10));

        partnerQuestion = new Label("EN:\tDo you want anything else?");
        partnerQuestion.setPadding(new Insets(0, 0, 0, 15));

        customerQuestion = new Label("SP:\tDesea algo más?");
        customerQuestion.setPadding(new Insets(0, 0, 0, 15));
        
        languageInfo = new HBox();
        phoeneticResponse = new Label("Deh-seh-ah ahl-goh mahs");
        phoeneticResponse.setPadding(new Insets(0, 10, 0, 15));
        responseListen = new Button();
		ImageView view = new ImageView(img);
		view.setFitHeight(14);
		view.setPreserveRatio(true);
		responseListen.setGraphic(view);
		audioFile = "C:/Users/kelly/Downloads/milkSpanish.wav";
		responseListen.setOnAction(new ButtonHandler());
        languageInfo.getChildren().addAll(phoeneticResponse, responseListen);
        

        totalConfirmation = new Label("If no, let customer know total is displayed on screen");
        totalConfirmation.setFont(Font.font("Ariel", FontWeight.BOLD, 12));
        totalConfirmation.setPadding(new Insets(10, 0, 0, 10));
        
        partnerTotal = new Label("EN:\tThe total is displayed, thank you.");
        partnerTotal.setPadding(new Insets(0, 0, 0, 15));

        customerTotal = new Label("SP:\tSe muestra el total, gracias.");
        customerTotal.setPadding(new Insets(0, 0, 0, 15));

        languageTotal = new HBox();
        phoeneticTotal = new Label("se ˈmwɛstɾa ɛl toˈtal ˈɡɾak");
        phoeneticTotal.setPadding(new Insets(0, 10, 0, 15));
        listenTotal = new Button();
		ImageView view2 = new ImageView(img2);
		view2.setFitHeight(14);
		view2.setPreserveRatio(true);
		listenTotal.setGraphic(view2);
		audioFile2 = "C:/Users/kelly/Downloads/milkSpanish.wav";
		listenTotal.setOnAction(new ButtonHandler());
        languageTotal.getChildren().addAll(phoeneticTotal, listenTotal);

        //live translation area
		liveTranslationLabel = new Label("Live Translation (EN to SP)");
		liveTranslationLabel.setPadding(new Insets(10));
		liveTranslationTextArea = new TextArea(); //method to display text here
		liveTranslationTextArea.setEditable(false);
		liveTranslationTextArea.setTranslateX(10);
		updateTextArea();

        
        screen.getChildren().addAll(order, orderGrid, orderVerification, partnerQuestion, customerQuestion, languageInfo,
            totalConfirmation, partnerTotal, customerTotal, languageTotal, liveTranslationLabel, liveTranslationTextArea);
        this.getChildren().add(screen);
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			if (event.getSource() == responseListen) {
                playTranslation(audioFile);
            }
            else if (event.getSource() == listenTotal) {
                playTranslation(audioFile2);
            }
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
    
    private void updateTextArea() {
		liveTranslationTextArea.setText(translatedSentence);
	}
}