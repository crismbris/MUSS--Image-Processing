package task01X;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;

public class flatzone {
	
    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Number of parameters wrong. ");
            System.out.println("In order to run the program the arguments should be: image input, image output, x,y, label.");
            System.exit(1);
        }

        BufferedImage image = UtilImageIO.loadImage(args[0]);

        if (image == null) { 
            System.out.println("Image not found");
            System.exit(1);
        }

        String out = args[1];
        
        int x = Integer.parseInt(args[2]);
        int y = Integer.parseInt(args[3]);
        int label = Integer.parseInt(args[4]);

        

        GrayU8 im8Original = ConvertBufferedImage.convertFromSingle(image, null, GrayU8.class);
        GrayU8 im8Aux = im8Original.clone();
        im8Aux = auxiliaryFunctions.flatzone(im8Original, x, y, label);
        

        UtilImageIO.saveImage(im8Aux, out);
        System.out.println("Output image : " + out);
    }
}