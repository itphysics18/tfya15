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
        double v_f = v_i + (9.8*2*dt)*gamma; // Förstärkt med faktor 2

        double r_f = r_i + v_f*dt;
        vy = v_f;
        firstIterate = false;

        return r_f;
    }

    public void update() {
        x += vx;


        //if (x<r ) vx *= -1;
        if (x<-50) x = 850;
        if (y<(-600+2*r)) vy *= -1;
        //if (x>(1600-2*r)) vx *= -0.9;
        if (x>850) x = -50;
        if (y>(600-2*r)) vy *= -0.9; // Studsfriktion

        y = leapFrog(vy);

    }

    public void update(Box b1, Box b2) {
        x += vx;


        if (x<r ) vx *= -1;
        if (y<r) vy *= -1;
        if (x>(800-r)) vx *= -1;                    //ändrade 2r till bara r
        if (y>(600-2*r)) vy *= -1;
        if (x>((b1.x-b1.r) - r) && x<((b1.x+b1.r) + r)){
            if (y<((b1.y+b1.r) + r) && y>((b1.y-b1.r) - r)){
                vx *= -1;
                vy *= -1;
            }
        }
        if (x>((b2.x-b2.r)-r) && x<((b2.x+b2.r)+r)) {
            if (y <((b2.y+b2.r)+r) && y>((b2.y-b2.r)-r)) {
                vx *= -1;
                vy *= -1;
            }
        }

        y = leapFrog(vy);

    }

    public void renderParticle(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)Math.round(x-r), (int)Math.round(y-r), (int)Math.round(2*r), (int)Math.round(2*r));
    }
}