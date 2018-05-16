import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;


public class PhysicsCanvas extends Canvas implements Runnable {

    private boolean running;
    private Particle p1;
    private Particle p2;
    private Box b1;
    private Box b2;

    public PhysicsCanvas() {
        Dimension d = new Dimension(800, 600);
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);

        p1 = new Particle(750, 450, 20, 15, Color.RED);
    //    p2 = new Particle(550, 350, 20, 25, Color.BLUE);

        b1 = new Box (10, 40, 600, 560);
        b2 = new Box (20, 50, 200, 550);
    }

    public static void main(String[] args) {
        JFrame myFrame = new JFrame("My physics canvas");
        PhysicsCanvas physics = new PhysicsCanvas();
        myFrame.add(physics);
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // 		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
        physics.start();
        myFrame.setLocationRelativeTo(null);
    }

    @Override
    public void run() {
        while (running) {
            update();
            render();

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }

    public void start() {
        if (!running) {
            Thread t = new Thread(this);
            createBufferStrategy(3);
            running = true;
            t.start();
        }
    }

    private void render() {
        BufferStrategy strategy = getBufferStrategy();
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        p1.renderParticle(g);
     //   p2.renderParticle(g);
        b1.renderBox(g);
        b2.renderBox(g);

        strategy.show();
    }
   /* private void renderBox() {
        BufferStrategy strategy = getBufferStrategy();
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        b1.renderBox(g);
        b2.renderBox(g);

        strategy.show();
    }*/

    private void update() {
        p1.update(b1, b2);
        //p2.update(b1, b2);
        b1.update();
        b2.update();
    //    checkCollision();

    }

    private double ignoreBounce = 0;
    private double lastCollisionX = 0;
    private double lastCollisionY = 0;

/*
    public void checkCollision() {

        double deltaX = Math.abs(p1.getX() - p2.getX());
        double deltaY = Math.abs(p1.getY() - p2.getY());
        double distance = deltaX * deltaX + deltaY * deltaY;

        if (distance < (p1.getR() + p2.getR()) * (p1.getR() + p2.getR()) && ignoreBounce == 0) {
            if (p1.getX() < (lastCollisionX + p1.getR()) && p1.getX() > (lastCollisionX - p1.getR())
                    && p1.getY() < (lastCollisionY + p1.getR()) && p1.getY() > (lastCollisionY - p1.getR())) {
                p1.setVX((p1.getVX() * (p1.getR() - p2.getR()) + (2 * p2.getR() * p2.getVX())) / (p1.getR() + p2.getR()));
                p1.setVY((p1.getVY() * (p1.getR() - p2.getR()) + (2 * p2.getR() * p2.getVY())) / (p1.getR() + p2.getR()));
                p2.setVX((p2.getVX() * (p2.getR() - p1.getR()) + (2 * p1.getR() * p1.getVX())) / (p2.getR() + p1.getR()));
                p2.setVY((p2.getVY() * (p2.getR() - p1.getR()) + (2 * p1.getR() * p1.getVY())) / (p2.getR() + p1.getR()));
                lastCollisionX = p1.getX();
                lastCollisionY = p1.getY();
            }
        }
    }
*/
}
