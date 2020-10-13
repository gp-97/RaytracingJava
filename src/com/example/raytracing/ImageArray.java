package com.example.raytracing;

public class ImageArray {
    private double[][][] image;

    ImageArray(int height, int width, int channels) {
        this.image = new double[height][width][channels];
    }

    public void setImage(double[][][] image) { this.image = image; }
    public double[][][] getImage() { return this.image; }
}
