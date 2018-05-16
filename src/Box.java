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


    public void update(Platform plat){
        if ((x<r) && (vx < 0)) vx *= -0.2;
        if (x>(800-r) && (vx > 0)) vx *= -0.2;

        leapFrogX();

        if ((x < plat.getxX()-r || x > plat.getxX() + plat.getW() + r) && y<(600-r)) { // x är lådans possition i x led(höger/vänster) -+ rade. och
            leapFrogY(vy);
        } else if (y>(600-r)) {
            y = 600-r;
        }

    }
    public void renderBox(Graphics2D g) {
        g.drawImage(player, (int)Math.round(x - r), (int)Math.round(y - r),
                (int)Math.round(r*2), (int)Math.round(r*2), null);    // Draw player
    }

    private int winDelay = 0;
    public boolean win() {
        if(y>=600-r && winDelay > 50) {
            return true;
        } else if (y>=600-r) {
            winDelay++;
        }
        return false;
    }
}