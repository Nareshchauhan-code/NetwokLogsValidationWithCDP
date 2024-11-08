import org.opencv.core.Core;
import org.opencv.core.Mat;

import static org.opencv.imgcodecs.Imgcodecs.IMREAD_COLOR;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.*;

public class TemplateValidation {


    public static boolean validateTemplate(String imagePath, String templatePath, double threshold) {

        nu.pattern.OpenCV.loadShared();


        // Load the image and template
        Mat image = imread(imagePath, IMREAD_COLOR);
        Mat template = imread(templatePath, IMREAD_COLOR);

        // Check if images are loaded successfully
        if (image.empty() || template.empty()) {
            System.out.println("Error loading image or template!");
            return false;
        }

        System.out.println("Images loaded successfully");

        // Convert both images to grayscale for matching
        Mat grayImage = new Mat();
        Mat grayTemplate = new Mat();
        cvtColor(image, grayImage, COLOR_BGR2GRAY);
        cvtColor(template, grayTemplate, COLOR_BGR2GRAY);

        // Perform template matching
        Mat result = new Mat();
        matchTemplate(grayImage, grayTemplate, result, TM_CCOEFF_NORMED);

        // Check the result of template matching
        if (result.empty()) {
            System.out.println("Template matching result is empty!");
            return false;
        }

        // Find the maximum match score and location
        Core.MinMaxLocResult mmr = new Core.MinMaxLocResult();
        double matchVal = mmr.maxVal;

        // Check if the match value exceeds the threshold
        System.out.println("Max match value: " + matchVal);
        if (matchVal >= threshold) {
            System.out.println("Template matched with score: " + matchVal);
            return true;
        } else {
            System.out.println("Template not found (score: " + matchVal + ")");
            return false;
        }
    }

    public static void main(String[] args) {
        // Specify paths to the image and template
        String imagePath = "GoogleFullImage.png"; // Path to the image to be searched
        String templatePath = "GoogleSmallImage.png"; // Path to the template image
        double threshold = 0.8; // Threshold for template matching (0.0 to 1.0)

        // Perform the validation
        boolean result = validateTemplate(imagePath, templatePath, threshold);

        // Print the result
        System.out.println("Template validation result: " + (result ? "Passed" : "Failed"));
    }
}
