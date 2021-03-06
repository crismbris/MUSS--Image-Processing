package tema2_0x_test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import tema2.auxiliary0X;

class closing_opening {
	static String file1 = "tema2_im/06a/immed_gray_inv_20051123_clo2ope2.pgm";
	static String file2 = "tema2_im/06a/closing_openingD2.pgm";
	static String file3 = "tema2_im/06a/immed_gray_inv_20051123_clo4ope4.pgm";
	static String file4="tema2_im/06a/closing_openingD4.pgm";
	public static BufferedImage image1 = UtilImageIO.loadImage(file1);
	public static BufferedImage image2 = UtilImageIO.loadImage(file2);
	public static BufferedImage image3 = UtilImageIO.loadImage(file3);
	public static BufferedImage image4 = UtilImageIO.loadImage(file4);
	public static GrayU8 im1= ConvertBufferedImage.convertFromSingle(image1, null, GrayU8.class);
	public static GrayU8 im2= ConvertBufferedImage.convertFromSingle(image2, null, GrayU8.class);
	public static GrayU8 im3= ConvertBufferedImage.convertFromSingle(image3, null, GrayU8.class);
	public static GrayU8 im4= ConvertBufferedImage.convertFromSingle(image4, null, GrayU8.class);

	@Test
	void testDim2() {
		boolean result = auxiliary0X.compare(im1, im2);
		assertTrue(result);
		}
	@Test
	void testDim4() {
		boolean result = auxiliary0X.compare(im3, im4);
		assertTrue(result);
		}

}
