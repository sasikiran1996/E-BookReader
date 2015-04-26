package PDFReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Import {
	public static ArrayList<String> importPdfFiles(){
		ArrayList<String> importedFiles = new ArrayList<String>() ;
		 
		flist("../../../../" , importedFiles) ;		
		try
	      {
	         FileOutputStream fileOut = new FileOutputStream("importArrayList.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(importedFiles);
	         out.close();
	         fileOut.close();
	         //System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }
		catch(IOException err)
	      {
	          err.printStackTrace();
	      }
		return importedFiles ;
	}
	
	public static BKTree importFileBKTree(ArrayList<String> importedFiles){
		int i = 0 ;
		BKTree importedBKTree = new BKTree() ;
		for (i=0 ; i<importedFiles.size() ; ++i ){
			importedBKTree.addBKNode(getName(importedFiles.get(i)));
		}
		try
	      {
	         FileOutputStream fileOut = new FileOutputStream("importBKTree.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(importedBKTree);
	         out.close();
	         fileOut.close();
	         //System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }
		catch(IOException err)
	      {
	          err.printStackTrace();
	      }
		return importedBKTree ;
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
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("importTTree.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(tree);
	         out.close();
	         fileOut.close();
	         //System.out.printf("Serialized data is saved in /tmp/employee.ser");
	      }catch(IOException err)
	      {
	          err.printStackTrace();
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

