package com.example.raytracing;

public class Sphere {
    private double[] center;
    private double radius;
    private int[] color;

    Sphere() {
        this.center = new double[3];
        this.radius = 0.0;
        this.color = new int[3];
    }
    Sphere(double[] center, double radius, int[] color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    public void setCenter(double[] center) { this.center = center; }
    public void setRadius(double radius) { this.radius = radius; }
    public void setColor(int[] color) { this.color = color; }

    public double[] getCenter() { return this.center; }
    public double getRadius() { return this.radius; }
    public int[] getColor() { return this.color; }
}
