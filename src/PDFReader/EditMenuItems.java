package PDFReader;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Rectangle;

import javax.swing.JTextField;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.grouping.PdfGroupingAlgorithms;
import org.jpedal.grouping.SearchType;
import org.jpedal.objects.PdfPageData;

public class EditMenuItems {
	
	private static float[] scalings = { 0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 2.0f, 4.0f};
	
	private final int maxScalings = 6;
	
	public static int zoomIn(PdfDecoder pdfDecoder,int currentPage,int currentScalingIndex){
		
		if(currentScalingIndex < 6){
			currentScalingIndex++;
		}
		pdfDecoder.setPageParameters(scalings[currentScalingIndex], currentPage);
		pdfDecoder.updateUI();
		return(currentScalingIndex);
	}
	
	public static int zoomOut(PdfDecoder pdfDecoder,int currentPage,int currentScalingIndex){
		
		if(currentScalingIndex > 0){
			currentScalingIndex--;
		}
		pdfDecoder.setPageParameters(scalings[currentScalingIndex], currentPage);
		pdfDecoder.updateUI();
		return(currentScalingIndex);
	}
	
	
	
	public static void searchWord(PdfDecoder pdfDecoder,int currentPage , String wordArg , JTextField searchArea , Container containerPane) {
		int pageNow = currentPage ;
		for (pageNow = currentPage ; pageNow <= pdfDecoder.getPageCount() ; ++ pageNow){	
			System.out.println("----------------------");
            System.out.println("Page "+pageNow);
           // containerPane.add(searchArea,BorderLayout.SOUTH );
            try {
				pdfDecoder.decodePage(pageNow);
				PdfGroupingAlgorithms currentGrouping = pdfDecoder.getGroupingObject();
				if (currentGrouping != null){
					int X1 , X2 , Y1 , Y2 ;
					PdfPageData currentPageData = pdfDecoder.getPdfPageData();
					  X1 = currentPageData.getMediaBoxX(currentPage);
                      X2 = currentPageData.getMediaBoxWidth(currentPage) + X1;

                      Y2 = currentPageData.getMediaBoxY(currentPage);
                      Y1 = currentPageData.getMediaBoxHeight(currentPage) + Y2;
                      
                      float[] coOrds ;
                      try{
                    	  //search Word must come from a text Box or somthing .... !!!
                    	  coOrds = currentGrouping.findText(new Rectangle(0 , 0 , X2 , Y1), 1 , new String[] {searchArea.getText()} , SearchType.MUTLI_LINE_RESULTS ) ;
                    	  System.out.println(X1 + " " + Y1 + " " + X2 + " " + Y2 + "size : "+coOrds.length );
                    	  int i = 0 ;
                    	  for(i=0 ; i<coOrds.length  ; i=i+5 ){
                    		  System.out.println("Found in this page at ("+coOrds[i] + " , " + coOrds[i+1] + ")  (" + coOrds[i+2] + " , " + coOrds[i+3] + ") ");
                    		  
                    	  }
                    			  				   
                      }
                      catch(PdfException e){
                    	  e.printStackTrace();
                      }
                      //containerPane.remove(searchArea);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
             
		}
		
	}

}
