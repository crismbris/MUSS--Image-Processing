package tema3;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import java.io.Serializable;
import java.lang.Comparable;

import boofcv.gui.binary.VisualizeBinaryData;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class grassfire {
	
	public static String im1;
	public static int dim;
	public static BufferedImage image1;
	public static GrayU8 original;
	public static Queue<Integer> values= new LinkedList<Integer>();
	

	
	
	public static void main(String [] args) {
		
		if(args.length != 2) {
			System.out.println("Deben introducir dos argumentos");
			System.out.println("el primer algumento debe ser la imagen a la que se quiere aplicar el algoritmo");
			System.out.println("el segundo argumento la dimension que se desea aplicar");
			System.exit(1);
		}
		
		im1 = args[0];
		dim= Integer.parseInt(args[1]);
		
		
		
		image1 = UtilImageIO.loadImage(im1);
		original = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);
		
		
		BufferedImage result = VisualizeBinaryData.renderBinary(auxiliaryFunctions21.grassfire(original, 1), false, null);
		
		UtilImageIO.saveImage(result, "tema3_im/grassfire/grassfireExample.pgm");
        System.out.println("Archivo: grassfireExample.pgm");
		
		
		
	}

}
