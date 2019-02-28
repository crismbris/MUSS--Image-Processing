package tema3;

import java.awt.image.BufferedImage;
import org.ddogleg.struct.Tuple2;
import java.util.LinkedList;
import java.util.Queue;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class CoffeGrains {
	
	public static String im1;
	public static int dim;
	public static BufferedImage image1;
	public static GrayU8 original;
	public static Queue<Integer> values= new LinkedList<Integer>();
	
	
	public static void main(String[] args) {
		
		if(args.length!=1) {
			System.out.println("error en el numero de parametros");
			System.exit(1);
		}
		
		im1 = args[0];
		
		image1 = UtilImageIO.loadImage(im1);
		original = ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);
		Tuple2<Integer, GrayU8> tuple = new Tuple2<Integer, GrayU8>();
		tuple = auxiliaryFunctions22.coffeGrains(original, values);
		//original = auxiliaryFunctions.countingTeeth(original, dim, values);
		
		System.out.println("Number of coffee grains: " + tuple.data0);
	
		UtilImageIO.saveImage(tuple.data1, "tema3_im/coffeGrains/coffeGrainsResult.pgm");
        System.out.println("Archivo: coffeGrainsResult.pgm");

	}

}
