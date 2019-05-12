//import static com.googlecode.javacv.cpp.opencv_core.*;
//import static com.googlecode.javacv.cpp.opencv_highgui.*;
//import com.googlecode.javacv.CanvasFrame;
/*
import org.opencv.highgui.*;
import org.opencv.core.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.*;
*/
import com.sun.javaws.util.JfxHelper;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static com.sun.glass.ui.Cursor.setVisible;
import static java.awt.Component.CENTER_ALIGNMENT;
import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_COLOR;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class Main {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    public static void main(String[] args) throws Exception {
        new Gui(1000,700).show();
        /*
        BufferedImage im = ImageIO.read(new File("z5.jpg"));
        Mat originalMat = imread("z5.jpg");
        Mat grayMat = new Mat();
        Mat people = new Mat();

        //Converting the image to grayscale
        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);

        HOGDescriptor hog = new HOGDescriptor();
        hog.setSVMDetector(hog.getDefaultPeopleDetector());

        MatOfRect faces = new MatOfRect();
        MatOfDouble weights = new MatOfDouble();
        Size winStride = new Size(4, 4);
        hog.detectMultiScale(grayMat, faces, weights, 0.0, winStride, new Size(),
                1.01, 0.01, true);
        originalMat.copyTo(people);
        //Draw faces on the image
        Rect[] facesArray = faces.toArray();
        Graphics2D g = im.createGraphics();
        for (int i = 0; i < facesArray.length; i++)
            g.draw(new Rectangle(facesArray[i].x, facesArray[i].y, facesArray[i].width, facesArray[i].height));
        g.dispose();
        int cc = 5;*/
    }

    public static class Gui {
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
            JMenuBar menuBar = new JMenuBar();
            JMenu file = new JMenu("Файл");
            // Пункт меню "Открыть" с изображением
            JMenuItem open = new JMenuItem("Открыть",
                    new ImageIcon("images/open.png"));
            // Пункт меню из команды с выходом из программы

            // Добавим в меню пункта open
            file.add(open);
            // Добавление разделителя
            file.addSeparator();

            open.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    JFileChooser fileopen = new JFileChooser();
                    int ret = fileopen.showDialog(null, "Открыть файл");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        File pic = fileopen.getSelectedFile();
                        try {
                            BufferedImage img = ImageIO.read(pic);
                            img = hog(img);
                            BufferedImage finalImg = img;
                            /*
                            frame.setSize(finalImg.getWidth(),finalImg.getHeight());
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);*/
                            JPanel pane = new JPanel(){
                                @Override
                                protected void paintComponent(Graphics g){
                                    super.paintComponent(g);
                                    g.drawImage(finalImg, (1000-finalImg.getWidth())/2, (600-finalImg.getHeight())/2, null);
                                }
                            };
                            frame.add(pane);
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
            menuBar.add(file);
            frame.setJMenuBar(menuBar);
            frame.add(pane);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        public BufferedImage hog(BufferedImage img){
            BufferedImage im = img;
            Mat originalMat = bufferedImageToMat(img);
            Mat grayMat = new Mat();
            Mat people = new Mat();

            //Converting the image to grayscale
            Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);

            HOGDescriptor hog = new HOGDescriptor();
            hog.setSVMDetector(hog.getDefaultPeopleDetector());

            MatOfRect faces = new MatOfRect();
            MatOfDouble weights = new MatOfDouble();
            Size winStride = new Size(4, 4);
            hog.detectMultiScale(grayMat, faces, weights, 0.0, winStride, new Size(),
                    1.01, 0.01, true);
            originalMat.copyTo(people);
            //Draw faces on the image
            Rect[] facesArray = faces.toArray();
            Graphics2D g = im.createGraphics();
            g.setPaint(Color.red);
            g.setFont(new Font("Calibri", Font.BOLD, 20));
            if (facesArray.length==0) {
                g.setPaint(Color.red);
                g.drawString("NEGATIVE", 10, 30);
            }
            else{
                g.setPaint(Color.green);
                g.drawString("POSITIVE", 10, 30);
            }
            g.dispose();
            return im;
        }

        static BufferedImage deepCopy(BufferedImage bi) {
            ColorModel cm = bi.getColorModel();
            boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
            WritableRaster raster = bi.copyData(null);
            return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        }

        public static Mat bufferedImageToMat(BufferedImage bi) {
            Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
            byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
            mat.put(0, 0, data);
            return mat;
        }

    }

}
