/*import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.JOptionPane;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class PlayTranslation {
	
	public static void main(String [] args)
	{
		playTranslation("C:\\Users\\Movi\\Documents\\asuStarbucksHackathon\\src\\milkSpanish.wav");
	}
	
	public static void playTranslation(String filename)
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

}*/
