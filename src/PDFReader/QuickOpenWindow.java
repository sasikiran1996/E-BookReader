package PDFReader;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

public class QuickOpenWindow extends JPanel{
	
	private static final long serialVersionUID = -6319430115413306158L;
	JComboBox<comboBoxNode> comboBox;
	//should be updated on another thread
	ArrayList<String> autoCompletePatterns = new ArrayList<String>();
	ArrayList<Integer> indices = new ArrayList<Integer>();
	String currentPattern;
	ArrayList<comboBoxNode> storage = new ArrayList<comboBoxNode>();
	comboBoxNode[] storageArray;
    
	
	public QuickOpenWindow()
	{
		
		JLabel patternLabel1 = new JLabel("Enter the File Name or");
        JLabel patternLabel2 = new JLabel("Dynamically select the file ot be opened");
        
        comboBox = new JComboBox<comboBoxNode>();
 //       comboBox.addItem(new comboBoxNode("SasiKiran",0));
 //       comboBox.addItem(new comboBoxNode("ShivaKrishna",1));
        comboBox.setEditable(true);
       
        // get the combo boxes editor component
        JTextComponent editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
        //TODO:check combo box model type
        
        editor.setDocument(new comboBoxTextField(comboBox));
      
        editor.addKeyListener(new KeyAdapter() {
        	
        	//Key Released required because field should first update
        	public void keyReleased(KeyEvent e){
        		if(comboBox.isDisplayable()){
        			//first remove all existing entries
        			//next add all relevant entries
        			//String typedWord = ((JTextField)comboBox.getEditor().getEditorComponent()).getText();
        			JTextComponent src = (JTextComponent)e.getSource();
        			String typedWord = (String)src.getText();
        			currentPattern = typedWord;
        			//String typedWord = (String)comboBox.getSelectedItem();
    				//currentPattern = typedWord;
    				System.out.println(currentPattern);
    				/*
        			ReadSer.fileTree.prefixedSearch(currentPattern.toString());
    				System.out.println(ReadSer.fileTree.matchings.size());
        			for(int i =0;i<ReadSer.fileTree.matchings.size();i++){
    					storage.add(new comboBoxNode(ReadSer.fileTree.matchings.get(i),ReadSer.fileTree.indices.get(i)));
    					System.out.println(ReadSer.fileTree.matchings.get(i));
        			}
        			
        			/*comboBox.setModel(new DefaultComboBoxModel(ReadSer.fileTree.matchings.toArray()) {
        			      protected void fireContentsChanged(Object obj, int i, int j) {
        			          System.out.println("Ha");
        			      }
        			    });*/
    				/*
        			storageArray = new comboBoxNode[storage.size()];
        			comboBox.setModel(new DefaultComboBoxModel<comboBoxNode>(storage.toArray(storageArray)));
        			*/comboBox.setPopupVisible(true);
        		}
        	}
		});
		
		
			
        
        //TODO:Add Action Listener here
        comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				/*String typedWord = comboBox.getSelectedItem().toString();
				currentPattern = typedWord;*/
				//on click something should happen
    			comboBoxNode desired = (comboBoxNode)comboBox.getSelectedItem();
    			
				
				comboBox.removeAllItems();
				comboBox.setPopupVisible(false);
			}
		});
        
        //Lay out everything.	
        JPanel patternPanel = new JPanel();
        patternPanel.setLayout(new BoxLayout(patternPanel,
                               BoxLayout.PAGE_AXIS));
        patternPanel.add(patternLabel1);
        patternPanel.add(patternLabel2);
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        patternPanel.add(comboBox);
        
        patternPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(patternPanel);
        
        
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
	}
}
