import java.awt.*;


public class Particle {

    private double vx;
    private double vy;
    private double r;
    private Color color;

    private double x;
    private double y;

    public Particle(double x, double y, double r, Color color) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
        this.vx = -1.0;
        this.vy = -0.5;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void update() {
        x += vx;
        y += vy;

        if (x < r) vx *= -1;
        if (y < r) vy *= -1;

    }

    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int) Math.round(x - r), (int) Math.round(y - r), (int) Math.round(2 * r), (int) Math.round(2 * r));
    }
}
