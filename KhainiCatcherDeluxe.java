import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KhainiCatcherDeluxe extends JPanel implements ActionListener, KeyListener {
    Timer timer;
    int paddleX = 250;
    int paddleWidth = 100;
    int paddleHeight = 15;
    int[] khainiX = new int[3];
    int[] khainiY = new int[3];
    int khainiSize = 25;
    int score = 0;
    int speed = 5;
    int lives = 3;
    boolean gameOver = false;

    public KhainiCatcherDeluxe() {
        timer = new Timer(30, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        for (int i = 0; i < khainiX.length; i++) {
            khainiX[i] = (int) (Math.random() * 500);
            khainiY[i] = (int) (Math.random() * -400);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Background
        g.setColor(new Color(244, 232, 201));
        g.fillRect(0, 0, 600, 450);
        
        // Paddle
        g.setColor(new Color(139, 69, 19));
        g.fillRect(paddleX, 400, paddleWidth, paddleHeight);
        
        // Khaini Packets
        g.setColor(Color.ORANGE);
        for (int i = 0; i < khainiX.length; i++) {
            g.fillOval(khainiX[i], khainiY[i], khainiSize, khainiSize);
        }

        // Score & Lives Display
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Khaini Score: " + score, 20, 30);
        g.drawString("Lives: " + lives, 20, 55);
        
        // Level Dialogues
        if (score >= 15 && score < 30) {
            g.drawString("Bhai, Speed Badh Gayi Hai!", 180, 30);
        } else if (score >= 30) {
            g.drawString("Khaini Ki Barsaat Ho Rahi Hai!", 160, 30);
        }

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Khaini gir gayi, Game Over!", 140, 200);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("Press 'R' to Restart", 200, 240);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            for (int i = 0; i < khainiX.length; i++) {
                khainiY[i] += speed;
                
                if (khainiY[i] + khainiSize >= 400 && khainiX[i] + khainiSize >= paddleX && khainiX[i] <= paddleX + paddleWidth) {
                    score++;
                    khainiY[i] = (int) (Math.random() * -200);
                    khainiX[i] = (int) (Math.random() * 500);
                    System.out.println("THOOK! Khaini pakdi gayi!"); // Sound Effect Text
                    
                    if (score % 15 == 0) {
                        speed++;  // Speed Up
                    }
                }
                
                if (khainiY[i] > 450) {
                    lives--;
                    khainiY[i] = (int) (Math.random() * -200);
                    khainiX[i] = (int) (Math.random() * 500);
                    if (lives == 0) {
                        gameOver = true;
                        timer.stop();
                    }
                }
            }
            repaint();
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT && paddleX > 0) {
            paddleX -= 30;
        }
        if (key == KeyEvent.VK_RIGHT && paddleX < 500) {
            paddleX += 30;
        }
        
        if (gameOver && key == KeyEvent.VK_R) {
            resetGame();
        }
    }

    public void resetGame() {
        paddleX = 250;
        score = 0;
        speed = 5;
        lives = 3;
        gameOver = false;
        for (int i = 0; i < khainiX.length; i++) {
            khainiX[i] = (int) (Math.random() * 500);
            khainiY[i] = (int) (Math.random() * -400);
        }
        timer.start();
        repaint();
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Khaini Catcher - Deluxe Desi Edition");
        KhainiCatcherDeluxe game = new KhainiCatcherDeluxe();
        frame.add(game);
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}