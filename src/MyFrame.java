
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MyFrame extends JFrame {
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MyFrame frame = new MyFrame();
                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MyFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 490);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        new MyThread().start();
    }

    VideoCap videoCap = new VideoCap();

    public void paint(Graphics g){
        g = contentPane.getGraphics();
//        BufferedImage bufferedImage = videoCap.getOneFrame();
        Mat image = new Mat();
        boolean isSuccess = videoCap.cap.read(image);
        if (!isSuccess) {
            System.out.println("failed!");
            return;
        }

        Mat outputImage = new Mat();
        identifyObject(image, outputImage);
        BufferedImage bufferedImage = videoCap.mat2Img.getImage(outputImage);
        g.drawImage(bufferedImage, 0, 0, this);
        image.release();
        outputImage.release();
        bufferedImage.flush();
    }

    public void identifyObject(Mat src, Mat dst) {

        Mat gray = new Mat();
        Imgproc.cvtColor(src,gray, Imgproc.COLOR_BGR2GRAY);
//        Imgproc.GaussianBlur(gray, gray, new Size(3,3), 0);
        Imgproc.medianBlur(gray, gray, 3);
        Imgproc.adaptiveThreshold(gray, gray, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 75, 10);
        Imgproc.cvtColor(gray,gray, Imgproc.COLOR_GRAY2BGR);
        gray.copyTo(dst);
        // release native memory
        gray.release();

//        Mat blur_image = new Mat();
//        Imgproc.medianBlur(src, blur_image, 3);
//
//        Mat hsv_image = new Mat();
//        Imgproc.cvtColor(src, hsv_image, Imgproc.COLOR_BGR2HSV);
//
//        Mat lower_color_hue = create_hue_mask(hsv_image, new Scalar(0,100,100), new Scalar(10,255,255));
//        Mat upper_color_hue = create_hue_mask(hsv_image, new Scalar(160,100,100), new Scalar(179,255,255));
//
//        Mat full_image = new Mat();
//        Core.addWeighted(lower_color_hue, 1.0, upper_color_hue, 1.0, 0.0, full_image);
//
//        full_image.copyTo(dst);
//
//        // release native memory
//        blur_image.release();
//        hsv_image.release();
//        lower_color_hue.release();
//        upper_color_hue.release();
//        full_image.release();

    }

    private static Mat create_hue_mask(Mat image, Scalar lower_color, Scalar upper_color) {

        Mat mask = new Mat();
        Core.inRange(image, lower_color, upper_color, mask);
        Mat output_image = new Mat();
        Core.bitwise_and(image, image, output_image, mask);

        // release native memory
        mask.release();
        return output_image;


    }

    class MyThread extends Thread{
        @Override
        public void run() {
            for (;;){
                repaint();
                try {
                    Thread.sleep(30);
                }
                catch (InterruptedException e) {    }
            }
        }
    }


}