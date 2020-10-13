package com.example.raytracing;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int width = 1024;
        int height = 1024;
        int noOfSpheres = 4;

        ArrayList<Sphere> sphereList = new ArrayList<>(noOfSpheres);
//        for(int i=0; i<noOfSpheres; ++i) {
//            Sphere customSphere = new Sphere();
//            double[] center = {-0.2, 0.0, -0.1};
//            double radius = 0.7;
//            int[] color = {0, 255, 0};
//            customSphere.setCenter(center);
//            customSphere.setRadius(radius);
//            customSphere.setColor(color);
//            sphereList.add(customSphere);
//        }

        // TODO : Remove the below 3 spheres and integrate in loop
        Sphere customSphere1 = new Sphere();
        double[] center1 = {-0.2, 0.0, -0.1};
        double radius1 = 0.7;
        int[] color1 = {0, 255, 0};
        double[] ambient1 = {0.1, 0.0, 0.0};
        double[] diffuse1 = {0.7, 0.0, 0.0};
        double[] specular1 = {1.0, 1.0, 1.0};
        double shininess1 = 100.0;
        customSphere1.setCenter(center1);
        customSphere1.setRadius(radius1);
        customSphere1.setColor(color1);
        customSphere1.setAmbient(ambient1);
        customSphere1.setDiffuse(diffuse1);
        customSphere1.setSpecular(specular1);
        customSphere1.setShininess(shininess1);
        sphereList.add(customSphere1);

        Sphere customSphere2 = new Sphere();
        double[] center2 = {0.1, -0.3, 0.0};
        double radius2 = 0.1;
        int[] color2 = {0, 0, 255};
        double[] ambient2 = {0.1, 0.0, 0.1};
        double[] diffuse2 = {0.7, 0.0, 0.0};
        double[] specular2 = {1.0, 1.0, 1.0};
        double shininess2 = 100.0;
        customSphere2.setCenter(center2);
        customSphere2.setRadius(radius2);
        customSphere2.setColor(color2);
        customSphere2.setAmbient(ambient2);
        customSphere2.setDiffuse(diffuse2);
        customSphere2.setSpecular(specular2);
        customSphere2.setShininess(shininess2);
        sphereList.add(customSphere2);

        Sphere customSphere3 = new Sphere();
        double[] center3 = {-0.3, 0, 0};
        double radius3 = 0.15;
        int[] color3 = {255, 0, 0};
        double[] ambient3 = {0.0, 0.1, 0.0};
        double[] diffuse3 = {0.7, 0.0, 0.7};
        double[] specular3 = {1.0, 1.0, 1.0};
        double shininess3 = 100.0;
        customSphere3.setCenter(center3);
        customSphere3.setRadius(radius3);
        customSphere3.setColor(color3);
        customSphere3.setAmbient(ambient3);
        customSphere3.setDiffuse(diffuse3);
        customSphere3.setSpecular(specular3);
        customSphere3.setShininess(shininess3);
        sphereList.add(customSphere3);

        Sphere customSphere4 = new Sphere();
        double[] center4 = {0, -9000.0, 0};
        double radius4 = 9000.0 - 0.7;
        int[] color4 = {128, 120, 120};
        double[] ambient4 = {0.1, 0.0, 0.0};
        double[] diffuse4 = {0.0, 0.7, 0.0};
        double[] specular4 = {1.0, 1.0, 1.0};
        double shininess4 = 100.0;
        customSphere4.setCenter(center4);
        customSphere4.setRadius(radius4);
        customSphere4.setColor(color4);
        customSphere4.setAmbient(ambient4);
        customSphere4.setDiffuse(diffuse4);
        customSphere4.setSpecular(specular4);
        customSphere4.setShininess(shininess4);
        sphereList.add(customSphere4);
        // TODO: ends

        Raytrace rt = new Raytrace(width, height, sphereList);
        double[][][] ret = rt.trace();
    }
}
