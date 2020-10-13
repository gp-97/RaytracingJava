package com.example.raytracing;

public class Sphere {
    private double[] center;
    private double radius;
    private int[] color;
    private double[] ambient;
    private double[] diffuse;
    private double[] specular;
    private double shininess;

    Sphere() {
        this.center = new double[3];
        this.radius = 0.0;
        this.color = new int[3];
        this.ambient = new double[3];
        this.diffuse = new double[3];
        this.specular = new double[3];
        this.shininess = 0.0;
    }
    Sphere(double[] center, double radius, int[] color, double[] ambient, double[] diffuse,
           double[] specular, double shininess) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    public void setCenter(double[] center) { this.center = center; }
    public void setRadius(double radius) { this.radius = radius; }
    public void setColor(int[] color) { this.color = color; }
    public void setAmbient(double[] ambient) { this.ambient = ambient; }
    public void setDiffuse(double[] diffuse) { this.diffuse = diffuse; }
    public void setSpecular(double[] specular) { this.specular = specular; }
    public void setShininess(double shininess) { this.shininess = shininess; }

    public double[] getCenter() { return this.center; }
    public double getRadius() { return this.radius; }
    public int[] getColor() { return this.color; }
    public double[] getAmbient() { return this.ambient; }
    public double[] getDiffuse() { return this.diffuse; }
    public double[] getSpecular() { return this.specular; }
    public double getShininess() { return this.shininess; }
}
