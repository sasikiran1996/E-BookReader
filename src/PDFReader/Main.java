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
		
		
		/*ternaryTree TT = new ternaryTree();
		TT.AddString("The da vinci code", 0);
		TT.AddString("Percy Jackson", 1);
		TT.AddString("Immortals of meluha", 2);
		TT.AddString("The lightning thief", 3);
		TT.prefixedSearch("the");
		for(int i=0;i<TT.matchings.size();i++){
			System.out.println(TT.matchings.get(i));
		}*/
	}
}


