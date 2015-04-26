package PDFReader;

import java.io.Serializable;
import java.util.ArrayList;

public class ternaryTree implements Serializable{
	
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 3596616777469286514L;

	public ternaryTreeNode root = null;
	
	// Every time new matchings arrayList will be assigned to it
	// $ is the terminating character
	protected ArrayList<Integer> indices = new ArrayList<Integer>();
	
	public ArrayList<String> matchings = new ArrayList<String>();
	
	private ArrayList<ternaryTreeNode> ReqNodes = new ArrayList<ternaryTreeNode>();
	
	private ArrayList<String> Generated = new ArrayList<String>();
	
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
				root = recurrsiveAdd(str + "$",0,root,index);
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
		ReqNodes = new ArrayList<ternaryTreeNode>();
		indices = new ArrayList<Integer>();
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
		//findNode(root,query,0);
		searchWithinHammingDistance(root, query, 0, 1, "");
		for(int i =0;i< ReqNodes.size();i++){
			ternaryTreeNode req = ReqNodes.get(i);
			if(req.endNode){
				matchings.add(query);
				indices.add(req.index);
			}
			if(req.down != null){
				//generated == query if only find node is used
				recurrsiveAddToMatchings(req.down,query,Generated.get(i));	
			}
		}
	}
	
	private void findNode(ternaryTreeNode root,String query,int position){
		
		//No null or empty string reaches here
		if(root == null){
			return;
		}
		else{
			if(query.charAt(position) < root.character){
				findNode(root.left,query,position);
			}
			else{
				if(query.charAt(position) > root.character){
					findNode(root.right,query,position);
				}
				else{
					if(position == query.length()-1){
						ReqNodes.add(root);
						Generated.add(query);
					}
					else{
						findNode(root.down,query,position + 1);
					}
				}
			}
		}
	}
	
	private void recurrsiveAddToMatchings(ternaryTreeNode root,String query,String generated){
		
		if(root.left != null){
			recurrsiveAddToMatchings(root.left, query,generated);
		}
		if(root.right != null){
			recurrsiveAddToMatchings(root.right, query,generated);
		}
		generated += Character.toString(root.character);
		if(root.endNode){
			matchings.add(generated);
			indices.add(root.index);
		}
		if(root.down != null){
			recurrsiveAddToMatchings(root.down, query,generated);
		}
	}
	
	
	private void searchWithinHammingDistance(ternaryTreeNode root,String query,int index,int distance,String generated){
		
		if(root == null | distance<0){
			return;
		}
		//go left
		if(distance>0 | query.charAt(index) < root.character){
			searchWithinHammingDistance(root.left,query,index,distance,generated);
		}
		//go right
		if(distance > 0 | query.charAt(index) > root.character){
			searchWithinHammingDistance(root.right,query,index,distance,generated);
		}
		
		if(root.character == '$'){
			if(query.length() - index <= distance){
				//No need of farther exploration
				matchings.add(generated + "$");
				indices.add(root.index);
			}
		}
		else{
			//TODO: Distance = 0 case
			if(index == query.length() - 1){
				if(root.character == query.charAt(index)){
					generated += Character.toString(root.character);
					ReqNodes.add(root);
					Generated.add(generated);
				}
				else{
					if(distance > 0){
						generated += Character.toString(root.character);
						ReqNodes.add(root);
						Generated.add(generated);
					}
					else{
						//do nothing
					}
				}
			}
			else{
				if(root.character == query.charAt(index)){
					searchWithinHammingDistance(root.down,query,index+1,distance,generated + Character.toString(root.character));
				}
				else{
					if(distance > 0){
						searchWithinHammingDistance(root.down,query,index+1,distance - 1,generated + Character.toString(root.character));					
					}
					else{
						//do nothing
					}
				}
			}
		}
	}
}
