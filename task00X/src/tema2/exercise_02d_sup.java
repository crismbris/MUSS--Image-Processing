package tema2;
import java.awt.image.BufferedImage;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.Scanner;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class exercise_02d_sup {

	public static String file1;
	public static String file2;
	public static BufferedImage image1;
	public static BufferedImage image2;

	public static GrayU8 imgray8;
	public static GrayU8 im2gray8;




	public static int esMayor(int a, int b) {
		if(a >b) return a;
		else return b;
	}

	public static void main(String[] args) {

		if(args.length!=2) {
			System.out.println("ERROR: format input exercice_02d_sup <file1> <file2>");
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

		//En la imagen resultante tendrá como valor en el pixel (i,j)
		//el valor maximo de del pixel (i,j) en ambas imagenes;
		//pixelResult(i,j)=max(pixel1.value(),pixel2.value())

		for (int i=0;i<imgray8.getHeight();i++) {
			for (int j=0; j< imgray8.getWidth();j++) {
				//System.out.println("pixel 1: "+imgray8.get(i, j));
				//System.out.println("pixel 2: "+im2gray8.get(i, j));
				int valor = esMayor(imgray8.get(i, j),im2gray8.get(i, j));
				//System.out.println("valor: "+ valor);
				imgray8.set(i, j, valor);

			}
		}

		UtilImageIO.saveImage(imgray8, "tema2_im/02d/supremoOut.pgm");
		System.out.println("fin");


	}

}
