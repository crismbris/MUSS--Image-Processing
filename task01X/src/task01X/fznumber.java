package task01X;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileReader;

public class fznumber {
    public static void writeOutMessage(String filename, int fz) {
        try {
            PrintWriter writer = new PrintWriter(filename);
            writer.println(fz);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

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
        int flatzones = auxiliaryFunctions.flatzoneNumber(im8Original);
        writeOutMessage(out, flatzones);
    }
}