package PDFReader ;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.jpedal.Display;
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
	private int currentScalingIndex = 2; 
	private JTextField searchArea = new JTextField("Search");
	Container containerPane ;
	int fullScreenFlag = 0 ;
	public PDFPanel(String name){
		
		pdfDecoder = new PdfDecoder(true) ;
		FontMappings.setFontReplacements();
		currentFileName = name ;
		try {
			pdfDecoder.openPdfFile(currentFileName);
			pdfDecoder.decodePage(currentPage);
			
			pdfDecoder.waitForDecodingToFinish();
			//1 for 100 % and one for initially displaying page 1
			pdfDecoder.setPageParameters(1, 1);  
			//making changes
			pdfDecoder.invalidate();
			//pdfDisplayFrame.repaint();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		initializeViewer();
	    
	    //set page number display
	    
	}
	
	 public void fullScreen() {
		 fullScreenFlag = 1 ;
			//pdfDisplayFrame.setVisible(false);
	 	pdfDisplayFrame.dispose();
	 //	pdfDisplayFrame.setUndecorated(true);
	 	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gs = ge.getScreenDevices();
	    GraphicsDevice gdev = gs[0];
	    gdev.setFullScreenWindow(pdfDisplayFrame);
	    
	  }
	 
	 public void removeFullScreen(){
		 fullScreenFlag = 0 ;
		 pdfDisplayFrame.dispose();
		// pdfDisplayFrame.setUndecorated(true);
		 GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		 GraphicsDevice[] gs = ge.getScreenDevices();
		 GraphicsDevice gdev = gs[0];
		 gdev.setFullScreenWindow(null);
		 pdfDisplayFrame.setVisible(true);
		 
		 //pdfDisplayFrame.pack();
		// pdfDisplayFrame.setUndecorated(false);

	 }
	
	public PDFPanel(){
		pdfDisplayFrame.setTitle("Empty File");
		pdfDecoder = new PdfDecoder(true) ;
		initializeViewer() ;
	}
	
	public void addBookMark(int pageNum , JMenu bookMarkMenu){
		final int page = pageNum ;
		JMenuItem bookMarkMenuItem = bookMarkMenu.add("Page : " + new Integer(pageNum).toString());
		bookMarkMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			currentPage = page ;
			remakePage();
			}
		});
	}
		
	
	protected void initializeViewer(){
		
		pdfDecoder.setDisplayView(Display.SINGLE_PAGE, Display.DISPLAY_CENTERED);
		pdfDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		containerPane = pdfDisplayFrame.getContentPane();
		containerPane.setLayout(new BorderLayout());
		menuBar = makeMenuBar() ;
		//JButton openButton = makeOpenButton() ;
		//JPanel topMenuBar = new JPanel() ;
		
		containerPane.add(menuBar, BorderLayout.BEFORE_FIRST_LINE);
		
		menuBar.add(Box.createHorizontalGlue());
		configureSearchBar();
		
		
		
		//creating horizontal glue
		menuBar.add(searchArea);
	    
		//setup scrollpane with pdf display inside
		JScrollPane display = initPdfDisplay();
	    containerPane.add(display,BorderLayout.CENTER);
	    //Add features to search bar
	    searchArea.setBackground(Color.gray);
	    
	   //opens at center
		pdfDisplayFrame.setLocationRelativeTo(null);
		pdfDisplayFrame.pack();
	    final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    pdfDisplayFrame.setSize(screen.width/2,screen.height/2);
		pdfDisplayFrame.setVisible(true);
	}
	
	private JMenuBar makeMenuBar(){
		JMenuBar mBar = new JMenuBar() ;
		JMenu fileMenu = new JMenu("File") ;
		JMenuItem fileItemOpen = fileMenu.add("Open");
		fileItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		fileItemOpen.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
				selectFile();
			}
		});
		
		JMenuItem fileItemQuickOpen = fileMenu.add("QuickOpen");
		fileItemQuickOpen.setAccelerator(KeyStroke.getKeyStroke("control alt O"));
		fileItemQuickOpen.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
				//opening new window on separate thread
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//DisplayTheme.setSystemTheme();
						JFrame frame = new JFrame("Quick Open");

				        //Create and set up the content pane.
						QuickOpenWindow qOW = new QuickOpenWindow(pdfDecoder,pdfDisplayFrame);
				        JComponent newContentPane = qOW.mainPanel;
				        newContentPane.setOpaque(true); //content panes must be opaque
				        frame.setContentPane(newContentPane);
				        
				        frame.setLocationRelativeTo(null);
					    final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
					    frame.setSize(screen.width/4,screen.height/4);

				        //Display the window.
				        frame.pack();
				        frame.setVisible(true);

				        
				        
					}
				});
				
			}
		});
		
		
		JMenuItem fileItemImport = fileMenu.add("Import");
		fileItemImport.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.CTRL_MASK));
		fileItemImport.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here
				ArrayList<String > list = Import.importPdfFiles() ;
				Import.makeTree(list) ;
				Import.importFileBKTree(list);
				new ReadSer() ;
				
				
			}
		});
		
		JMenuItem fileItemExit = fileMenu.add("Exit");
		fileItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));
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
		editItemHighlight.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
		editItemHighlight.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		JMenuItem editItemInvertcolors = editMenu.add("Invert Colors");
		editItemInvertcolors.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		editItemInvertcolors.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			}
		});
		
		JMenuItem editItemZoomIn = editMenu.add("Zoom In");
		editItemZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
		editItemZoomIn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
				currentScalingIndex = EditMenuItems.zoomIn(pdfDecoder,currentPage,currentScalingIndex);
			}
		});
		
		JMenuItem editItemZoomOut = editMenu.add("Zoom Out");
		editItemZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		editItemZoomOut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here
				currentScalingIndex = EditMenuItems.zoomOut(pdfDecoder,currentPage,currentScalingIndex);
			}
		});
		
		JMenuItem editItemSearch = editMenu.add("Search");
		editItemSearch.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		editItemSearch.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				//Get word from a text box and send it ...
				EditMenuItems.searchWord(pdfDecoder, currentPage, searchArea.getText() , containerPane);
			}
		});
		
		JMenuItem editItemFullScreen = editMenu.add("Full Screen");
		editItemFullScreen.setAccelerator(KeyStroke.getKeyStroke("F5"));
		editItemFullScreen.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			if (fullScreenFlag == 0 )
				fullScreen();
			else if (fullScreenFlag == 1){
				removeFullScreen();
			}
				
			}
		});
		
	
		
		mBar.add(editMenu);
		
		/*
		JMenu viewMenu = new JMenu("View");
		JCheckBoxMenuItem viewItemContinuous = new JCheckBoxMenuItem("Continuous");
		viewMenu.add(viewItemContinuous);
		viewItemContinuous.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//changing continuous flag
				if(continuous){
					continuous = false;
				}
				else{
					continuous = true;
				}
				
				int parameter = 0;
				if(continuous){
					parameter += 2;
				}
				if(facing){
					parameter += 1;
				}
				
				setViewMode(parameter);
			}
		});
		
		
		JCheckBoxMenuItem viewItemFacing = new JCheckBoxMenuItem("Facing");
		viewMenu.add(viewItemFacing);
		viewItemFacing.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//changing continuous flag
				if(facing){
					facing = false;
				}
				else{
					facing = true;
				}
				
				int parameter = 0;
				if(continuous){
					parameter += 2;
				}
				if(facing){
					parameter += 1;
				}
				
				setViewMode(parameter);
			}
		});
		
		
		mBar.add(viewMenu);
		*/
		
		JMenu goMenu = new JMenu("Goto") ;
		
		JMenuItem goItemNextPage = goMenu.add("Next Page");
		goItemNextPage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,ActionEvent.CTRL_MASK));
		
		
		goItemNextPage.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
			if(currentPage < pdfDecoder.getPageCount()){
				currentPage ++ ;
				remakePage();
			}
				
			}
		});
		
		
		JMenuItem goItemPrevPage = goMenu.add("Previous Page");
		goItemPrevPage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP,ActionEvent.CTRL_MASK));
		
		goItemPrevPage.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here
				if(currentPage >1){
					currentPage -- ;
					remakePage();
				}
			}
		});
		
		JMenuItem goItemFirstPage = goMenu.add("First Page");
		goItemFirstPage.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
				currentPage = 1 ;
				remakePage();
			}
		});
		
		JMenuItem goItemLastPage = goMenu.add("Last Page");
		goItemLastPage.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			//Open here 
				currentPage = pdfDecoder.getPageCount();
				remakePage();
			}
		});
		mBar.add(goMenu) ;
		
		final JMenu bookMarkMenu = new JMenu("Bookmarks") ;
		mBar.add(bookMarkMenu);
		JMenuItem bookMarkMenuAdd = bookMarkMenu.add("Add");
		bookMarkMenuAdd.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
			
				addBookMark(currentPage, bookMarkMenu);
			}
		});
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
	
	
	public void selectFile(){
		
		JFileChooser fileChooser = new JFileChooser(".");
		//setting the selectable option to files only 
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		String[] PDF = {"pdf"};
		//only pdfs must be chosen
		//arg0 is extension and arg1 is regex
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
			pdfDecoder.setExtractionMode(PdfDecoder.TEXT); //extract just text
            PdfDecoder.init(true);
			pdfDecoder.openPdfFile(currentFileName);
			
			pdfDecoder.decodePage(currentPage);

			pdfDecoder.waitForDecodingToFinish();
			pdfDecoder.setPageParameters(1,1); 
			pdfDecoder.invalidate();
			//pdfDisplayFrame.repaint();
	          
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pdfDisplayFrame.setTitle(currentFileName);
        
		pdfDisplayFrame.repaint();
	}
	
	public void remakePage(){
		try {
		//	pdfDecoder.closePdfFile();
			//pdfDecoder.openPdfFile(currentFileName);
			
			pdfDecoder.decodePage(currentPage);

			pdfDecoder.waitForDecodingToFinish();
			pdfDecoder.setPageParameters(-1,currentPage); 
			pdfDecoder.invalidate();
			//pdfDisplayFrame.repaint();
	          
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pdfDisplayFrame.setTitle(currentFileName);
		//EditMenuItems.zoomIn(pdfDecoder, currentPage, currentScalingIndex);
        
		pdfDisplayFrame.repaint();
	}

	//uses JScroll pane to set pdf display and its scroll bar 
	private JScrollPane initPdfDisplay() {
	    
		//TODO: change scroll speed
	    JScrollPane currentScroll = new JScrollPane();
	    currentScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    currentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    
	    currentScroll.setViewportView(pdfDecoder);
	        
	    return currentScroll;
	  }
	
	private void configureSearchBar(){

		searchArea.setLayout(new BorderLayout());
		
		searchArea.setPreferredSize(new Dimension(100,20));
		searchArea.setMinimumSize(new Dimension(100,20));
		searchArea.setMaximumSize(new Dimension(100,20));
		searchArea.setBackground(menuBar.getBackground());
		searchArea.add(new JSeparator(JSeparator.VERTICAL),BorderLayout.AFTER_LINE_ENDS);
		Image SearchImage = null;
		
		try {
			SearchImage = ImageIO.read(new FileInputStream("search-icon.png"));
			SearchImage = SearchImage.getScaledInstance(15, 15, Image.SCALE_FAST);
			ImageIcon icon = new ImageIcon(SearchImage);
			JLabel searchLabel = new JLabel(icon);
			
			searchLabel.setCursor(Cursor.getDefaultCursor());
			searchArea.add(searchLabel,BorderLayout.LINE_END);
			searchLabel.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					//Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					//Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					//Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					//Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					//Auto-generated method stub
					EditMenuItems.searchWord(pdfDecoder, currentPage, searchArea.getText(), containerPane);
					searchArea.setText("");
				}
			});
			
		} catch (FileNotFoundException e) {
			//Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	private void setViewMode(int parameter){
		
		if(parameter == 0){
			pdfDecoder.setDisplayView(Display.SINGLE_PAGE, Display.DISPLAY_CENTERED);
			pdfDecoder.invalidate();
			return;
		}
		if(parameter == 1){
			pdfDecoder.setDisplayView(Display.FACING, Display.DISPLAY_CENTERED);
			pdfDecoder.invalidate();
			return;
		}
		if(parameter == 2){
			pdfDecoder.setDisplayView(Display.CONTINUOUS, Display.DISPLAY_CENTERED);
			pdfDecoder.invalidate();
			return;
		}
		if(parameter == 3){
			pdfDecoder.setDisplayView(Display.CONTINUOUS_FACING, Display.DISPLAY_CENTERED);
			pdfDecoder.invalidate();
			return;
		}
	}
	 */
}
