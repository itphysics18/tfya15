import java.awt.Graphics2D;
import java.awt.Color;


public class Particle extends GameObject {

    private boolean firstIterate = true;
    private Color color;
    private int ignoreOneBounce = 0;

    public Particle(double x, double y, double r, double m, Color color) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.m = m;
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
        double gamma = 1;
        double dt = 0.1;
        double r_i = y;
        if(firstIterate){
            gamma = 0.5;
        }
        double v_f = v_i + (9.8*dt)*gamma;

        double r_f = r_i + v_f*dt;
        vy = v_f;
        firstIterate = false;

        return r_f;
    }

    public void windowBounce() {
        //if (x<r ) vx *= -1;
        if (x<-50) x = 850;
        if (y<(-600+2*r)) vy *= -0.95;
        //if (x>(1600-2*r)) vx *= -0.9;
        if (x>850) x = -50;
        if (y>(600-2*r)) vy *= -0.9; // Studsfriktion
    }

    public void update() {
        x += vx;
        windowBounce();
        y = leapFrog(vy);

    }

    public void update(Box b1, Box b2) {
        x += vx;
        windowBounce();

    /*  if (x<r ) vx *= -1;
        if (y<r) vy *= -1;
        if (x>(800-r)) vx *= -1;                    //ändrade 2r till bara r
        if (y>(600-2*r)) vy *= -1; */

        if (x>((b1.x-b1.r) - r) && x<((b1.x+b1.r) + r)){
            if (y<((b1.y+b1.r) + r) && y>((b1.y-b1.r) - r)){
                vx *= -0.95;
                vy *= -0.9;
                vx *= -1;
                vy *= -1;
/*                if(Math.abs(x-b1.x) > Math.abs(y-b1.y)){
                    update(Particle);
                }*/
            }
        }
        if (x>((b2.x-b2.r)-r) && x<((b2.x+b2.r)+r)) {
            if (y <((b2.y+b2.r)+r) && y>((b2.y-b2.r)-r)) {
                vx *= -0.95;
                vy *= -0.9;
            }
        }

        y = leapFrog(vy);

    }

    public void update(Box b1, Box b2, Particle p1) {
        x += vx;
        windowBounce();

        if (x>((b1.x-b1.r) - r) && x<((b1.x+b1.r) + r)){
            if (y<((b1.y+b1.r) + r) && y>((b1.y-b1.r) - r)){
                vx *= -0.95;
                vy *= -0.9;
            }
        }
        if (x>((b2.x-b2.r)-r) && x<((b2.x+b2.r)+r)) {
            if (y <((b2.y+b2.r)+r) && y>((b2.y-b2.r)-r)) {
                vx *= -0.95;
                vy *= -0.9;
            }
        }
        if (ignoreOneBounce != 0) {
            ignoreOneBounce -= 1;
        } else {
            if (x > ((p1.x - p1.r) - r) && x < ((p1.x + p1.r) + r)) {
                if (y < ((p1.y + p1.r) + r) && y > ((p1.y - p1.r) - r)) {
                    vx = ((r - p1.r) / (r + p1.r) * vx + 2 * p1.r / (r + p1.r) * p1.vx);
                    vy = ((r - p1.r) / (r + p1.r) * vy + 2 * p1.r / (r + p1.r) * p1.vy);
                    ignoreOneBounce = 10;
                }
            }
        }


        y = leapFrog(vy);

    }

    public void renderParticle(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)Math.round(x-r), (int)Math.round(y-r), (int)Math.round(2*r), (int)Math.round(2*r));
    }
}