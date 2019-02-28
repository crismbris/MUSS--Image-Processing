package tema3;

import boofcv.struct.ConnectRule;
import boofcv.struct.feature.TupleDesc_B;
import boofcv.struct.*;
import boofcv.struct.image.GrayS32;
import boofcv.struct.image.GrayU16;
import boofcv.struct.image.GrayU8;
import org.ddogleg.struct.Tuple2;

import java.awt.Color;
import java.awt.List;
import java.lang.Object;
import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.Contour;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.misc.PixelMath;
import boofcv.core.image.ConvertImage;
import java.util.*;
public class auxiliaryFunctions21 {



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
		imagen = auxiliaryFunctions21.opening(auxiliaryFunctions21.closing(imagen, dim, values), dim, values);
		return imagen;
	}
	/*******************************************************************************************************/
	public static GrayU8 closing_opening(GrayU8 imagen, int dim, Queue<Integer> values) {
		imagen = auxiliaryFunctions21.closing(auxiliaryFunctions21.opening(imagen, dim, values), dim, values);
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


	


    public static GrayU8 grassfire(GrayU8 original, int size) {

        GrayU8 aux = original.clone();
        
        int region = 0;
        int intensidad = 0;
        Tuple2<Integer,Integer> actual;

        Queue <Tuple2<Integer,Integer>> tratados = new LinkedList<Tuple2<Integer,Integer>>();
        Queue <Tuple2<Integer,Integer>> no_tratados = new LinkedList<Tuple2<Integer,Integer>>(); 
        ArrayList <Tuple2<Integer,Integer>> vecinos = new ArrayList<Tuple2<Integer,Integer>>(); 


        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                region += 10;

                tratados.clear();
                no_tratados.clear();

                tratados.add(new Tuple2<Integer,Integer>(x,y));
                intensidad = original.get(x,y);
                
                vecinos = peekNeighbord(original,x,y,size);

                while (!tratados.isEmpty()) {
                    actual = tratados.poll();
                    vecinos.add(actual);

                    tratados.add(actual);
                    for (int i=0; i< vecinos.size();i++) {
                    	if((getIntensidad(vecinos.get(i).data0,vecinos.get(i).data1,original) == intensidad) 
                    		&& no_tratados.contains(vecinos.get(i))){
                    		tratados.add((Tuple2<Integer,Integer>)vecinos.get(i));
                    		tratados.add(vecinos.get(i));
                    	}else {
                    		no_tratados.add(vecinos.get(i));
                    	}
                    }

                }

            }
        }
        return aux;
    }
    

	/*
	 * 
	 * if(!isTreat){
	 * 	createNewRegion();---> set a intensity
	 * 	if(peekNeighbord() has the same intensity color){
	 * 		add to the tratados queue;
	 * 	else
	 * 		add to the no_tratados queue;
	 */
	
	
	/*******************************************************************************************************/

    public static ArrayList<Tuple2<Integer,Integer>> peekNeighbord(GrayU8 image, int i, int j, int size){
        	
            ArrayList<Tuple2<Integer,Integer>> list = new ArrayList<Tuple2<Integer,Integer>>(8);

            for (int neighbor = 1; neighbor <= size; neighbor++) {
                for (int n = i - neighbor; n <= i + neighbor; n++) {
                    if (image.isInBounds(n,j + neighbor))
                        { list.add(new Tuple2<Integer,Integer>(n,j + neighbor)); }
                }

                for (int n = i - neighbor; n <= i + neighbor; n++) {
                    if (image.isInBounds(n,j - neighbor))
                        { list.add(new Tuple2<Integer,Integer>(n,j - neighbor)); }
                }

                for (int n = j - neighbor + 1; n < j + neighbor; n++) {
                    if (image.isInBounds(i - neighbor,n))
                        { list.add(new Tuple2<Integer,Integer>(i - neighbor,n)); }
                }

                for (int n = j - neighbor + 1; n < j + neighbor; n++) {
                    if (image.isInBounds(i + neighbor,n))
                        { list.add(new Tuple2<Integer,Integer>(i + neighbor,n)); }
                }
            }
            return list;
        }
    
    public static int getIntensidad(int x, int y, GrayU8 imagen) {
    	return imagen.get(x, y);
    }

	public static void exercise11(GrayU8 imgray, GrayU8 imresult, int x, int y, int flatZoneIntensity) {
		imresult.set(x,y,flatZoneIntensity);
		Queue <Tuple2> tratados = new LinkedList<Tuple2>();
		Tuple2 aux = new Tuple2(); 
		aux.setData0(x);aux.setData1(y);
		
		
		tratados.add(aux);
		
		
		while(!tratados.isEmpty()) {
			aux = tratados.poll();
			ArrayList<Tuple2<Integer, Integer>> vecinos = peekNeighbord(imgray,x,y,8);
			System.out.println("size vecinos: "+vecinos.size());
			int i=0;
			while(i<vecinos.size()) {
				Tuple2<Integer, Integer> aux1 = vecinos.remove(i);
				int x1= Integer.parseInt(aux1.getData0().toString());
				int y1= Integer.parseInt(aux1.getData0().toString());
				if(imgray.get(x1, y1) == imgray.get(x, y) && imresult.get(x1, y1)!= flatZoneIntensity) {
					imresult.set(x1,y1,flatZoneIntensity);
					tratados.add(aux1);
				}
			}vecinos = null; 
		}
	}
    


}
