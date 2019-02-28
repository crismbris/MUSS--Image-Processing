package task01X;

import java.lang.NullPointerException;

public class Point<X, Y> implements Comparable<Point>  { 
    public final X x; 
    public final Y y; 

    public Point(X x, Y y) { 
        this.x = x; 
        this.y = y; 
    }

    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            if (this.x.equals(((Point) obj).x) &&
                this.y.equals(((Point) obj).y)) {
                return true;
            }
        }
        return false;
    }



	@Override
	public int compareTo(Point p) {
		if (p == null) {
            throw new NullPointerException();
        } else if (this.equals(p)) {
            return 0;
        } else {
            try {
                return 
                    ((Integer) p.x  - (Integer) this.x) / 
                    ((Integer) p.y - (Integer) this.y);
                
            } catch (ArithmeticException e) {
                return ((Integer) p.x  - (Integer) this.x);
            }
        }
	}
}