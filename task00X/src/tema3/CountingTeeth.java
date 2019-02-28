package tema3;

import java.awt.image.BufferedImage;

import org.ddogleg.struct.Tuple2;
import boofcv.struct.image.GrayU8;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import java.io.Serializable;
import java.lang.Comparable;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class CountingTeeth {
	public static String im1;
	public static int dim;
	public static BufferedImage image1;
	public static GrayU8 original;
	public static Queue<Integer> values= new LinkedList<Integer>();
	
	
	public static void main(String[] args) {

		
		if(args.length !=2) {
			System.out.println("Numero de parametros erroneos");
			System.out.println("El programa debe tener dos argumentos");
			System.out.println("primer algumento es la imagen");
			System.out.println("segundo argumento es la dimension");
			System.exit(1);
		}
		
		
		im1 = args[0];
		dim = Integer.parseInt(args[1]);
		
	
		image1 = UtilImageIO.loadImage(im1);
		original = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);
		
		Tuple2<Integer, GrayU8> tuple = new Tuple2<Integer, GrayU8>();
		tuple = auxiliaryFunctions22.countingTeeth(original, dim, values);
		System.out.println("Number of teeths: "+tuple.data0);
		
		UtilImageIO.saveImage(tuple.data1, "tema3_im/countingTeeth/wheelResult.pgm");
        System.out.println("Archivo: wheelResult.pgm");
		
		

	}

}
