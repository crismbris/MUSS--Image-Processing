package task01X;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileReader;

public class maximaRegion {
    public static void main(String[] args) {
        if (args.length != 2) {
        	System.out.println("Number of parameters wrong. ");
            System.out.println("In order to run the program the arguments should be: image input, image output");
            System.exit(1);
        }

        BufferedImage image = UtilImageIO.loadImage(args[0]);
        String out = args[1];

        if (image == null) { 
            System.out.println("Image not found");
            System.exit(1);
        }

        GrayU8 im8Original = ConvertBufferedImage.convertFromSingle(image, null, GrayU8.class);
        GrayU8 im8Aux = im8Original.clone();
        im8Aux = auxiliaryFunctions.flatzoneRegion(im8Original, Region.Maxima);
        
        

        UtilImageIO.saveImage(im8Aux, out);
        System.out.println("Output image file: " + out);
    }
}