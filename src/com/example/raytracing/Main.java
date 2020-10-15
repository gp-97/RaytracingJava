package com.example.raytracing;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args)throws IOException {
        int width = 1024;
        int height = 1024;
        int noOfSpheres = 4;

        ArrayList<Sphere> sphereList = new ArrayList<>(noOfSpheres);

        // TODO : Remove the below 4 spheres and integrate in loop
        Sphere customSphere1 = new Sphere();
        double[] center1 = {0.0, 9000.0, 0.0};
        double radius1 = 9000 - 0.7;
        double[] ambient1 = {0.5, 0.5, 0.5};
        double[] diffuse1 = {0.7, 0.0, 0.7};
        double[] specular1 = {1.0, 1.0, 1.0};
        double shininess1 = 100.0;
        customSphere1.setCenter(center1);
        customSphere1.setRadius(radius1);
        customSphere1.setAmbient(ambient1);
        customSphere1.setDiffuse(diffuse1);
        customSphere1.setSpecular(specular1);
        customSphere1.setShininess(shininess1);
        sphereList.add(customSphere1);

        Sphere customSphere2 = new Sphere();
        double[] center2 = {0.5, -0.5, 0.0};
        double radius2 = 0.1;
        double[] ambient2 = {0.0, 0.2, 0.3};
        double[] diffuse2 = {0.7, 0.0, 0.7};
        double[] specular2 = {1.0, 1.0, 1.0};
        double shininess2 = 100.0;
        customSphere2.setCenter(center2);
        customSphere2.setRadius(radius2);
        customSphere2.setAmbient(ambient2);
        customSphere2.setDiffuse(diffuse2);
        customSphere2.setSpecular(specular2);
        customSphere2.setShininess(shininess2);
        sphereList.add(customSphere2);

        Sphere customSphere3 = new Sphere();
        double[] center3 = {0.0, 0.0, 0.0};
        double radius3 = 0.15;
        double[] ambient3 = {0.0, 1.0, 0.0};
        double[] diffuse3 = {0.7, 0.0, 0.7};
        double[] specular3 = {1.0, 1.0, 1.0};
        double shininess3 = 100.0;
        customSphere3.setCenter(center3);
        customSphere3.setRadius(radius3);
        customSphere3.setAmbient(ambient3);
        customSphere3.setDiffuse(diffuse3);
        customSphere3.setSpecular(specular3);
        customSphere3.setShininess(shininess3);
        sphereList.add(customSphere3);

        Sphere customSphere4 = new Sphere();
        double[] center4 = {-0.5, -0.3, 0};
        double radius4 = 0.1;
        double[] ambient4 = {1.0, 0.0, 0.0};
        double[] diffuse4 = {0.0, 0.7, 0.0};
        double[] specular4 = {1.0, 1.0, 1.0};
        double shininess4 = 100.0;
        customSphere4.setCenter(center4);
        customSphere4.setRadius(radius4);
        customSphere4.setAmbient(ambient4);
        customSphere4.setDiffuse(diffuse4);
        customSphere4.setSpecular(specular4);
        customSphere4.setShininess(shininess4);
        sphereList.add(customSphere4);
        // TODO: ends

        BufferedImage imgObj = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Raytrace rt = new Raytrace(width, height, sphereList);
        for(int i=0; i<90*10; ++i) {
            Light.lightPosition[0] += i/10.0;
            Light.lightPosition[2] += i/10.0;
            double[][][] img = rt.trace();
            for (int y = 0; y < height; ++y)
                for (int x = 0; x < width; ++x) {
                    double[] RGB = img[y][x];
                    int rgb = (int) RGB[0];
                    rgb = (rgb << 8) + (int) RGB[1];
                    rgb = (rgb << 8) + (int) RGB[2];
                    imgObj.setRGB(x, y, rgb);
                }
            String num = Integer.toString(i);
            File outputFile = new File("./Output/output"+num+".png");
            ImageIO.write(imgObj, "png", outputFile);
            System.out.println("Progress: "+i+"/900");
        }
    }
}
