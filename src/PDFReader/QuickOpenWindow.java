package PDFReader;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QuickOpenWindow extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6319430115413306158L;
	JComboBox comboBox;
	//should be updated on another thread
	String[] autoCompletePatterns = {"Junkyard"};
	String currentPattern;

	
	public QuickOpenWindow()
	{
		
		JLabel patternLabel1 = new JLabel("Enter the File Name or");
        JLabel patternLabel2 = new JLabel("Dynamically select the file ot be opened");
        
        comboBox = new JComboBox(autoCompletePatterns);
        comboBox.setEditable(true);
       
        //TODO:Add Action Listener here
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
