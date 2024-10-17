import javax.swing.*;
public class frame {
    public static void main(String[]args){
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLocation(500,500);
        frame.setTitle("My Frame");
        ImageIcon icon = new ImageIcon("\"E:\\Wallpaper\\pexels-maria-tyutina-954599.jpg\"");
        frame.setIconImage(icon.getImage());
        
    }
    
}
