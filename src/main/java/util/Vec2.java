package util;

public class Vec2 {
    public double x, y;
    public Vec2() { this(0,0); }
    public Vec2(double x, double y) { this.x = x; this.y = y; }
    public Vec2 set(double nx, double ny){ this.x = nx; this.y = ny; return this; }
    public Vec2 add(Vec2 o){ this.x += o.x; this.y += o.y; return this; }
    public Vec2 scale(double s){ this.x *= s; this.y *= s; return this; }
    public Vec2 copy(){ return new Vec2(x, y); }
}
