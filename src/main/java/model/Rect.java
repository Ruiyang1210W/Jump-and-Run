package model;

public class Rect {
    public double x, y, w, h;
    public Rect(double x, double y, double w, double h){ this.x=x; this.y=y; this.w=w; this.h=h; }
    public boolean intersects(Rect o){
        return x < o.x + o.w && x + w > o.x && y < o.y + o.h && y + h > o.y;
    }
}
