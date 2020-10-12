package com.example.raytracing;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int width = 10;
        int height = 10;
        int noOfSpheres = 1;

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
        customSphere1.setCenter(center1);
        customSphere1.setRadius(radius1);
        customSphere1.setColor(color1);
        sphereList.add(customSphere1);

        Sphere customSphere2 = new Sphere();
        double[] center2 = {0.1, -0.3, 0.0};
        double radius2 = 0.1;
        int[] color2 = {0, 0, 255};
        customSphere2.setCenter(center2);
        customSphere2.setRadius(radius2);
        customSphere2.setColor(color2);
        sphereList.add(customSphere2);

        Sphere customSphere3 = new Sphere();
        double[] center3 = {-0.3, 0, 0};
        double radius3 = 0.15;
        int[] color3 = {255, 0, 0};
        customSphere3.setCenter(center3);
        customSphere3.setRadius(radius3);
        customSphere3.setColor(color3);
        sphereList.add(customSphere3);

        Sphere customSphere4 = new Sphere();
        double[] center4 = {0, -9000.0, 0};
        double radius4 = 9000.0 - 0.7;
        int[] color4 = {128, 120, 120};
        customSphere4.setCenter(center4);
        customSphere4.setRadius(radius4);
        customSphere4.setColor(color4);
        sphereList.add(customSphere4);
        // TODO: ends

        Raytrace rt = new Raytrace(width, height, sphereList);
        rt.trace();
    }
}
