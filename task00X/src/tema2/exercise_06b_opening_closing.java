package tema2;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class exercise_06b_opening_closing {
	
	static int dim;
	static String file;
	
	public static BufferedImage image1;
	public static GrayU8 im8;
	public static Queue<Integer> values= new LinkedList<Integer>();
	
	
	
	public static void main (String [] args) {
		if(args.length!=2) {
			System.out.println("ERROR: format input exercice_06b_opening_closing <file> <dimension> ");
    		System.exit(1);
    	}
    	
    	file = args[0];
    	dim = Integer.parseInt(args[1]);
    	image1 = UtilImageIO.loadImage(file);
		if(image1==null || dim<=0) {
			System.out.println("Image not found or bad dimension");
			System.exit(1);
		}
		
		
		im8 = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);

		
		im8 = auxiliary0X.opening_closing(im8, dim, values);
		
		UtilImageIO.saveImage(im8, "tema2_im/06b/opening_closingD"+dim+".pgm");
        System.out.println("Archivo: opening_closingD"+dim+".pgm");
		
	}

}
