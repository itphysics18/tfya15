import java.awt.Graphics2D;
import java.awt.Color;


public class Particle extends GameObject {

    private boolean firstIterate = true;
    private Color color;
    private double bounce = -0.9;

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
        x += vx;
        y = leapFrog(vy);

        if ((x<r) && (vx < 0)) vx *= bounce;
        if ((y<r) && (vy < 0)) vy *= bounce;
        if (x>(800-r) && (vx > 0)) vx *= bounce;
        if (y>(600-r) && (vy > 0)) vy *= bounce;

        if ((x>plat.getX()-r) && (x<plat.getX()+plat.getW()+r) && (y > plat.getY()-r) && (y < plat.getY()+plat.getH()+r)) {
            if(Math.abs(x-plat.getX()) > Math.abs(y-plat.getY())) {
                vy *= bounce;

            }
            else{

                vx *= bounce;
               // System.out.println("HIT");
            }
        }
        double b1R = b1.getR();

        if (x>((b1.getX()-b1R) - r) && x<((b1.getX()+b1R) + r)){
            if (y<((b1.getY()+b1R) + r) && y>((b1.getY()-b1R) - r)){
             //   vx *= -1;

                if(Math.abs(x-b1.getX()+r) > Math.abs(y-b1.getY())){
                 //   System.out.println("TWO!!");
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

        if (x>((b2.getX()-b2R)-r) && x<((b2.getX()+b2R)+r)) {
            if (y <((b2.getY()+b2R)+r) && y>((b2.getY()-b2R)-r)) {
             //   vx *= -1;
             //   vy *= -1;
                 if(Math.abs(x-b2.getX()+r) > Math.abs(y-b2.getY())){
                 //   System.out.println("ONE!!");
                    collision(b2);
                }
                 else{
                     vy *= bounce;
                 }

            }
        }



    }

    //double lastCollisionX = 0;
    //double lastCollisionY = 0;
    int lastCollision = 0;

    public void collision (Box b) {
        //  System.out.println("IM IN");
     /*   vx = (3);
        b.setVX((-20)); */
        //  System.out.println(m);
        //  vx = (((m - b.m) / (m + b.m)) * vx) + (((2 * b.m) / (m + b.m)) * b.vx);
        // vx = (((m - b.m) / (m + b.m)) * vx);
        //  b.set_vx((((2*m) / (m+b.m)) * vx) + (((b.m-m) / (m+b.m)) * b.vx));
        //  b.set_vx(((2*m) / (m+b.m)) * vx);




        double deltaX = Math.abs(x - b.getX());
        double deltaY = Math.abs(y - b.getY());
        double distance = deltaX * deltaX + deltaY * deltaY;

        if (lastCollision != b.hashCode()) {
            ignoreBounce = 0;
        }

        if (Math.abs(x - b.getX()) < (r + b.getR()) && ignoreBounce == 0) {
        //    System.out.println("Particle: vx:" + vx + " m: " + m + " bM: " + b.getM() + " bVX: " + b.getVX());
            double oldVx = vx;
            vx = ((vx * (m - b.getM()) + (2 * b.getM() * b.getVX())) / (m + b.getM()));
         //   System.out.println(vx);
         //   System.out.println("Box: vx:" + b.getVX() + " bM: " + b.getM() + " m: " + m + " vx: " + vx);
            double bVX = (b.getVX() * (b.getM() - m) + (2 * m * oldVx)) / (b.getM() + m);
         //   System.out.println(bVX);
            b.setVX(bVX * 10);
         //   System.out.println("New bVX: " + b.getVX());
            //b.setVX((b.getVX() * (b.getM() - m) + (2 * m * vx)) / (b.getM() + m));
            ignoreBounce = 20;
            lastCollision = b.hashCode();
        }
    /*    if (distance < (r + b.getR()) * (r + b.getR()) && ignoreBounce == 0) {
            if (x < (lastCollisionX + r) && x > (lastCollisionX - r)
                    && y < (lastCollisionY + r) && y > (lastCollisionY - r)) {
                this.setVX((vx * (r - b.getR()) + (2 * b.getR() * b.getVX())) / (r + b.getR()));
                this.setVY((vy * (r - b.getR()) + (2 * b.getR() * b.getVY())) / (r + b.getR()));
                b.setVX((b.getVX() * (b.getR() - r) + (2 * r * vx)) / (b.getR() + r));
                b.setVY((b.getVY() * (b.getR() - r) + (2 * r * vy)) / (b.getR() + r));
                lastCollisionX = x;
                lastCollisionY = y;
            }
        }*/
    }

    public void renderParticle(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)Math.round(x-r), (int)Math.round(y-r), (int)Math.round(2*r), (int)Math.round(2*r));

        // Lagt till en plattform som lådan kan stå på!


    }
}