package tema2;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

import java.awt.image.BufferedImage;
import java.util.Scanner;

public class exercise_02a_tresh {
	
	public static String file1;
	public static BufferedImage image1;
	public static GrayU8 imgray8;
	public static int value;
	


	public static void main(String[] args) {
		
		if(args.length!=2) {
			System.out.println("ERROR: format input exercice_02a_tresh <file> <value>");
			System.exit(1);
		}

		file1=args[0];
		value = Integer.valueOf(args[1]);
		if (value <0) {
			System.out.println("the arg value should be grater than 0");
			System.exit(1);
		}

		image1 = UtilImageIO.loadImage(file1);
		
		if(image1 == null ) {
			System.out.println("Image not found");
			System.exit(1);
		}
		

		imgray8 = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);


		imgray8 = auxiliary0X.thresh(imgray8,value);

		UtilImageIO.saveImage(imgray8, "tema2_im/02a/thresh_out.pgm");
		System.out.println("Output: thresh_out.pgm");
	}

}
