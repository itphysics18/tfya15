import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;


public class PhysicsCanvas extends Canvas implements Runnable {

    private boolean running;
    private Particle p1;
    private Particle p2;
    private Box b1;
    private Box b2;
    private Platform plat1;

    public PhysicsCanvas() {
        Dimension d = new Dimension(800, 600);
        setPreferredSize(d);
        setMinimumSize(d);
        setMaximumSize(d);

        double vinkel = Double.parseDouble(JOptionPane.showInputDialog("Vilken vinkel vill du skjuta ut bollen i?"));
        double tryck = Double.parseDouble(JOptionPane.showInputDialog("Vilket tryck vill du ha i bollen?"));

        double p1vx = Math.cos(Math.toRadians(vinkel)) * tryck;
        double p1vy = 0 - Math.sin(Math.toRadians(vinkel)) * tryck;

        p1 = new Particle(20, 580, 20, 15, Color.RED, p1vx, p1vy);

        b1 = new Box (10, 40, 350, 560);
        b2 = new Box (20, 50, 450, 170);
        //  g.fillRect(250, 220, 200, 50);
        plat1 = new Platform(350, 220, 200, 50, Color.cyan);
    }

    private static JFrame myFrame;

    public static void main(String[] args) {
        myFrame = new JFrame("Det magiska bollspelet");
        PhysicsCanvas physics = new PhysicsCanvas();
        myFrame.add(physics);
        myFrame.pack();
        myFrame.setResizable(false);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
        physics.start();
    //    System.out.println("Back in main");
        myFrame.setLocationRelativeTo(null);
        myFrame.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        physics.p1.boost(-1, 'y');
                        break;
                    case KeyEvent.VK_DOWN:
                        physics.p1.boost(1, 'y');
                        break;
                    case KeyEvent.VK_LEFT:
                        physics.p1.boost(-1, 'x');
                        break;
                    case KeyEvent.VK_RIGHT:
                        physics.p1.boost(1, 'x');
                        break;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                physics.p1.boost(0,'0');
            }
        });
    }

    private BufferStrategy strategy;
    private Graphics2D g;
    private Image img;

    @Override
    public void run() {
        strategy = getBufferStrategy();
        g = (Graphics2D) strategy.getDrawGraphics();
        try {
            img = ImageIO.read(new File(FileSystems.getDefault().getPath("src", "background.gif").toUri()));
        } catch (IOException e) {
            System.out.println(e);
        }
        int gameStatus = 0;
        while (running) {
            gameStatus = update();
            render();

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
            if (gameStatus != 0) {
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
        g.drawImage(img, 0,0,null);

        p1.renderParticle(g);
        b1.renderBox(g);
        b2.renderBox(g);
        plat1.renderPlatform(g);
        strategy.show();
    }

   private int update() {
        p1.update(b1, b2, plat1);
        b1.update(plat1);
        b2.update(plat1);

        if (b2.win()) {
            JOptionPane.showMessageDialog(this,
                    "Ta det lugnt med våra boxar!",
                    "Pang i bygget",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
        return 0;

    }

}
