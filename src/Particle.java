import java.awt.Graphics2D;
import java.awt.Color;

public class Particle extends GameObject {

    private boolean firstIterate = true;
    private Color color;
    private double bounce = -0.9;
    private int yBoost = 0;
    private int xBoost = 0;

    public Particle(double x, double y, double r, double m, Color color, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.m = m;
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
        double v_f = v_i + (-gravity*dt)*gamma;

        if ((r_i + v_f*dt)>(600-r) && (v_f > 0)){
            v_f *= bounce; // För att försöka förhindra kvicksanden //Mange
        }

        double r_f = r_i + v_f*dt;
        vy = v_f;
        firstIterate = false;

        return r_f;
    }

    double ignoreBounce = 0;


    public void update(Box b1, Box b2, Platform plat) {
        if (yBoost != 0) {
            vy += yBoost*20;
            yBoost = 0;
        }
        if (xBoost != 0) {
            vx += xBoost*2;
            xBoost = 0;
        }
        x += vx;
        y = leapFrog(vy);

        if ((x<r) && (vx < 0)) vx *= bounce;
        if ((y<r) && (vy < 0)) vy *= bounce;
        if (x>(800-r) && (vx > 0)) vx *= bounce;
        if (y>(600-r) && (vy > 0)) vy *= bounce;

        if ((x>(plat.getxX()-r)) && (x<(plat.getxX()+plat.getW()+r)) && ignoreBounce == 0) {
            if ((y > (plat.getyY()-r) && y <(plat.getyY()) && vy > 0) || (y < (plat.getyY()+plat.getH()+r) && y > (plat.getyY()+plat.getH()) && vy < 0)) {
                if (Math.abs(x - plat.getxX()) > Math.abs(y - plat.getyY())) {
                    vy *= bounce;
                    ignoreBounce = 10;
                }
            }
        }
        if ((y > (plat.getyY()-r)) && (y<(plat.getyY()+plat.getH()+r)) && ignoreBounce == 0) {
            if ((x>(plat.getxX()-r) && x <(plat.getxX()+r)) || (x<(plat.getxX()+plat.getW()+r) && x > (plat.getxX()+plat.getW()-2*r) && vx < 0)) {
                if (Math.abs(y - plat.getyY()) > Math.abs(x - plat.getxX())) {
                    vx *= bounce;
                    ignoreBounce = 10;
                }
            }
        }
        double b1R = b1.getR();

        if (x>((b1.getxX()-b1R) - r) && x<((b1.getxX()+b1R) + r)){
            if (y<((b1.getyY()+b1R) + r) && y>((b1.getyY()-b1R) - r)){

                if(Math.abs(x-b1.getxX()+r) > Math.abs(y-b1.getyY())){
                    collision(b1);
                }
                else if (ignoreBounce == 0){
                    vy *= bounce;
                    ignoreBounce = 10;
                }
            }
        }

        if (ignoreBounce > 0) {
            ignoreBounce--;
        }

        double b2R = b2.getR();

        if (x>((b2.getxX()-b2R)-r) && x<((b2.getxX()+b2R)+r)) {
            if (y <((b2.getyY()+b2R)+r) && y>((b2.getyY()-b2R)-r)) {
                 if(Math.abs(x-b2.getxX()+r) > Math.abs(y-b2.getyY())){
                    collision(b2);
                }
                 else{
                     vy *= bounce;
                 }

            }
        }
    }

    int lastCollision = 0;

    public void collision (Box b) {
        double deltaX = Math.abs(x - b.getxX());
        double deltaY = Math.abs(y - b.getyY());
        double distance = deltaX * deltaX + deltaY * deltaY;

        if (lastCollision != b.hashCode()) {
            ignoreBounce = 0;
        }

        if (Math.abs(x - b.getxX()) < (r + b.getR()) && ignoreBounce == 0) {
            double oldVx = vx;
            vx = ((vx * (m - b.getM()) + (2 * b.getM() * b.getVX())) / (m + b.getM()));
            double bVX = (b.getVX() * (b.getM() - m) + (2 * m * oldVx)) / (b.getM() + m);
            b.setVX(bVX * 10);
            ignoreBounce = 20;
            lastCollision = b.hashCode();
        }
    }

    public void renderParticle(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)Math.round(x-r), (int)Math.round(y-r), (int)Math.round(2*r), (int)Math.round(2*r));
    }

    public void boost(int boost, char type) {
        switch (type) {
            case 'x':
                xBoost = boost;
                break;
            case 'y':
                yBoost = boost;
                break;
            case 'z':
                xBoost = boost; yBoost = boost;
                break;
            case '0':
                xBoost = 0; yBoost = 0
                break;

        }
    }
}