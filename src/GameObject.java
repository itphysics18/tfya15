abstract class GameObject {
    protected double x;
    protected double y;
    protected double vx;
    protected double vy;
    protected double r;
    protected double m;
    protected double w;
    protected double h;

    protected final double gravity = -9.8;

    public double getX() { return x; }
    public double getY() { return y; }
    public double getVX() { return vx; }
    public double getVY() { return vy; }
    public double getR() { return r; }
    public double getM() { return m; }

    public void setVX(double vx) { this.vx = vx; }
    public void setVY(double vy) { this.vy = vy; }
}
