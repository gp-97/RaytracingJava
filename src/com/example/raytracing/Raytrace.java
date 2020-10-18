package com.example.raytracing;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Raytrace {
    private Screen screenObj;
    private int width;
    private int height;
    private ArrayList<Sphere> sphereList;
    static int maxDepth = 3;

    Raytrace(int width, int height, ArrayList<Sphere> sphereList) {
        this.width = width;
        this.height = height;
        this.sphereList = sphereList;
    }

    private double sphereIntersect(double[] center, double radius,
                                   double[] rayOrigin, double[] rayDirection) {
        double b = 2 * LinearAlgebra.dot(rayDirection,
                LinearAlgebra.vectorSubtraction(rayOrigin, center));
        double c = Math.pow(LinearAlgebra.normalize(LinearAlgebra.vectorSubtraction(rayOrigin, center)), 2.0) -
                Math.pow(radius, 2.0);
        double delta = Math.pow(b, 2.0) - 4*c;
        double t1, t2;
        if(delta > 0.0) {
            t1 = (-b + Math.sqrt(delta)) / 2.0;
            t2 = (-b - Math.sqrt(delta)) / 2.0;

            if(t1 > 0 && t2 > 0)
                return Math.min(t1, t2);
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

    public double[][][] trace(int frameNumber, int totalFrames) {

        ImageArray imageObj = new ImageArray(height, width, 3);
        double[][][] image = imageObj.getImage();

        this.screenObj = new Screen(width, height);
        screenObj.createScreen();
        screenObj.createLinspaces();

        double[] heightLinspace = screenObj.getHeightLinspace();
        double[] widthLinspace = screenObj.getWidthLinspace();

        for (int y = 0; y < heightLinspace.length; y++) {
            double progress = (y * 1.0 / heightLinspace.length) * 100;
            String progressPercentage = Double.toString(progress).concat("000");
            System.out.print("\rTracing frame: "+frameNumber+"/"+totalFrames+
                    "\t\tProgress: "+progressPercentage.substring(0, 5).toString()+"%");
            for (int x = 0; x < widthLinspace.length; x++) {
                double[] pixel = {widthLinspace[x], heightLinspace[y], 0.0};
                double[] rayOrigin = Camera.camera;
                double[] rayDirection = LinearAlgebra.vectorNormalize(
                        LinearAlgebra.vectorSubtraction(pixel, rayOrigin)
                );

                double[] color = {0.05, 0.05, 0.05};
                double reflection = 1.0;

                for(int k=0; k<Raytrace.maxDepth; ++k) {
                    List<Object> ret = nearestIntersectedSphere(sphereList, rayOrigin, rayDirection);
                    Sphere nearestSphere = (Sphere) ret.get(0);
                    double minDistance = (double) ret.get(1);

                    if(nearestSphere == null)
                        break;

                    double[] intersection = LinearAlgebra.vectorAddition(rayOrigin,
                            LinearAlgebra.scalarVectorMultiplication(minDistance, rayDirection));


                    double[] normalToSurface = LinearAlgebra.vectorNormalize(
                            LinearAlgebra.vectorSubtraction(intersection, nearestSphere.getCenter())
                    );

                    double[] shiftedPoint = LinearAlgebra.vectorAddition(
                            intersection, LinearAlgebra.scalarVectorMultiplication(1.0+1e-5, normalToSurface)
                    );
                    double[] intersectionToLight = LinearAlgebra.vectorNormalize(
                            LinearAlgebra.vectorSubtraction(Light.lightPosition, shiftedPoint)
                    );

                    ret = nearestIntersectedSphere(sphereList, shiftedPoint, intersectionToLight);
                    double minDistance2 = (double) ret.get(1);

                    double intersectionToLightDistance = LinearAlgebra.normalize(
                            LinearAlgebra.vectorSubtraction(Light.lightPosition, intersection)
                    );

                    if(minDistance2 < intersectionToLightDistance)
                        break;

                    double[] illumination = {0.10, 0.10, 0.10};

                    illumination = LinearAlgebra.vectorAddition(
                            illumination, LinearAlgebra.hadamard(
                                    nearestSphere.getAmbient(), Light.lightAmbient
                            )
                    );

                    double dot = LinearAlgebra.dot(intersectionToLight, normalToSurface);

                    double[] tempDot = LinearAlgebra.hadamard(nearestSphere.getDiffuse(), Light.lightDiffuse);
                    tempDot = LinearAlgebra.scalarVectorMultiplication(dot, tempDot);

                    illumination = LinearAlgebra.vectorAddition(illumination, tempDot);

                    double[] intersectionToCamera = LinearAlgebra.vectorNormalize(
                            LinearAlgebra.vectorSubtraction(Camera.camera, intersection)
                    );

                    double[] H = LinearAlgebra.vectorNormalize(
                            LinearAlgebra.vectorAddition(intersectionToLight, intersectionToCamera)
                    );

                    double[] tempSpecular = LinearAlgebra.hadamard(
                            nearestSphere.getSpecular(), Light.lightSpecular
                    );
                    tempSpecular = LinearAlgebra.scalarVectorMultiplication(
                            LinearAlgebra.dot(normalToSurface, H), tempSpecular
                    );
                    double shininessCoefficient = nearestSphere.getShininess() / 4.0;

                    tempSpecular = LinearAlgebra.vectorPower(tempSpecular, shininessCoefficient);

                    illumination = LinearAlgebra.vectorAddition(illumination, tempSpecular);

                    color = LinearAlgebra.vectorAddition(
                            color, LinearAlgebra.scalarVectorMultiplication(reflection, illumination)
                    );
                    reflection *= nearestSphere.getReflection();

                    rayOrigin = shiftedPoint;
                    rayDirection = LinearAlgebra.reflected(rayDirection, normalToSurface);
                }
                double[] clippedColor = LinearAlgebra.clip(color, 0, 1);
                image[y][x] = LinearAlgebra.scalarVectorMultiplication(255.0, color);
            }
        }
        return image;
    }
}
