package task01X;

import java.util.ArrayList;
import java.util.PriorityQueue;

import boofcv.struct.image.GrayU8;

public class auxiliaryFunctions {
	
	public static ArrayList<Point<Integer,Integer>> allNeighborPoints(GrayU8 image, int i, int j, int size) {
        ArrayList<Point<Integer,Integer>> list = new ArrayList<Point<Integer,Integer>>(8);

        for (int neighbor = 1; neighbor <= size; neighbor++) {
            for (int n = i - neighbor; n <= i + neighbor; n++) {
                if (image.isInBounds(n,j + neighbor))
                    { list.add(new Point<Integer,Integer>(n,j + neighbor)); }
            }

            for (int n = i - neighbor; n <= i + neighbor; n++) {
                if (image.isInBounds(n,j - neighbor))
                    { list.add(new Point<Integer,Integer>(n,j - neighbor)); }
            }

            for (int n = j - neighbor + 1; n < j + neighbor; n++) {
                if (image.isInBounds(i - neighbor,n))
                    { list.add(new Point<Integer,Integer>(i - neighbor,n)); }
            }

            for (int n = j - neighbor + 1; n < j + neighbor; n++) {
                if (image.isInBounds(i + neighbor,n))
                    { list.add(new Point<Integer,Integer>(i + neighbor,n)); }
            }
        }
        return list;
    }
	
	public static GrayU8 flatzone(GrayU8 original, int x, int y, int label) {
        int width = original.width;
        int height = original.height;

        GrayU8 aux = new GrayU8(width, height);

        PriorityQueue<Point<Integer,Integer>> queue = new PriorityQueue<Point<Integer,Integer>>();
        PriorityQueue<Point<Integer,Integer>> visited = new PriorityQueue<Point<Integer,Integer>>();

        Point<Integer,Integer> current = new Point<Integer,Integer>(x,y);

        queue.add(current);

        while (!queue.isEmpty()) {
            current = (Point<Integer,Integer>) queue.poll();
            
            visited.add(new Point<Integer,Integer>(current.x,current.y));
            aux.set(current.x, current.y, label);

            for (Point<Integer,Integer> point : allNeighborPoints(original, current.x, current.y, 1)) {
                if (original.get(point.x,point.y) == original.get(current.x,current.y)
                    && !visited.contains(point))
                    { queue.add(point); }
            }
        }

        return aux;
    }
	
	public static int flatzoneNumber(GrayU8 original) {
        int width = original.width;
        int height = original.height;

        int visited[][] = new int[width][height];

        int fz = 0;

        PriorityQueue<Point<Integer,Integer>> queue = new PriorityQueue<Point<Integer,Integer>>();
        Point<Integer,Integer> current;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (visited[x][y] == 1) { continue; }

                fz++;

                current = new Point<Integer,Integer>(x,y);                
                queue.add(current);

                while (!queue.isEmpty()) {
                    current = (Point<Integer,Integer>) queue.poll();

                    visited[current.x][current.y] = 1;

                    for (Point<Integer,Integer> point : allNeighborPoints(original, current.x, current.y, 1)) {
                        if (original.get(point.x,point.y) == original.get(current.x,current.y)
                            && visited[point.x][point.y] == 0)
                            { queue.add(point); }
                    }
                }
            }
        }

        return fz;
    }

	 public static GrayU8 flatzoneRegion(GrayU8 original, Region region) {
	        int width = original.width;
	        int height = original.height;

	        GrayU8 aux = new GrayU8(width, height);

	        PriorityQueue<Point<Integer,Integer>> queue = new PriorityQueue<Point<Integer,Integer>>();
	        PriorityQueue<Point<Integer,Integer>> in_flatzone = new PriorityQueue<Point<Integer,Integer>>();
	        PriorityQueue<Point<Integer,Integer>> out_flatzone = new PriorityQueue<Point<Integer,Integer>>();
	        
	        int visited[][] = new int[width][height];

	        Point<Integer,Integer> current;

	        for (int x = 0; x < width; x++) {
	            for (int y = 0; y < height; y++) {
	                if (visited[x][y] == 1) { continue; }

	                queue.clear();
	                in_flatzone.clear();
	                out_flatzone.clear();

	                current = new Point<Integer,Integer>(x,y);
	                queue.add(current);

	                while (!queue.isEmpty()) {
	                    current = (Point<Integer,Integer>) queue.poll();
	                    in_flatzone.add(current);

	                    visited[current.x][current.y] = 1;

	                    for (Point<Integer,Integer> point : allNeighborPoints(original, current.x, current.y, 1)) {
	                        if (original.get(point.x,point.y) == original.get(current.x,current.y)
	                            && visited[point.x][point.y] == 0)
	                            { queue.add(point); }
	                        else if (region.compare(original.get(point.x,point.y), original.get(current.x,current.y)))
	                            { out_flatzone.add(point); }
	                    }

	                    if (!out_flatzone.isEmpty()) { break; }
	                }

	                if (out_flatzone.isEmpty()) {
	                    for (Point<Integer,Integer> point : in_flatzone) {
	                        aux.set(point.x, point.y, 255);
	                    }
	                } else {
	                    for (Point<Integer,Integer> point : in_flatzone) {
	                        visited[point.x][point.y] = 0;
	                    }
	                  
	                }
	            }
	        }

	        return aux;
	    }

}
