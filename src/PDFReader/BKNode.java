package PDFReader;

import java.util.ArrayList;

public class BKNode {
	String word ;
	ArrayList<BKNode> childList ;
	int keyValue ;
	
	
	public BKNode(String x){
		word = x ;
		childList = new ArrayList<BKNode>() ;
		keyValue = 0 ;
	}
	
	public void addBKNode(BKNode node){
		if (node == null){
			return ; 
		}
		int key = LDistance.levenDistance(this.word,
										  node.word, 
										  this.word.length(), 
										  node.word.length(), 
										  LDistance.makeArray(this.word.length(), node.word.length()));
		BKNode tempNode = this.hasChildKey(key) ;
		if(tempNode == null){
			node.keyValue = key ;
			this.childList.add(node) ;
			
		}
		else {
			tempNode.addBKNode(node);
		}
		
	}
	
	public BKNode hasChildKey(int key){
		int i = 0 ;
		for (i=0 ; i<childList.size() ; ++i){
			if(this.childList.get(i).keyValue == key){
				return childList.get(i) ;
			}
			
		}
		return null ;
	}
	
}
