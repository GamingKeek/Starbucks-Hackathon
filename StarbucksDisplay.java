import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StarbucksDisplay extends Application {
	public static final int WINSIZE_X = 400, WINSIZE_Y = 400;
	private final String WINTITLE = "Starbucks";

    @Override
    public void start(Stage stage) throws Exception
    {
        OrderPane rootPane = new OrderPane();
        rootPane.setPrefSize(WINSIZE_X, WINSIZE_Y);
        Scene scene = new Scene(rootPane, WINSIZE_X, WINSIZE_Y);
        stage.setTitle(WINTITLE);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
	
	
}
