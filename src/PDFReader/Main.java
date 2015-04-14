package PDFReader;

public class Main {
	
	public static void  main(String[] args){
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DisplayTheme.setSystemTheme();
				new PDFPanel();
				
			}
		});
	}
}


