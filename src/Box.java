import javax.imageio.ImageIO;

public class Box {
    double mass = 0;
    double size = 0;
    double fr = 0;
    double x = 0;
    double y = 0;
    double vx = 0;
    double vy = 0;

     public BufferedImage player;

    public Box(double mass, double size, double fr, double x, double y, double vx, double vy){
        this.mass = mass;
        this.size = size;
        this.fr = fr;
        this.x = x;
        this.y = y;

        try {
            player = ImageIO.read(new File(FileSystems.getDefault().getPath(
                    "src", "Box.png").toUri()));
        } catch (IOException e) {
            System.out.println("Image not found");
            player = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR);
        }
    }
}

