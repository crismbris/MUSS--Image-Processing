package tema2;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class exercise_08_removeNoise {
	
	
	

	static String file;
	
	public static BufferedImage image1;
	public static GrayU8 im0;
	public static GrayU8 im1;
	public static GrayU8 im2;
	public static GrayU8 im3;
	public static GrayU8 im4;
	public static Queue<Integer> values= new LinkedList<Integer>();
	
	
	
	
	public static void main(String []args) {
		if(args.length!=1) {
    		System.out.println("ERROR: format input exercice_08_removeNoise <file> ");
    		System.exit(1);
    	}
    	
    	file = args[0];	
		image1 = UtilImageIO.loadImage(file);
		if(image1 == null) {
			System.out.println("Image not found");
			System.exit(1);
		}
		im0 = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);
		
		im1 = auxiliary0X.opening(im0, 1, values);
		im2 = auxiliary0X.closing(im0, 1, values);
		im3 = auxiliary0X.closing_opening(im0, 1, values);
		im4 = auxiliary0X.opening_closing(im0, 1, values);
		
		UtilImageIO.saveImage(im1, "tema2_im/08/opening_filter.pgm");
		UtilImageIO.saveImage(im2, "tema2_im/08/closing_filter.pgm");
		UtilImageIO.saveImage(im3, "tema2_im/08/closing_opening_filter.pgm");
		UtilImageIO.saveImage(im4, "tema2_im/08/opening_closing_filter.pgm");
		
		
        System.out.println("Archive filter 1: opening_filter.pgm");
        System.out.println("Archive filter 2: closing_filter.pgm");
        System.out.println("Archive filter 3: closing_opening_filter.pgm");
        System.out.println("Archive filter 4: opening_closing_filter.pgm");
		
	}

}

