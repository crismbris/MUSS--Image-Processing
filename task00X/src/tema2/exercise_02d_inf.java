package tema2;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import javax.swing.SingleSelectionModel;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;

public class exercise_02d_inf {
	
	
	public static String file1;
	public static String file2;
	public static BufferedImage image1;
	public static BufferedImage image2;
	
	public static GrayU8 imgray8;
	public static GrayU8 im2gray8;
	
	
	
	public static int esMenor(int a, int b) {
		if(a >b) return b;
		else return a;
	}

	public static void main(String[] args) {
		
		if(args.length!=2) {
			System.out.println("ERROR: format input exercice_02d_inf <file1> <file2>");
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
		
		
		GrayU8 imFinal = imgray8.clone();

		
		
		//EL infimo es la intereccion de dos imágenes
		//la imagen resultante tendrá como valor en el pixel (i,j) 
		int valor;
		
		for (int i=0;i<imgray8.getHeight();i++) {
			for (int j=0; j<imgray8.getWidth();j++) {
				//System.out.println("pixel 1: "+imgray8.get(i, j));
				//System.out.println("pixel 2: "+im2gray8.get(i, j));
				valor=esMenor(imgray8.get(i, j),im2gray8.get(i, j));
				//System.out.println("infimo: "+ valor);
				imFinal.set(i, j, valor);
				//Esta suma a veces devuelve un numero mayor de 255, pero no pasa nada porque lo toma como
				//tal si lo pasa. Si vale 400 lo representa como 255.
			}
		}
		UtilImageIO.saveImage(imFinal, "tema2_im/02d/infimo_out.pgm");
		System.out.println("fin");
		

	}

}
