package com.example.raytracing;

public class Sphere {
    private double[] center;
    private double radius;
    private double[] ambient;
    private double[] diffuse;
    private double[] specular;
    private double shininess;
    private double reflection;

    Sphere() {
        this.center = new double[3];
        this.radius = 0.0;
        this.ambient = new double[3];
        this.diffuse = new double[3];
        this.specular = new double[3];
        this.shininess = 0.0;
        this.reflection = 0.0;
    }
    Sphere(double[] center, double radius, double[] ambient, double[] diffuse,
           double[] specular, double shininess, double reflection) {
        this.center = center;
        this.radius = radius;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
        this.reflection = reflection;
    }

    public void setCenter(double[] center) { this.center = center; }
    public void setRadius(double radius) { this.radius = radius; }
    public void setAmbient(double[] ambient) { this.ambient = ambient; }
    public void setDiffuse(double[] diffuse) { this.diffuse = diffuse; }
    public void setSpecular(double[] specular) { this.specular = specular; }
    public void setShininess(double shininess) { this.shininess = shininess; }
    public void setReflection(double reflection) { this.reflection = reflection; }

    public double[] getCenter() { return this.center; }
    public double getRadius() { return this.radius; }
    public double[] getAmbient() { return this.ambient; }
    public double[] getDiffuse() { return this.diffuse; }
    public double[] getSpecular() { return this.specular; }
    public double getShininess() { return this.shininess; }
    public double getReflection() { return this.reflection; }
}
