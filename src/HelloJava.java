import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class HelloJava {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String filePath = "images/sheet2.jpg";
        Mat img = Imgcodecs.imread(filePath);

        Mat blur_image = new Mat();
        Imgproc.medianBlur(img, blur_image, 3);

        Mat hsv_image = new Mat();
        Imgproc.cvtColor(blur_image, hsv_image, Imgproc.COLOR_BGR2HSV);
        ImageViewer imageViewer = new ImageViewer();

        imageViewer.show(hsv_image, "HSV Image");

        Mat lower_color_hue = create_hue_mask(hsv_image, new Scalar(0,100,100), new Scalar(10,255,255));
        Mat upper_color_hue = create_hue_mask(hsv_image, new Scalar(160,100,100), new Scalar(179,255,255));

        int H1 = 120;
        int H2 = 180 - H1;

//        Mat lower_color_hue  = create_hue_mask(hsv_image, new Scalar(H1 - 10,50,50), new Scalar(H1 + 10,255,255));
//        Mat upper_color_hue  = create_hue_mask(hsv_image, new Scalar(H2 - 10,50,50), new Scalar(H2 + 10,255,255));

        Mat full_image = new Mat();
//
        Core.addWeighted(lower_color_hue, 1.0, upper_color_hue, 1.0, 0.0, full_image);

        Imgcodecs.imwrite("images/lower_color_hue.jpg", lower_color_hue);
        Imgcodecs.imwrite("images/upper_color_hue.jpg", upper_color_hue);
        Imgcodecs.imwrite("images/full_image.jpg", full_image);
    }

    private static Mat create_hue_mask(Mat image, Scalar lower_color, Scalar upper_color) {

        Mat mask = new Mat();
        Core.inRange(image, lower_color, upper_color, mask);
        Mat output_image = new Mat();
        Core.bitwise_and(image, image, output_image, mask);
        return output_image;

    }

    private static void getHSV() {
        Scalar color = new Scalar(0,0,0);
        Mat image = new Mat(1,1, CvType.CV_8UC3);
        image.setTo(color);
        Mat hsv_image = new Mat();
        Imgproc.cvtColor(image, hsv_image, Imgproc.COLOR_BGR2HSV);
        System.out.println(hsv_image.dump());
    }
}
