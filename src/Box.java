import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class Box extends GameObject {
    private double my = 0;

    private Color color;

    public BufferedImage player;

    public Box(double m, double r, double x, double y){
        this.m = m;
        this.r = r;
        this.x = x;
        this.y = y;
        this.my = 0.5;
        this.vx = 0;
        this.vy = 0;

        try {
            player = ImageIO.read(new File(FileSystems.getDefault().getPath("src", "Box.png").toUri()));
        } catch (IOException e) {
            System.out.println("Image not found");
            player = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
        }
    }

    boolean firstIterate;

    public void leapFrogX(){
        double gamma = 1;
        double dt = 0.1;
      //  double r_i = x;
     //   double v_f = 0;
        int k = 1;
        double vi = vx;
        if(firstIterate){
            gamma = 0.5;
        }
        if(vx < 0){
            k=-1;
        }
        if(vx != 0) {
            vx = vx + (k * my * (gravity) * dt * gamma);
        }
        if((vi < 0 && vx > 0) || (vi > 0 && vx < 0)){
            vx=0;
        }
             x = x + vx*dt;

       // vx = v_f;
        firstIterate = false;

    //    return r_f;
    }

    public double leapFrogY(double v_i){
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
        y+= v_f;
        return v_f;

    }


    public void update(Platform plat, Box ob){
     // x += vx;
        boolean gravity = false;
        if ((x<r) && (vx < 0)) vx = 0;
    //    if ((y<r) && (vy < 0)) vy *= bounce;
        if (x>(800-r) && (vx > 0)) vx = 0;
    //    if (y>(600-r) && (vy > 0)) vy *= bounce;



        leapFrogX();

        if ((x < plat.getX()-r || x > plat.getX() + plat.getW() + r) && y<560) { // x är lådans possition i x led(höger/vänster) -+ rade. och
            gravity = true;
        }

        if((y+r > ob.getY()+r) && (x+r > ob.getX()-r)){
           System.out.println("NEE");
            gravity = false;
            vx = 0;
            vy = 0;
        }
        if(gravity) {

          //  System.out.println("hej");
            leapFrogY(vy);
        } else if (y>(600-r)) {
            y = 600-r;
        }

    }
    public void renderBox(Graphics2D g) {
        g.drawImage(player, (int)Math.round(x - r), (int)Math.round(y - r),
                (int)Math.round(r*2), (int)Math.round(r*2), null);    // Draw player
    }

    int winDelay = 0;
    public boolean win() {
        if(y>=600-r && winDelay > 200) {
            return true;
        } else {
            winDelay++;
            return false;
        }
    }
}