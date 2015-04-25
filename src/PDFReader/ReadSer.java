package PDFReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ReadSer {
	public static ternaryTree fileTree ;
	public static ArrayList<String>  fileArray ;
	public ReadSer(){
		   try
		      {
		         FileInputStream fileIn = new FileInputStream("importTTree.ser");
		         
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         fileTree = (ternaryTree) in.readObject();
		         
		         in.close();
		         fileIn.close();
		      }
		   
		   catch(IOException i)
		      {
		         i.printStackTrace();
		         
		      }
		   
		   catch(ClassNotFoundException c)
		      {
		         System.out.println("fileTree Serialised Object not found");
		         c.printStackTrace();
		         fileTree = null ;
		         
		      }
		   
		   try
		      {
		         FileInputStream fileIn = new FileInputStream("importArrayList.ser");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         fileArray = (ArrayList<String>) in.readObject();
		         in.close();
		         fileIn.close();
		      }
		   
		   catch(IOException i)
		      {
		         i.printStackTrace();
		         
		      }
		   
		   catch(ClassNotFoundException c)
		      {
		         System.out.println("fileTree Serialised Object not found");
		         c.printStackTrace();
		         fileArray = null ;
		         
		      }
	}
}
