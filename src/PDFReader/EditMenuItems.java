package PDFReader;

import org.jpedal.PdfDecoder;

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
}
