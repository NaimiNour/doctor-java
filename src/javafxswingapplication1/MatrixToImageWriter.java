/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxswingapplication1;
    import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import com.google.zxing.common.BitMatrix;

/**
 *
 * @author Bureau
 */
class MatrixToImageWriter {

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return image;
    }

    public static void saveQRCodeImage(BitMatrix matrix, String format, String filePath) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        File file = new File(filePath);
        ImageIO.write(image, format, file);
    }
}
    

