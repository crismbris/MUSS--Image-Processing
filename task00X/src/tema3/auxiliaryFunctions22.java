package tema3;

import java.util.LinkedList;
import java.util.Random;
import java.util.Queue;

import boofcv.struct.ConnectRule;

import org.ddogleg.struct.Tuple2;
import boofcv.struct.image.GrayS32;
import boofcv.struct.image.GrayU16;
import boofcv.struct.image.GrayU8;

import java.awt.Color;
import java.awt.List;
import java.lang.Object;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.misc.PixelMath;
import boofcv.core.image.ConvertImage;
import java.util.*;
public class auxiliaryFunctions22 {



	private static GrayU8 binary;




	public static GrayU8 thresh(GrayU8 imgray8) {
		for (int i = 0; i < imgray8.getWidth(); i++) {
			for (int j = 0; j < imgray8.getHeight(); j++) {
				if (imgray8.get(i, j) < 100) {
					imgray8.set(i, j, 0);
				} else {
					imgray8.set(i, j, 255);
				}
			}
		}

		return imgray8;
	}

	/*******************************************************************************************************/

	public static void compare(GrayU8 imgray8, GrayU8 im2gray8) {

		boolean continuo = true;
		//System.out.println(imgray8.get(x, y));
		// Comprobamos que ambas fotos tengan los mismos tamaños
		if (imgray8.getHeight() == im2gray8.getHeight() && imgray8.getWidth() == im2gray8.getWidth()) {
			//System.out.println("somos iguales en tamaño");
			boolean salir = false;
			for (int i = 0; i < imgray8.getWidth() && !salir; i++) {
				for (int j = 0; j < imgray8.getHeight() && !salir; j++) {
					//System.out.println("pos: ("+i+", "+j+")");
					if (imgray8.get(i, j) != im2gray8.get(i, j)) {
						System.out.println("No soy igual en i="+i+" j ="+j);
						System.out.println(imgray8.get(i,j));
						System.out.println(im2gray8.get(i, j));
						salir = true;
					}
				}
			}

			if (salir)
				System.out.println("!=");
			else
				System.out.println(" = ");

		} else {
			System.out.println("No somos iguales en tamaño");
		}
	}


	/*******************************************************************************************************/

	public static  Queue<Integer> getVecinos(GrayU8 imagen, int i, int j, Queue<Integer> values) {
		for(int a = i-1;a<=i+1;a++) {
			for (int b=j-1;b<=j+1;b++) {
				if(a>=0 && a<imagen.getWidth() && b>=0 && b<imagen.getHeight())
					values.add(imagen.get(a, b));
			}
		}
		return values;
	}


	public static int getMinimun(Queue<Integer> values) {
		int min=0;
		if(!values.isEmpty()) min = values.poll();

		while(!values.isEmpty()) {
			int n = values.poll();
			if(min > n)  min = n;	
		}
		return min;
	}

	public static GrayU8 erosion (GrayU8 imagen,int dim, Queue<Integer> values) {
		GrayU8 aux = new GrayU8();
		aux = imagen.clone();
		for (int t=0; t<dim;t++) {
			for (int i=0;i<imagen.getWidth();i++) {
				for (int j=0;j<imagen.getHeight();j++) {
					getVecinos(imagen, i, j, values);
					int min = getMinimun(values);
					values.clear();
					aux.set(i, j, min);
				}
			}
			imagen = aux.clone();
		}
		return aux;

	}



	/*******************************************************************************************************/

	public static int getMaximun(Queue<Integer> values) {
		//System.out.println(values.toString());

		int max=0;
		if(!values.isEmpty()) max = values.poll();

		while(!values.isEmpty()) {
			int n = values.poll();
			if(max < n)  max = n;	
		}
		return max;
	}

	public static GrayU8 dilation (GrayU8 imagen,int dim, Queue<Integer> values) {
		GrayU8 aux = new GrayU8();
		aux = imagen.clone();
		for (int t=0; t<dim;t++) {
			for (int i=0;i<imagen.getWidth();i++) {
				for (int j=0;j<imagen.getHeight();j++) {
					getVecinos(imagen, i, j, values);
					int max = getMaximun(values);
					values.clear();
					aux.set(i, j, max);
				}
			}
			imagen = aux.clone();
		}
		return aux;
	}

	/*******************************************************************************************************/


	public static GrayU8 opening(GrayU8 imagen, int dim, Queue<Integer> values) {

		imagen = dilation((erosion(imagen, dim, values)),dim, values);

		return imagen;
	}

	/*******************************************************************************************************/
	public static GrayU8 closing(GrayU8 imagen, int dim, Queue<Integer> values) {

		imagen = erosion((dilation(imagen, dim, values)),dim, values);

		return imagen;
	}

	/*******************************************************************************************************/
	public static GrayU8 opening_closing(GrayU8 imagen, int dim, Queue<Integer> values) {
		imagen = auxiliaryFunctions22.opening(auxiliaryFunctions22.closing(imagen, dim, values), dim, values);
		return imagen;
	}
	/*******************************************************************************************************/
	public static GrayU8 closing_opening(GrayU8 imagen, int dim, Queue<Integer> values) {
		imagen = auxiliaryFunctions22.closing(auxiliaryFunctions22.opening(imagen, dim, values), dim, values);
		return imagen;
	}


	/*******************************************************************************************************/


	public static GrayU8 subtraction(GrayU8 original , GrayU8 second) {

		GrayU8 aux= original.clone();		
		for(int i=0; i< original.getWidth();i++) {
			for (int j=0; j<original.getHeight();j++) {

				int value = original.get(i, j) - second.get(i, j);
				//System.out.println("my value: "+value);
				if(value>0)
					aux.set(i, j, value);
				else
					aux.set(i,j,0);
			}
		}

		return aux;
	}


	/**
	 * @return *****************************************************************************************************/


	
	
	public static Tuple2<Integer, GrayU8> countingTeeth(GrayU8 im, int dim, Queue<Integer> values) {
		
		 	int width = im.width;
	        int height = im.height;

/*
		 	GrayU8 thresholded = new GrayU8(width, height);
	        GrayU8 aux = new GrayU8(width, height);
	        GrayU16 subtracted = new GrayU16(width, height);*/
	        
	        GrayU8 th = im.clone();
	        GrayU8 aux = im.clone();
	        
	        ThresholdImageOps.threshold(im,th,100,false);

	        aux = th;

	       aux = closing(aux,5, values);
	       aux = BinaryImageOps.erode4(aux,1,null);
	       aux = BinaryImageOps.dilate4(aux,2,null);

	       aux = subtraction(aux, th);

	       aux = BinaryImageOps.erode4(aux,1,null);

	       //todavía quedan puntos dentro aunque no se vea bien
	       aux = opening(aux,1, values);
	     

	        java.util.List<Contour> contours = BinaryImageOps.contour(aux, ConnectRule.EIGHT, null); 
	        Tuple2<Integer, GrayU8> tuple = new Tuple2<Integer, GrayU8>();
	        tuple.data0=contours.size();
	        tuple.data1=aux;
	        
		
		return tuple;
	}
	
	
	 public static Tuple2 coffeGrains(GrayU8 original, Queue<Integer> values) {
	        
	        GrayU8 thr = original.clone();
	        GrayU8 aux = original.clone();
	        
	        ThresholdImageOps.threshold(original,thr,131,false);

	        aux = closing(opening(thr,1, values),1, values);
	        aux = BinaryImageOps.dilate4(aux,5,null);
	        aux = opening(aux,1, values);
	        aux = BinaryImageOps.dilate4(aux,6,null);
	        aux = BinaryImageOps.invert(aux,null);

	        java.util.List<Contour> contours = BinaryImageOps.contour(aux, ConnectRule.EIGHT, null); 
	        Tuple2<Integer, GrayU8> i = new Tuple2<Integer, GrayU8>();
	        i.data0= contours.size();
	        i.data1 = aux;

	        return i;
	    }


}
