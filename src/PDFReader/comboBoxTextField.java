package PDFReader;

import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class comboBoxTextField extends PlainDocument{

	public boolean selected = false;
	private static final long serialVersionUID = 3097038476091400801L;
	ArrayList<comboBoxNode> storage = new ArrayList<comboBoxNode>();
	comboBoxNode[] storageArray;
    JComboBox<comboBoxNode> comboBox;
	
    public comboBoxTextField(JComboBox<comboBoxNode> cb){
    	comboBox = cb;
    }
    
	
	public void remove(int offs, int len) throws BadLocationException {
	        // return immediately when selecting an item
		if(selected) return;
        super.remove(offs, len);
        String content = getText(0, getLength());
        System.out.println(content);
        
        storage = new ArrayList<comboBoxNode>();
        
        ReadSer.fileTree.prefixedSearch(content);
        System.out.println(ReadSer.fileTree.matchings.size());
		for(int i =0;i<ReadSer.fileTree.matchings.size();i++){
			storage.add(new comboBoxNode(ReadSer.fileTree.matchings.get(i),ReadSer.fileTree.indices.get(i)));
			System.out.println(ReadSer.fileTree.matchings.get(i));
		}
		
		selected = true;
		storageArray = new comboBoxNode[storage.size()];
		comboBox.setModel(new DefaultComboBoxModel<comboBoxNode>(storage.toArray(storageArray)));
		selected = false;
		
		comboBox.setPopupVisible(true);

	}
	
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        // return immediately when selecting an item
		if(selected) return;
        System.out.println("insert " + str + " at " + offs);
        // insert the string into the document
        super.insertString(offs, str, a);
        // get the resulting string
        String content = getText(0, getLength());
        
        System.out.println(content);
        
        // lookup a matching item
        /*Object item = lookupItem(content);
        // select the item (or deselect if null)
        if(item!=model.getSelectedItem()) System.out.println("Selecting '" + item + "'");
        selecting = true;
        model.setSelectedItem(item);
        selecting = false;*/
        storage = new ArrayList<comboBoxNode>();
        
        ReadSer.fileTree.prefixedSearch(content);
        System.out.println(ReadSer.fileTree.matchings.size());
		for(int i =0;i<ReadSer.fileTree.matchings.size();i++){
			storage.add(new comboBoxNode(ReadSer.fileTree.matchings.get(i),ReadSer.fileTree.indices.get(i)));
			System.out.println(ReadSer.fileTree.matchings.get(i));
		}
		
		selected = true;
		storageArray = new comboBoxNode[storage.size()];
		comboBox.setModel(new DefaultComboBoxModel<comboBoxNode>(storage.toArray(storageArray)));
		selected = false;

		comboBox.setPopupVisible(true);
	}
}
