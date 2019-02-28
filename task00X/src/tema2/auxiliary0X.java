package tema2;

import boofcv.struct.image.GrayU8;
import java.util.*;
import org.ddogleg.struct.Tuple2;




public class auxiliary0X {



	private static GrayU8 binary;



	public static GrayU8 thresh(GrayU8 imgray8, int value) {
		for (int i = 0; i < imgray8.getWidth(); i++) {
			for (int j = 0; j < imgray8.getHeight(); j++) {
				if (imgray8.get(i, j) < value) {
					imgray8.set(i, j, 0);
				} else {
					imgray8.set(i, j, 255);
				}
			}
		}

		return imgray8;
	}

	/*******************************************************************************************************/

	public static boolean compare(GrayU8 imgray8, GrayU8 im2gray8) {

		boolean continuo = true;
		boolean result;
		//System.out.println(imgray8.get(x, y));
		// Comprobamos que ambas fotos tengan los mismos tamaños
		if (imgray8.getHeight() == im2gray8.getHeight() && imgray8.getWidth() == im2gray8.getWidth()) {
			//System.out.println("somos iguales en tamaño");
			boolean salir = false;
			for (int i = 0; i < imgray8.getWidth() && !salir; i++) {
				for (int j = 0; j < imgray8.getHeight() && !salir; j++) {
					//System.out.println("pos: ("+i+", "+j+")");
					if (imgray8.get(i, j) != im2gray8.get(i, j)) {
						System.out.println("The images are not equal in the pixel i="+i+" j ="+j);
						System.out.println(imgray8.get(i,j));
						System.out.println(im2gray8.get(i, j));
						salir = true;
					}
				}
			}
			
			if (salir) {
				System.out.println("!=");
				result= false;
			}
				
			else {
				System.out.println(" = ");
				result= true;
			}
				

		} else {
			System.out.println("The size of the images are not equal");
			result=false;
		}
		return result;
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
		imagen = auxiliary0X.opening(auxiliary0X.closing(imagen, dim, values), dim, values);
		return imagen;
	}
	/*******************************************************************************************************/
	public static GrayU8 closing_opening(GrayU8 imagen, int dim, Queue<Integer> values) {
		imagen = auxiliary0X.closing(auxiliary0X.opening(imagen, dim, values), dim, values);
		return imagen;
	}
}