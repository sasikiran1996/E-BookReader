package PDFReader;


import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class DisplayTheme {
	
	//setting display theme to system theme (Ubuntu)
	
	public static void setSystemTheme(){
		
		String theme = null;
		theme = UIManager.getSystemLookAndFeelClassName();
		
		try {
			UIManager.setLookAndFeel(theme);
			//UIManager.put("Menu.font", new FontUIResource(new Font("Ubuntu", 1, 48)));
			//UIManager.put("Button.font", new FontUIResource(new Font("Ubuntu", 0, 48)));
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
