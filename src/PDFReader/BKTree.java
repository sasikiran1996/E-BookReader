package PDFReader;

import java.io.Serializable;
import java.util.ArrayList;

public class BKTree implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1409869771920665409L;
	BKNode root ;
	public BKTree() {
		root = null;
	}
	public BKTree(String s){
		root = new BKNode(s);
	}
	
	public void addBKNode (String s){
		if(root == null){
			root = new BKNode(s);
			return ;
		}
		root.addBKNode(new BKNode(s));
	}
	
	public ArrayList<String> getMatches( String str , int precision){
		ArrayList<String > matchesArray = new ArrayList<String>() ;
		getMatches(str , matchesArray , root , precision) ;
		return matchesArray ;
	}
	
	public void getMatches (String str , ArrayList<String> matchesArray , BKNode root , int precision){
		if(root == null){
			return ;
		}
		int rootKey = LDistance.levenDistance(root.word,
				 str, 
				 root.word.length(), 
				 str.length(), 
				 LDistance.makeArray(root.word.length(), str.length()) );
		if (precision >=rootKey) {
			matchesArray.add(root.word) ;
		}
		int i = 0 ;
		for (i=0 ; i<root.childList.size() ; ++i){
			if (((rootKey - precision)<=root.childList.get(i).keyValue) &&(root.childList.get(i).keyValue <= (rootKey + precision)) ){
				getMatches(str , matchesArray , root.childList.get(i) , precision);
				
			}
		}
	}
} 
