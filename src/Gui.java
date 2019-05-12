import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;

public class Gui {
    int Width;
    int Height;
    public Gui(int w, int h){
        Width=w;
        Height=h;
    }
    private JFrame frame;

    public void show() throws Exception{
        frame = new JFrame("CV Lab");
        //frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(Width,Height);

        JPanel pane = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                //g.drawImage(bufimg, 0, 0, null);
            }
        };
        frame.add(pane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
