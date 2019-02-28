package tema2;

import boofcv.io.*;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.*;
import boofcv.struct.image.GrayU8;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class exercise_03a_erosion {
	static int dim;

	static String file;

	public static Queue<Integer> values = new LinkedList<Integer>();

	public static BufferedImage image1;
	public static GrayU8 im8;
	public static GrayU8 copy;

	static int min;
	
	

	

	
	
    public static void main(String[] args) {
    	
		if(args.length!=2) {
			System.out.println("ERROR: format input exercice_03a_eorsion <file> <dimension> ");
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
		
		GrayU8 img_gray8_aux = auxiliary0X.erosion(im8, dim, values);
		
        UtilImageIO.saveImage(img_gray8_aux, "tema2_im/03a/erosionD"+dim+".pgm");
        System.out.println("Output: erosionD"+dim+".pgm");

    }
    

}