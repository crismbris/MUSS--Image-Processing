package tema2;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

import java.awt.image.BufferedImage;
import java.util.Scanner;

public class exercise_02b_compre {
	
	public exercise_02b_compre() {
		
	}
	
	public static String file1;
	public static String file2;
	public static BufferedImage image1;
	public static BufferedImage image2;
	public static GrayU8 imgray8;
	public static GrayU8 im2gray8;
	
	
	public static boolean test = false;
	


	public static void main(String[] args) {
		
		if(args.length!=2) {
			System.out.println("ERROR: format input exercice_02b_compare <file1> <file2>");
			System.exit(1);
		}

		file1=args[0];file2=args[1];

		image1 = UtilImageIO.loadImage(file1);
		image2 = UtilImageIO.loadImage(file2);
		if(image1==null ) {
			System.out.println("Image 1 not found");
			System.exit(1);
		}
		if(image2==null) {
			System.out.println("Image 2 not found");
			System.exit(1);
		}
		imgray8 = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);
		im2gray8 = ConvertBufferedImage.convertFromSingle(image2, null, GrayU8.class);
		
		auxiliary0X.compare(imgray8, im2gray8);

	}

}
