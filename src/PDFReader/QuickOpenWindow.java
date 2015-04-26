package PDFReader;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

import org.jpedal.PdfDecoder;

public class QuickOpenWindow{
	
	JPanel mainPanel = new JPanel();
	JComboBox<comboBoxNode> comboBox;
	//should be updated on another thread
	String currentPattern;
	ArrayList<comboBoxNode> storage = new ArrayList<comboBoxNode>();
	comboBoxNode[] storageArray;
    PdfDecoder pdfDecoder;
    JFrame pdfDisplayFrame;
	
	public QuickOpenWindow(PdfDecoder pdfDec,JFrame frame)
	{
		this.pdfDecoder = pdfDec;
		pdfDisplayFrame = frame;
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
    			int desired = comboBox.getSelectedIndex();
				System.out.println(ReadSer.fileArray.get(ReadSer.fileTree.indices.get(desired)));
				String currentFileName = ReadSer.fileArray.get(ReadSer.fileTree.indices.get(desired));
				int currentPage = 1;
				
				try {
					pdfDecoder.closePdfFile();
					pdfDecoder.setExtractionMode(PdfDecoder.TEXT); //extract just text
		            PdfDecoder.init(true);
					
					pdfDecoder.openPdfFile(currentFileName);
					pdfDecoder.decodePage(currentPage);

					pdfDecoder.waitForDecodingToFinish();
					pdfDecoder.setPageParameters(1,1); 
					pdfDecoder.invalidate();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				pdfDisplayFrame.setTitle(currentFileName);
		        
				pdfDisplayFrame.repaint();
				
				
				
				comboBox.removeAllItems();
				comboBox.setPopupVisible(false);
				//Now disposing of main panel
				
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
        mainPanel.add(patternPanel);
        
        
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
	}
}
