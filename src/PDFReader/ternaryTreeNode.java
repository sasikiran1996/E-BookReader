package PDFReader;

import java.io.Serializable;

public class ternaryTreeNode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2930873687484090585L;

	//character present at node
	//TODO: compressed to string
	protected char character;
	
	//true when it is end node in a tree
	protected boolean endNode; 
	
	//used to find file path in file list
	//index = 0 for all nodes other than end nodes
	protected int index;
	
	//left,right and bottom
	protected ternaryTreeNode left = null;
	protected ternaryTreeNode right = null;
	protected ternaryTreeNode down = null;
	
	public ternaryTreeNode(char alphabet, boolean end,int index) {
		
		character = alphabet;
		endNode = end;
		this.index = index;
	}
	
}
