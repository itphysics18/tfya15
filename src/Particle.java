import java.awt.Graphics2D;
import java.awt.Color;


public class Particle {

    private boolean firstIterate = true;
    private double vx;
    private double vy;
    private double r;
    private Color color;

    private double x;
    public double getX() {
        return x;
    }

    private double y;
    public double getY() {
        return y;
    }

    public Particle(double x, double y, double r, Color color) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
        this.vx = -1.0;
        this.vy = -0.1;
    }

    public Particle(double x, double y, double r, Color color, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
        this.vx = vx;
        this.vy = vy;
    }

    public double leapFrog(double v_i){
        double gamma = 0.5; //tidigare 1
        double dt = 0.1;
        double r_i = y;
        if(firstIterate){
            gamma = 1; //tidigare 0.5
        }
        double v_f = v_i + (9.8*2*dt)*gamma; // OBS – förstärkt med faktor 2

        double r_f = r_i + v_f*dt;
        vy = v_f;
        firstIterate = false;

        return r_f;
    }

    public void update() {
        x += vx;


        if (x<r ) vx *= -1;
        if (y<r) vy *= -1;
        if (x>(800-2*r)) vx *= -1;
        if (y>(600-2*r)) vy *= -1;

        y = leapFrog(vy);

    }

    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)Math.round(x-r), (int)Math.round(y-r), (int)Math.round(2*r), (int)Math.round(2*r));
    }
}