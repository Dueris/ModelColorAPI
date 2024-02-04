package me.dueris.modelcolor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Color;
import java.awt.image.*;

import me.dueris.modelcolor.colortransformers.ColorTransformer;
import me.dueris.modelcolor.colortransformers.OriginsTransformer;

public class ModelColorAPI {
    private File saveDir = null;

    public static void main(String[] args){
        // Test code, not actual API
        final long curTime = System.currentTimeMillis();
        ModelColorAPI api = create("skins");
        BufferedImage img = api.createSourceFile(
            "https://s.namemc.com/i/f0982553f0070d57.png",
            "testingSource");
        try {
            api.createTransformed(
                img,
                new OriginsTransformer(),
                "testingSource-modified",
                0.25,
                0.75,
                0.25
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        Utils.getLogger().info("Finished ModelColor TEST application in: " + String.valueOf(System.currentTimeMillis() - curTime) + "ms");
        // Test end
    }

    public void setSaveDir(File file){
        saveDir = file;
    }

    public File getSaveDir(){
        return saveDir == null ? Paths.get("skins").toFile() : saveDir;
    }

    public static ModelColorAPI create(String saveOutput){
        return new ModelColorAPI(saveOutput);
    }

    private ModelColorAPI(String saveOutput){
        this.saveDir = Paths.get(saveOutput).toFile();
    }

    public BufferedImage createSourceFile(String url, String outputName){
        try {;
            Path saveDir = getSaveDir().toPath();
            if(!saveDir.toFile().exists()) {
                saveDir.toFile().mkdirs();
            }else{
                if(!saveDir.toFile().isDirectory()) {throw new RuntimeException("Provided saveDir isnt a valid directory");}
            }
            
            return Utils.downloadImage(url, getSaveDir().getAbsolutePath(), outputName);
        } catch (Exception throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public File createTransformed(BufferedImage file, ColorTransformer transformer, String saveName, double r, double g, double b) throws IOException{
        if(r > 1 || g > 1 || b > 1) throw new IllegalStateException("RGB values must be under 1");
        for (int x = 0; x < file.getWidth(); x++) {
            for (int y = 0; y < file.getHeight(); y++) {
                file.setRGB(x, y, transformer.transform(new Color(file.getRGB(x, y)), r, g, b).getRGB());
            }
        }

        Utils.saveImage(file, getSaveDir().getAbsolutePath(), saveName + ".png");
        return Paths.get(getSaveDir().getAbsolutePath() + File.separator + saveName + ".png").toFile();
    }
}