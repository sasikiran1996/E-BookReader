package PDFReader;


public class comboBoxNode{
	
	
	public String nameOfFile;
	
	public int indexInArray;
	
	public comboBoxNode(String name,int index){
		
		nameOfFile = name;
		indexInArray = index;
	}
	
	@Override
	public String toString(){
		return this.nameOfFile;
	}
}
