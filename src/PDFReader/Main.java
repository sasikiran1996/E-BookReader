package PDFReader;

import java.util.ArrayList;

import org.jpedal.examples.viewer.commands.FullScreen;


public class Main {
	
	public static void  main(String[] args){
		System.out.println("Hello");
		ArrayList<String> sugArr = Import.importPdfFiles() ;
		BKTree sugBKTree = Import.importFileBKTree(sugArr) ;
		ternaryTree sugTree = Import.makeTree(sugArr);
		ReadSer readSer = new ReadSer() ;
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DisplayTheme.setSystemTheme();
				new PDFPanel();
				
				
			}
		});
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				/*ArrayList<String> sugArr = Import.importPdfFiles() ;
				BKTree sugBKTree = Import.importFileBKTree(sugArr) ;
				ternaryTree sugTree = Import.makeTree(sugArr);
				ReadSer readSer = new ReadSer() ;*/
				
				
			}
		});
		
		
		//ArrayList<String> sugArr = Import.importPdfFiles() ;
		//BKTree sugBKTree = Import.importFileBKTree(sugArr) ;
		//ternaryTree sugTree = Import.makeTree(sugArr);
		//ReadSer readSer = new ReadSer() ;
		ArrayList<String> serSugArr = readSer.fileArray ;
		ternaryTree serSugTree = readSer.fileTree ;
		BKTree serSugBKTree = readSer.fileBKTree ;
		int i = 0 ;
		for(i=0 ; i<serSugArr.size() ; ++i){
			System.out.println(serSugArr.get(i));
		}
		
		System.out.println("****************************************");
		serSugTree.prefixedSearch("a");
		for(i=0 ; i<serSugTree.matchings.size() ; ++i){
			System.out.println(serSugTree.matchings.get(i));
		}
		System.out.println("----------------------------------------");
		serSugTree.prefixedSearch("ass");
		for(i=0 ; i<serSugTree.matchings.size() ; ++i){
			System.out.println(serSugTree.matchings.get(i));
		}
		
		System.out.println("========================================");
		ArrayList<String> suggList = serSugBKTree.getMatches("report" , 3);
		for(i=0 ; i<suggList.size() ; ++i){
			System.out.println(suggList.get(i));
		}
		//Note : TODO: this should be on  a separate thread
		
		/*ArrayList<String> importList = Import.importPdfFiles();
		ternaryTree TTree = Import.makeTree(importList) ;
		TTree.prefixedSearch("cs13b051");
		for ( i=0;i<TTree.matchings.size();i++){
			//System.out.println(TTree.matchings.get(i));
		}*/
		
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
		
		/*BKNode root = new BKNode("book");
		root.addBKNode(new BKNode("boor"));
		root.addBKNode(new BKNode("brook"));
		System.out.println(root.childList.get(0).childList.get(0).keyValue) ;*/
	}
}


