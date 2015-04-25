package PDFReader;

import java.io.File;
import java.util.ArrayList;

public class Import {
	public static ArrayList<String> importPdfFiles(){
		ArrayList<String> importedFiles = new ArrayList<String>() ;
		flist("../../../" , importedFiles) ;
		return importedFiles ;
	}
	public static void flist(String dirName , ArrayList<String> importedList){
		File directory = new File(dirName);
		File[] fList = directory.listFiles();
		
		for (File file : fList){
			if (file.isFile()){
				if(file.getName().endsWith(".pdf")){
					importedList.add(file.getAbsolutePath() );
					//System.out.println(file.getAbsolutePath());
				}
			}
			else if (file.isDirectory()){
				flist(file.getAbsolutePath() , importedList);
			}
		}
	}
	
	public static  ternaryTree makeTree(ArrayList<String> importedArrayList){
		ternaryTree tree = new ternaryTree() ;
		int i = 0 ;
		for (i=0 ; i<importedArrayList.size() ; ++i){
			tree.AddString(getName(importedArrayList.get(i)), i);
		}
		return  tree;
		
	}
	public static String getName(String longName){
		int i = longName.length() ;
		if (i<=4){
			return longName ;
		}
		String str = "" ;
		for(i = longName.length()-5 ; i>=0 ; --i ){
			if(longName.charAt(i) == '/'){
				break ;
			}
			else{
				str = longName.charAt(i) + str ;			
			}
		}
		return str ;
	}
}

