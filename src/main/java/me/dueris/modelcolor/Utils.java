package me.dueris.modelcolor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.logging.Logger;
import java.awt.image.*;

import javax.imageio.ImageIO;

public class Utils {
    @SuppressWarnings("javax.imageio.IIOException")
        public static BufferedImage downloadImage(String imageUrl, String savePath, String fileName) throws IOException {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);
            File outputDir = new File(savePath);
            outputDir.mkdirs();

            File outputFile = new File(savePath, fileName + ".png");
            if(outputFile.exists()){
                Files.delete(outputFile.toPath());
            }
            ImageIO.write(image, "png", outputFile);

            return image;
        }

    public static Logger getLogger() {
        return Logger.getLogger("ModelColorAPI");
    }


    public static void saveImage(BufferedImage image, String savePath, String fileName) throws IOException {
        File outputDir = new File(savePath);
        outputDir.mkdirs();

        File outputFile = new File(savePath, fileName);
        if(outputFile.exists()){
            Files.delete(outputFile.toPath());
        }
        ImageIO.write(image, "png", outputFile);
    }
}
