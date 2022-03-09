import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StarbucksDisplay extends Application {
	public static final int WINSIZE_X = 400, WINSIZE_Y = 400;
	private final String WINTITLE = "Starbucks";
	private TabPane tabPane;
    private OrderPane orderPane;
    private PhrasesPane phrasesPane;
    private TotalPane totalPane;

    @Override
    public void start(Stage stage) throws Exception {
    	StackPane rootPane = new StackPane();
        orderPane = new OrderPane();
        phrasesPane = new PhrasesPane();
        totalPane = new TotalPane();
        rootPane.setPrefSize(WINSIZE_X, WINSIZE_Y);
        
        //tabs
        tabPane = new TabPane();
        Tab orderTab = new Tab();
        orderTab.setText("ORDER");
        orderTab.setContent(orderPane);

        Tab phraseTab = new Tab();
        phraseTab.setText("PHRASES");
        phraseTab.setContent(phrasesPane);
        
        Tab totalTab = new Tab();
        totalTab.setText("TOTAL");
        totalTab.setContent(totalPane);
        
        tabPane.getSelectionModel().select(0);
        tabPane.getTabs().addAll(orderTab, phraseTab, totalTab);
        tabPane.setSide(Side.BOTTOM);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        rootPane.getChildren().addAll(tabPane);
        
        Scene scene = new Scene(rootPane, WINSIZE_X, WINSIZE_Y);
        stage.setTitle(WINTITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}