package PDFReader;

public class Main {
	
	public static void  main(String[] args){
		System.out.println("Hello");
		/*javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DisplayTheme.setSystemTheme();
				new PDFPanel();
				
			}
		});*/
		
	/*	String S1 = "book" ;
		String S2 = "boob" ;
		System.out.println(LDistance.levenDistance(S1, S2, S1.length(), S2.length(), LDistance.makeArray(S1.length(), S2.length())));
		*/
	
		
		/*ternaryTree TT = new ternaryTree();
		TT.AddString("The da vinci code", 0);
		TT.AddString("Percy Jackson", 1);
		TT.AddString("Immortals of meluha", 2);
		TT.AddString("The lightning thief", 3);
		TT.prefixedSearch("the");
		for(int i=0;i<TT.matchings.size();i++){
			System.out.println(TT.matchings.get(i));
		}*/
		
		BKNode root = new BKNode("book");
		root.addBKNode(new BKNode("boor"));
		root.addBKNode(new BKNode("brook"));
		System.out.println(root.childList.get(0).childList.get(0).keyValue) ;
	}
}


