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

    public double[][][] trace() {

        ImageArray imageObj = new ImageArray(height, width, 3);
        double[][][] image = imageObj.getImage();

        this.screenObj = new Screen(width, height);
        screenObj.createScreen();
        screenObj.createLinspaces();

        double[] heightLinspace = screenObj.getHeightLinspace();
        double[] widthLinspace = screenObj.getWidthLinspace();
        for (int y = 0; y < heightLinspace.length; ++y) {
            System.out.println("Progress: "+(y+1)+"/"+heightLinspace.length);
            for (int x = 0; x < widthLinspace.length; ++x) {
                double[] pixel = {heightLinspace[x], widthLinspace[y], 0.0};
                double[] rayOrigin = Camera.camera;
                double[] rayDirection = LinearAlgebra.vectorNormalize(
                        LinearAlgebra.vectorSubtraction(pixel, rayOrigin)
                );

                double[] color = {0.0, 0.0, 0.0};
                double reflection = 1.0;

                for(int k=0; k<Raytrace.maxDepth; ++k) {
                    List<Object> ret = nearestIntersectedSphere(sphereList, rayOrigin, rayDirection);
                    Sphere nearestSphere = (Sphere) ret.get(0);
                    double minDistance = (double) ret.get(1);

                    if(nearestSphere == null)
                        break;

                    double[] intersection = LinearAlgebra.vectorAddition(rayOrigin,
                            LinearAlgebra.scalarVectorMultiplication(minDistance,
                                    LinearAlgebra.vectorNormalize(rayDirection)));

                    double[] normalToSurface = LinearAlgebra.vectorNormalize(
                            LinearAlgebra.vectorSubtraction(intersection, nearestSphere.getCenter())
                    );

                    double[] shiftedPoint = LinearAlgebra.vectorAddition(
                            intersection, LinearAlgebra.scalarVectorMultiplication(1e-5, normalToSurface)
                    );
                    double[] intersectionToLight = LinearAlgebra.vectorNormalize(
                            LinearAlgebra.vectorSubtraction(Light.lightPosition, shiftedPoint)
                    );

                    ret = nearestIntersectedSphere(sphereList, shiftedPoint, intersectionToLight);
                    double minDistance2 = (double) ret.get(1);

                    double intersectionToLightDistance = LinearAlgebra.normalize(
                            LinearAlgebra.vectorSubtraction(Light.lightPosition, intersection)
                    );

                    boolean isShadowed = minDistance2 < intersectionToLightDistance;

                    if(isShadowed)
                        break;

                    double[] illumination = {0.0, 0.0, 0.0};

                    illumination = LinearAlgebra.vectorAddition(
                            illumination, LinearAlgebra.hadamard(
                                    nearestSphere.getAmbient(), Light.lightAmbient
                            )
                    );

                    double dot = LinearAlgebra.dot(intersectionToLight, normalToSurface);

                    illumination = LinearAlgebra.vectorAddition(
                            illumination, LinearAlgebra.hadamard(nearestSphere.getDiffuse(),
                                    nearestSphere.getDiffuse())
                    );

                    illumination = LinearAlgebra.scalarVectorMultiplication(dot, illumination);

                    double[] intersectionToCamera = LinearAlgebra.vectorNormalize(
                            LinearAlgebra.vectorSubtraction(
                                    Camera.camera, intersection
                            )
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

                    rayOrigin = shiftedPoint;
                    rayDirection = LinearAlgebra.reflected(rayDirection, normalToSurface);
                }
                double[] clippedColor = LinearAlgebra.clip(color, 0, 1);
                for(int iter=0; iter<3; ++iter)
                    image[y][x][iter] = clippedColor[iter];
            }
        }
        return image;
    }
}
