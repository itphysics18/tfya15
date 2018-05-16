import java.awt.*;

public class Platform{

    private Color color;
    private int x;
    private int y;
    private int w;
    private int h;

    public Platform(int x, int y, int w, int h ,Color color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;

    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getW() { return w; }
    public double getH() { return h; }
    public void renderPlatform(Graphics2D g) {
        g.drawRect(x, y, w, h);
     //   g.drawRect(250, 220, 200, 50);
        g.setColor(Color.green);
        g.fillRect(x, y, w, h);
      //  g.fillRect(250, 220, 200, 50);
    }
}
