import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;


public class PhysicsCanvas extends Canvas implements Runnable {

    private boolean running;
    private Particle p1;
    private Particle p2;


    public PhysicsCanvas() {
        Dimension d = new Dimension(800, 600);
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);

        Double vinkel = Double.parseDouble(JOptionPane.showInputDialog("Vilken vinkel vill du skjuta ut bollen i?"));
        Double tryck = Double.parseDouble(JOptionPane.showInputDialog("Vilket tryck vill du ha i bollen?"));

        Double p1vx = Math.cos(Math.toRadians(vinkel)) * tryck;
        Double p1vy = 0 - Math.sin(Math.toRadians(vinkel)) * tryck;

        p1 = new Particle(41, 559, 20, Color.blue, p1vx, p1vy);
        p2 = new Particle(550, 350, 5, Color.MAGENTA, 0, 0);
    }

    public static void main(String[] args) {
        JFrame myFrame = new JFrame("My physics canvas");
        PhysicsCanvas physics = new PhysicsCanvas();
        myFrame.add(physics);
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

        p1.render(g);
        p2.render(g);

        strategy.show();
    }

    private void update() {
        p1.update();
        p2.update();
    }
}
