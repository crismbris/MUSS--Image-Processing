package tema3;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class subtraction {

	public static String im1;
	public static BufferedImage image1;
	public static GrayU8 original;
	
	public static Queue<Integer> values= new LinkedList<Integer>();
	


	public static void main(String []args) {
		
		if(args.length!=1) {
			System.out.println("error en el número de inputs. Introduce el path de la foto");
			System.out.println(1);
		}
		im1 = args[0];
		image1 = UtilImageIO.loadImage(im1);
		
		
		original = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);
		
		
		GrayU8 imE = auxiliaryFunctions21.erosion(original, 1, values);
		
		original = auxiliaryFunctions21.subtraction(original, imE);
		
		UtilImageIO.saveImage(original, "tema3_im/subtract/subtractExample.pgm");
        System.out.println("Archivo: subtractExample.pgm");
		
		
		
		
		
		
	}
}
