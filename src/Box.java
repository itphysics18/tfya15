import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class Box {
    double mass = 0;
    double r = 0;
    double fr = 0;
    double x = 0;
    double y = 0;
    double vx = 0;
    double vy = 0;

    private Color color;

     public BufferedImage player;

    public Box(double mass, double r, double x, double y){
        this.mass = mass;
        this.r = r;
        this.x = x;
        this.y = y;
        this.fr = 0.4;
        this.vx = 0;
        this.vy = 0;

        try {
            player = ImageIO.read(new File(FileSystems.getDefault().getPath(
                    "src", "Box.png").toUri()));
        } catch (IOException e) {
            System.out.println("Image not found");
            player = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
        }
    }

    public void renderBox(Graphics2D g) {
        g.setColor(color);
        g.fillRect((int)Math.round(x-r), (int)Math.round(y-r), (int)Math.round(2*r), (int)Math.round(2*r));
    }
}

// Kirrade alla trassligheter. // CM
// Wilma was here