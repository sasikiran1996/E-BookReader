

import java.awt.* ;
import java.net.URL;

import javax.swing.*;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.examples.viewer.utils.FileFilterer;
import org.jpedal.fonts.FontMappings;

public class JPanelDemo extends JFrame {
	
	private PdfDecoder pdfDecoder ; 
	private String fileName ;
	private int currentPage = 1 ;
	
	public JPanelDemo(String name){
		pdfDecoder = new PdfDecoder() ;
		FontMappings.setFontReplacements();
		fileName = name  ;
		try {
			pdfDecoder.openPdfFile(fileName);
			pdfDecoder.decodePage(currentPage);
			pdfDecoder.setPageParameters(1, 1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static int main(String[] args){
		System.out.println("Hello ...");
		//new JPanelDemo("shiva.pdf");
		return 1 ;
	}
}

