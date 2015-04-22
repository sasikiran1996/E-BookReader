package PDFReader;

import java.util.ArrayList;

public class ternaryTree {
	
	public ternaryTreeNode root = null;
	
	//Everytime new matchings arrayList will be assigned to it
	public ArrayList<String> matchings = new ArrayList<String>();
	
	public void AddString(String str,int index){
		if(str == null){
			System.err.println("Null String Exception");
			return;
		}
		else{
			if(str.length() == 0){
				System.err.println("Empty String Exception");
				return;
			}
			else{
				root = recurrsiveAdd(str,0,root,index);
			}
		}
		
	}
	
	private ternaryTreeNode recurrsiveAdd(String str,int position,ternaryTreeNode root,int index){
		
		if(root == null){
			if(position == str.length()){
				return(root);
			}
			else{
				if(position == str.length()-1){
					root = new ternaryTreeNode(str.charAt(position), true, index);	
				}
				else{
					root = new ternaryTreeNode(str.charAt(position),false,0);
					root.down = recurrsiveAdd(str, position + 1, root.down, index);
				}
			}
		}
		else{
			if(str.charAt(position) < root.character){
				//go left
				root.left = recurrsiveAdd(str, position, root.left,index);
			}
			else{
				if(str.charAt(position) > root.character){
					//go right
					root.right = recurrsiveAdd(str, position, root.right,index);
				}
				else{
					if(position == str.length() - 1){
						root.endNode = true;
						root.index = index;
					}
					else{
						root.down = recurrsiveAdd(str, position + 1, root.down,index);	
					}
				}
			}
		}
		return(root);
	}
	
	//gives the strings with given prefix
	public void prefixedSearch(String query){
		
		matchings = new ArrayList<String>();
		if(query == null){
			System.err.println("Null string used for searching");
			return;
		}
		else{
			if(query.length() == 0){
				System.err.println("Empty string used for searching");
				return;		
			}
		}
		
		//Note that query should be non null or empty
		ternaryTreeNode reqNode = findNode(root,query,0);
		if(reqNode == null){
			return;
		}
		else{
			if(reqNode.endNode){
				matchings.add(query);
			}
			if(reqNode.down != null){
				recurrsiveAddToMatchings(reqNode.down,query);	
			}
		}
	}
	
	private ternaryTreeNode findNode(ternaryTreeNode root,String query,int position){
		
		//No null or empty string reaches here
		if(root == null){
			return(null);
		}
		else{
			if(query.charAt(position) < root.character){
				return(findNode(root.left,query,position));
			}
			else{
				if(query.charAt(position) > root.character){
					return(findNode(root.right,query,position));
				}
				else{
					if(position == query.length()-1){
						return(root);
					}
					else{
						return(findNode(root.down,query,position + 1));
					}
				}
			}
		}
	}
	
	private void recurrsiveAddToMatchings(ternaryTreeNode root,String query){
		
		if(root.left != null){
			recurrsiveAddToMatchings(root.left, query);
		}
		if(root.right != null){
			recurrsiveAddToMatchings(root.right, query);
		}
		query += Character.toString(root.character);
		if(root.endNode){
			matchings.add(query);
		}
		if(root.down != null){
			recurrsiveAddToMatchings(root.down, query);
		}
	}
}
