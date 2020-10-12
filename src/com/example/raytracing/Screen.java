package com.example.raytracing;

public class Screen {
    private int width;
    private int height;
    private double[] screen;
    private double ratio;
    private double[] heightLinspace;
    private double[] widthLinspace;

    Screen(int width, int height) {
        this.width = width;
        this.height = height;
        this.screen = new double[4];
        this.ratio = (width*1.0) / height;
        this.heightLinspace = new double[this.height];
        this.widthLinspace = new double[this.width];
    }
    public void createScreen() {
        this.screen[0] = 1;
        this.screen[1] = 1 / this.ratio;
        this.screen[2] = -1;
        this.screen[3] = -1 / this.ratio;
    }
    public void createLinspaces() {
        this.heightLinspace[0] = this.screen[3];
        this.heightLinspace[this.height - 1] = this.screen[1];
        double heightConstant = (this.screen[3] - this.screen[1]) / (this.height - 1);
        for(int i=1; i<this.height - 1; ++i) {
            this.heightLinspace[i] = this.heightLinspace[0] - heightConstant * i;
        }

        this.widthLinspace[0] = this.screen[2];
        this.widthLinspace[this.width - 1] = this.screen[0];
        double widthConstant = (this.screen[2] - this.screen[0]) / (this.width - 1);
        for(int i=1; i<this.width - 1; ++i) {
            this.widthLinspace[i] = this.widthLinspace[0] - widthConstant * i;
        }
    }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public double[] getScreen() { return this.screen; }
    public double[] getHeightLinspace() { return this.heightLinspace; }
    public double[] getWidthLinspace() { return this.widthLinspace; }
}
