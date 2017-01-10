import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ColorConversion {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        getHSV();
    }

    private static void getHSV() {
        Mat image = new Mat(1,1, CvType.CV_8UC3, new Scalar(255,0,0));
        Mat hsv_image = new Mat();
        Imgproc.cvtColor(image, hsv_image, Imgproc.COLOR_BGR2HSV);
        System.out.println(hsv_image.dump());
    }

}
