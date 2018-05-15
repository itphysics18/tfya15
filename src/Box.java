import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class Box {
    double m = 0;
    double r = 0;
    double my = 0;
    double x = 0;
    double y = 0;
    double vx = 0;
    double vy = 0;

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
    public void leapFrog(){
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
            vx = vx + (k * my * (-9.8) * dt * gamma);
        }
        if((vi < 0 && vx > 0) || (vi > 0 && vx < 0)){
            vx=0;
        }
             x = x + vx*dt;

       // vx = v_f;
        firstIterate = false;

    //    return r_f;
    }


    public void update(){
    //    System.out.println("Snopp");
     // x += vx;

      leapFrog();


    }
    public void renderBox(Graphics2D g) {

        g.drawImage(player, (int)Math.round(x - r), (int)Math.round(y - r),
                (int)Math.round(r*2), (int)Math.round(r*2), null);    // Draw player
    }

    public void set_vx (double vx){
        this.vx = vx;
    }
}

// Kirrade alla trassligheter. // CM
// Wilma was here