import java.io.*;

public class Ponto2D implements Serializable {

    private double x, y;

    public Ponto2D (double cx, double cy) { x = cx; y = cy; }

    public Ponto2D () { this(0.0, 0.0); }

    public Ponto2D (Ponto2D p) { x = p.getX(); y = p.getY(); }

    //**************************************************************************************************************************

    public double getX() { return x; }

    public double getY() { return y; }

    //**************************************************************************************************************************

    public boolean equals (Object o) {
       if (this == o) return true;
       
       if ((o == null) || (this.getClass() != o.getClass())) return false;
       
       Ponto2D aux = (Ponto2D) o;
       return (this.x == aux.getX() && this.y == aux.getY());
   }

    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append("(" + x + ";" + y + ")");
        return sb.toString();
    }

    public Ponto2D clone () {
        return new Ponto2D (this);
    }
}
