import java.awt.Graphics2D;
import java.awt.Color;


public class Particle {

    private boolean firstIterate = true;
    private double vx;
    private double vy;
    private double r;
    private Color color;
    private double m;

    private double x;
    public double getX() {
        return x;
    }

    private double y;
    public double getY() {
        return y;
    }

    public Particle(double x, double y, double r, double m, Color color) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.m = m;
        this.color = color;
        this.vx = -2.0;
        this.vy = -0.1;
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



    public void update(Box b1, Box b2) {
        x += vx;
        y = leapFrog(vy);

        if (x<r ) vx *= -1;
        if (y<r) vy *= -1;
        if (x>(800-r)) vx *= -1;
        if (y>(600-2*r)) vy *= -1;
        if (x>((b1.x-b1.r) - r) && x<((b1.x+b1.r) + r)){
            if (y<((b1.y+b1.r) + r) && y>((b1.y-b1.r) - r)){
             //   vx *= -1;

                if(Math.abs(x-b1.x) > Math.abs(y-b1.y)){
                    System.out.println("TWO!!");
                    collision(b1);
                }
                else{
                    vy *= -1;
                }
            }
        }
        if (x>((b2.x-b2.r)-r) && x<((b2.x+b2.r)+r)) {
            if (y <((b2.y+b2.r)+r) && y>((b2.y-b2.r)-r)) {
             //   vx *= -1;
             //   vy *= -1;
                 if(Math.abs(x-b2.x) > Math.abs(y-b2.y)){
                  //  System.out.println("ONE!!");
                    collision(b2);
                }
                 else{
                     vy *= -1;
                 }

            }
        }



    }

    public void collision (Box b){
      //  System.out.println("IM IN");
       vx = (3);
       b.set_vx((-20));
      //  System.out.println(m);
     //  vx = (((m - b.m) / (m + b.m)) * vx) + (((2 * b.m) / (m + b.m)) * b.vx);
       // vx = (((m - b.m) / (m + b.m)) * vx);
     //  b.set_vx((((2*m) / (m+b.m)) * vx) + (((b.m-m) / (m+b.m)) * b.vx));
      //  b.set_vx(((2*m) / (m+b.m)) * vx);
    }

    public void renderParticle(Graphics2D g) {
        g.setColor(color);
        g.fillOval((int)Math.round(x-r), (int)Math.round(y-r), (int)Math.round(2*r), (int)Math.round(2*r));
    }
}