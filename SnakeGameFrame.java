import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakeGameFrame extends JFrame {

    public SnakeGameFrame() {
        // Set the title of the frame (window)
        this.setTitle("Snake Game");

        // Set the size of the frame
        this.setSize(600, 400);

        // Exit the application when the frame is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the frame to the center of the screen
        this.setLocationRelativeTo(null);

        // Adding the game panel (where the game will be played)
        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);

        // Make the frame visible
        this.setVisible(true);
        
        this.setResizable(false);
    }

    // Main method to run the game
    public static void main(String[] args) {
        new SnakeGameFrame();  // Create and display the Snake game frame
    }
}

// Panel where the Snake game will be drawn
class GamePanel extends JPanel implements ActionListener{
    private int dots;
    private Image apple;
    private Image dot;
    private Image head;
    private Timer timer;
    
    private final int All_dots = 900;
    private final int dot_size = 10;
    private final int RANDOM_POSITION = 29;
    private int apple_x;
    private int apple_y;
    
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private boolean inGame = true;
    
    private final int x[] = new int[All_dots];
    private final int y[] = new int[All_dots];
     
    GamePanel(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        loadImages();
        initGame();
        
    }
    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
        apple = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        dot = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
       head = i3.getImage();
    }
    public void initGame(){
        dots = 3;
        for(int i =0;i<dots;i++){
            y[i] = 50;
            x[i] = 50 - (dot_size*i);
        }
        
        locateApple();
        
        timer = new Timer(140,this);
        timer.start();
        
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void locateApple(){
        
        int r = (int)(Math.random() * RANDOM_POSITION);
        apple_x = r*dot_size;
        r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = r*dot_size;
        
    }
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, apple_x, apple_y, this);
        for(int i =0;i<dots;i++){
            if(i == 0)
                g.drawImage(head,x[i],y[i],this);
            else
                g.drawImage(dot, x[i], y[i], this);
        }
        Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
        
    }
    
    public void gameOver(Graphics g){
        String msg = "GameOver";
        Font font = new Font("SAN SARIF",Font.BOLD,14);
        FontMetrics metrices = getFontMetrics(font);
        g.setColor(Color.GREEN);
        g.drawString(msg, (600- metrices.stringWidth(msg))/2,400/2);
    }
    public void move(){
        for(int i = dots;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection){
            x[0] -= dot_size;
        }
        if(rightDirection){
            x[0] += dot_size;
        }
        if(upDirection){
            y[0] -= dot_size;
        }
        if(downDirection){
            y[0] += dot_size;
        }
//        x[0] += dot_size;
//        y[0] += dot_size;
    }
    
    public void checkApple(){
        if((x[0] == apple_x) && (y[0] == apple_y)){
            dots++;
            locateApple();
        }
    }
    public void checkcollision(){
        for(int i = dots -1 ; i>0 ; i--){
            if( (i>4) && (x[0]==x[i]) && (y[0]==y[i])){
                inGame = false; 
            }
        }
        if(y[0] >= 400){
            inGame = false;
        }
        if(x[0] >= 600){
            inGame = false;
        }
        if(y[0] < 0){
            inGame = false;
        }
        if(x[0] < 0){
            inGame = false;
        }
        if(!inGame){
           timer.stop();
        }
    }
    public void actionPerformed(ActionEvent ae){
        if(inGame){
        checkApple();
        checkcollision();
        move();
        repaint();
       }
    }
    public class TAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            int key  = e.getKeyCode();
            
            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if(key == KeyEvent.VK_UP && (!downDirection)){
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            
            if(key == KeyEvent.VK_DOWN && (!upDirection)){
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
}
