package PDFReader ;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.jpedal.PdfDecoder;
import org.jpedal.examples.viewer.utils.FileFilterer;
import org.jpedal.fonts.FontMappings;

public class PDFPanel{
	
	private JFrame pdfDisplayFrame = new JFrame();
	private PdfDecoder pdfDecoder ; 
	private String currentFileName;
	private int currentPage = 1 ;
	public JMenu menu ;
	public JMenuBar menuBar ;
	
	public PDFPanel(String name){
		pdfDecoder = new PdfDecoder(true) ;
		FontMappings.setFontReplacements();
		currentFileName = name ;
		try {
			pdfDecoder.openPdfFile(currentFileName);
			pdfDecoder.decodePage(currentPage);
			//1 for 100 % and one for initially displaying page 1
			pdfDecoder.setPageParameters(1, 1);  
			//making changes
			pdfDecoder.invalidate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		initializeViewer();
	    
	    //set page number display
	    
	}
	
	public PDFPanel(){
		pdfDisplayFrame.setTitle("Empty File");
		pdfDecoder = new PdfDecoder(true) ;
		initializeViewer() ;
	}
	
	protected void initializeViewer(){
		
		pdfDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container containerPane = pdfDisplayFrame.getContentPane();
		containerPane.setLayout(new BorderLayout());
		menuBar = makeMenuBar() ;
		//JButton openButton = makeOpenButton() ;
		//JPanel topMenuBar = new JPanel() ;
		
		containerPane.add(menuBar, BorderLayout.BEFORE_FIRST_LINE);
	    
		pdfDisplayFrame.pack();
	    final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    pdfDisplayFrame.setSize(screen.width/2,screen.height/2);
		pdfDisplayFrame.setVisible(true);
	}
	
	private JMenuBar makeMenuBar(){
		JMenuBar mBar = new JMenuBar() ;
		JMenu fileMenu = new JMenu("File") ;
		JMenuItem fileItemOpen = fileMenu.add("Open");
		
		fileItemOpen.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
				selectFile();
			}
		});
		
		JMenuItem fileItemQuickOpen = fileMenu.add("QuickOpen");
		
		fileItemQuickOpen.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		
		JMenuItem fileItemImport = fileMenu.add("Import");
		fileItemImport.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		JMenuItem fileItemExit = fileMenu.add("Exit");
		fileItemExit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here
				pdfDecoder.dispose();
				pdfDisplayFrame.dispose();
			}
		});
		
		mBar.add(fileMenu);
		
		JMenu editMenu = new JMenu("Edit") ;
		JMenuItem editItemHighlight = editMenu.add("Highlight");
		editItemHighlight.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		JMenuItem editItemInvertcolors = editMenu.add("Invert Colors");
		editItemInvertcolors.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		JMenuItem editItemZoomIn = editMenu.add("Zoom In");
		editItemZoomIn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		JMenuItem editItemZoomOut = editMenu.add("Zoom Out");
		editItemZoomOut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		JMenuItem editItemFullScreen = editMenu.add("Full Screen");
		editItemFullScreen.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		mBar.add(editMenu);
		
		JMenu goMenu = new JMenu("Go") ;
		
		JMenuItem goItemNextPage = goMenu.add("Next Page");
		goItemNextPage.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		
		JMenuItem goItemPrevPage = goMenu.add("Previous Page");
		goItemPrevPage.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		JMenuItem goItemFirstPage = goMenu.add("First Page");
		goItemFirstPage.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		JMenuItem goItemLastPage = goMenu.add("Last Page");
		goItemLastPage.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		mBar.add(goMenu) ;
		
		JMenu bookMarkMenu = new JMenu("Bookmarks") ;
		mBar.add(bookMarkMenu);
		//Adding the bookmarks into the menu automatically ..... <----- To be done here 
		
		JMenu helpMenu = new JMenu("Help") ;
		JMenuItem helpMenuItemHelp = helpMenu.add("Help");
		helpMenuItemHelp.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		JMenuItem helpMenuItemCredits = helpMenu.add("Credits");
		helpMenuItemHelp.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		mBar.add(helpMenu) ;
		
		
		return mBar ;
		
		
		
		
	}
	

	protected JButton makeOpenButton(){
		JButton openButton = new JButton();
		openButton.setIcon(new ImageIcon(getClass().getResource("/org/jpedal/examples/viewer/res/open.gif"))); //$NON-NLS-1$
	    openButton.setText("Open");
	    openButton.setToolTipText("Open a file"); 
	    openButton.setBorderPainted(false);
	    openButton.addActionListener(new ActionListener() {
	    	
	    	public void actionPerformed( ActionEvent e) {
	            selectFile();
	      }
	    });
	    
	    return openButton;
	}
	
	public void selectFile(){
		
		JFileChooser fileChooser = new JFileChooser(".");
		//setting the selectable option to files only 
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		String[] PDF = {"pdf"};
		//only pdfs must be chosen
		fileChooser.addChoosableFileFilter(new FileFilterer(PDF, "(*.pdf)"));
		
		int resultOfFileSelect = JFileChooser.ERROR_OPTION;
	    
		//until a valid file is chosen
		while(resultOfFileSelect==JFileChooser.ERROR_OPTION){
		      
		      resultOfFileSelect = fileChooser.showOpenDialog(pdfDisplayFrame);
		      
		      if(resultOfFileSelect==JFileChooser.ERROR_OPTION) {
		                System.err.println("JFileChooser error");
		            }
		      
		      if(resultOfFileSelect==JFileChooser.APPROVE_OPTION){
		        currentFileName = fileChooser.getSelectedFile().getAbsolutePath();
		        
		        currentPage = 1;
		      }
		}
		
		
		try {
			pdfDecoder.closePdfFile();
			pdfDecoder.openPdfFile(currentFileName);
			
			pdfDecoder.decodePage(currentPage);
			pdfDecoder.setPageParameters(1,1); 
			pdfDecoder.invalidate();
	          
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pdfDisplayFrame.setTitle(currentFileName);
        
		pdfDisplayFrame.pack();
		pdfDisplayFrame.repaint();
	}
	
	public static void  main(String[] args){
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				DisplayTheme.setSystemTheme();
				new PDFPanel("report_lab3.pdf");
				
			}
		});
		
	}
}
