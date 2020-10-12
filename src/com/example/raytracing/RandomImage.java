package com.example.raytracing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

class ImageIOOps {
    private final int width;
    private final int height;
    private BufferedImage image;
    ImageIOOps(int width, int height) {
        this.width = width;
        this.height = height;
        this.image = null;
    }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public BufferedImage getImageBuffer() { return this.image; }
    public void readImage(String pathname)throws IOException {
        File inp = new File(pathname);
        this.image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        this.image = ImageIO.read(inp);
    }
    public void writeImage(String pathname, String fileFormat)throws IOException {
        File op = new File(pathname);
        ImageIO.write(this.image, fileFormat, op);
        System.out.println("Writing complete...");
    }
}
public class RandomImage {
    public static void main(String[] args)throws IOException {
        ImageIOOps imgio = new ImageIOOps(2827, 2827);
        imgio.readImage("data/inputs/apple.png");
        System.out.println("Image dimensions: ("+imgio.getHeight()+", "+imgio.getWidth()+")");
        imgio.writeImage("data/outputs/apple.jpg", "jpg");
    }
}
