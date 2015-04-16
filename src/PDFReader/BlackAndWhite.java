package PDFReader;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.jpedal.color.PdfPaint;
import org.jpedal.external.JPedalHelper;
import org.jpedal.fonts.PdfFont;
import org.jpedal.fonts.glyph.PdfJavaGlyphs;

public class BlackAndWhite implements JPedalHelper{

	public BlackAndWhite(boolean inverted){
		
		this.inverted = inverted;
	}
	
	public boolean inverted;
	
	@Override
	public Font getJavaFontX(PdfFont arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedImage processImage(BufferedImage image, int pageNumber, boolean isPrinting) {
		// TODO Auto-generated method stub
		 BufferedImage newImage=null;

        
        //black and white conversion
        newImage=new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        final Graphics2D newG2bw=newImage.createGraphics();
        if(inverted){
        	newG2bw.setPaint(Color.BLACK);
        }    
        else{
        	newG2bw.setPaint(Color.WHITE);
        }
        newG2bw.fillRect(0,0,image.getWidth(), image.getHeight());
        newG2bw.drawImage(image,0,0,null);

        //grayscale conversion
  //      newImage=new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
  //      Graphics2D newG2=newImage.createGraphics();
  //      newG2.setPaint(Color.WHITE);
  //      newG2.fillRect(0,0,image.getWidth(), image.getHeight());
  //      newG2.drawImage(image,0,0,null);


        return newImage;
	}

	@Override
	public Font setFont(PdfJavaGlyphs arg0, String arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPaint(Graphics2D g2, PdfPaint color, int pageNumber, boolean isPrinting) {
		// TODO Auto-generated method stub
		
		int rgb = color.getRGB();
		
		//inverting the color of pdf display
		float[] rgbArray = new float[3];
		//r value
		rgbArray[0] = ((rgb>>16) & 255)/255f;
		//g value
		rgbArray[1] = ((rgb>>8) & 255)/255f;
		//b value
		rgbArray[2] = (rgb & 255)/255f;
	
		//to gray scale
		//ColorSpace cs=ColorSpace.getInstance(ColorSpace.CS_GRAY);
        //float[] grayVal=cs.fromRGB(rgbArray);
        
		if(inverted){
			rgbArray[0] = 255;
			rgbArray[1] = 255;
			rgbArray[2] = 255;
			System.out.println("Hi");
		}
        
        /*if(inverted){
        	grayVal[0] = 255;
        	System.out.println(grayVal[0]);
            
        }*/
        
        
		Color invertedColor = new Color(rgbArray[0],rgbArray[1],rgbArray[2]);
		g2.setPaint(invertedColor);
	}
	
}
