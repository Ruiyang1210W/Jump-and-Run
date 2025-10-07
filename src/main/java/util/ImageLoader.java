package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageLoader {
    public static BufferedImage load(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResourceAsStream(path));
        } catch (Exception e) {
            System.err.println("Image not found: " + path);
            return null;
        }
    }
}
