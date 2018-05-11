import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class Box extends GameObject {
    private double fr = 0;
    private Color color;

    public BufferedImage player;

    public Box(double m, double r, double x, double y){
        this.m = m;
        this.r = r;
        this.x = x;
        this.y = y;
        this.fr = 0.4;
        this.vx = 0;
        this.vy = 0;

        try {
            player = ImageIO.read(new File(FileSystems.getDefault().getPath("src", "Box.png").toUri()));
        } catch (IOException e) {
            System.out.println("Image not found");
            player = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
        }
    }

    public void update (Particle p){
        m = p.getM();

    }

    public void renderBox(Graphics2D g) {

        g.drawImage(player, (int)Math.round(x - r), (int)Math.round(y - r),
                (int)Math.round(r*2), (int)Math.round(r*2), null);    // Draw player
    }

}