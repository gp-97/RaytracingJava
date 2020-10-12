package com.example.raytracing;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Raytrace {
    private Screen screenObj;
    private int width;
    private int height;
    private ArrayList<Sphere> sphereList;

    Raytrace(int width, int height, ArrayList<Sphere> sphereList) {
        this.width = width;
        this.height = height;
        this.sphereList = sphereList;
    }

    private double sphereIntersect(double[] center, double radius,
                                   double[] rayOrigin, double[] rayDirection) {
//        double[] b = LinearAlgebra.scalarVectorMultiplication(2.0 * rayDirection,
//                LinearAlgebra.vectorSubtraction(rayOrigin, center));
        double b = 2 * LinearAlgebra.dot(rayDirection, LinearAlgebra.vectorSubtraction(rayOrigin, center));
//        System.out.println("b: "+b);
        double c = Math.pow(LinearAlgebra.normalize(LinearAlgebra.vectorSubtraction(rayOrigin, center)), 2.0) -
                Math.pow(radius, 2.0);
//        System.out.println("c: "+c);
        double delta = b*b - 4*c;
//        System.out.println("delta: "+delta);
        double t1, t2;
        if(delta > 0.0) {
            t1 = (-b + Math.sqrt(delta)) / 2.0;
            t2 = (-b - Math.sqrt(delta)) / 2.0;

            if(t1 > 0 && t2 > 0) {
                return Math.min(t1, t2);
            }
        }
        return Double.MIN_VALUE;
    }

    private List<Object> nearestIntersectedSphere(ArrayList<Sphere> spheres,
                                                  double[] rayOrigin, double[] rayDirection) {
        double[] distances = new double[spheres.size()];
        double minDistance = Double.MAX_VALUE;
        Sphere nearestSphere = null;
        int idx = 0;
        for(Sphere sphere: spheres) {
            double[] center = sphere.getCenter();
            double radius = sphere.getRadius();
            distances[idx++] = sphereIntersect(center, radius, rayOrigin, rayDirection);
        }
        for(int i=0; i<idx; ++i) {
            if((distances[i] != Double.MIN_VALUE) && (distances[i] < minDistance)) {
                minDistance = distances[i];
                nearestSphere = spheres.get(i);
            }
        }

        return Arrays.asList(nearestSphere, minDistance);
    }

    public void trace() {
        this.screenObj = new Screen(width, height);
        screenObj.createScreen();
        screenObj.createLinspaces();

        double[] heightLinspace = screenObj.getHeightLinspace();
        double[] widthLinspace = screenObj.getWidthLinspace();
        for (int y = 0; y < heightLinspace.length; ++y) {
            for (int x = 0; x < widthLinspace.length; ++x) {
                double[] pixel = {heightLinspace[x], widthLinspace[y], 0.0};
                double[] rayOrigin = Camera.camera;
                double[] rayDirection = LinearAlgebra.vectorSubtraction(pixel, rayOrigin);
                double direction = LinearAlgebra.normalize(rayDirection);

                List<Object> ret = nearestIntersectedSphere(sphereList, rayOrigin, rayDirection);
                Sphere nearestSphere = (Sphere) ret.get(0);
                double minDistance = (double) ret.get(1);

                if(nearestSphere == null)
                    continue;

                double[] intersection = LinearAlgebra.vectorAddition(rayOrigin, LinearAlgebra.scalarVectorMultiplication(minDistance,
                        LinearAlgebra.vectorNormalize(rayDirection)));

                
            }
            System.out.println("Progress: "+(y+1)+"/"+heightLinspace.length);
        }
    }
}
