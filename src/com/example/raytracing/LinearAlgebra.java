package com.example.raytracing;

import java.lang.Math;

public class LinearAlgebra {
    public static double[] vectorSubtraction(double[] vec1, double[] vec2) {
        double[] ret = new double[vec1.length];
        for(int i=0; i<vec1.length; ++i) {
            ret[i] = vec1[i] - vec2[i];
        }
        return ret;
    }
    public static double[] vectorAddition(double[] vec1, double[] vec2) {
        double[] ret = new double[vec1.length];
        for(int i=0; i<vec1.length; ++i) {
            ret[i] = vec1[i] + vec2[i];
        }
        return ret;
    }
    public static double dot(double[] vec1, double[] vec2) {
        double ret = 0.0;
        for(int i=0; i<vec1.length; ++i)
            ret += vec1[i] * vec2[i];
        return ret;
    }
    public static double normalize(double[] vector) {
        double vectorCoefficientSquareSum = 0.0;
        for(double val: vector)
            vectorCoefficientSquareSum += val*val;
        return Math.sqrt(vectorCoefficientSquareSum);
    }
    public static double[] vectorNormalize(double[] vector) {
        double denominator = LinearAlgebra.normalize(vector);
        for(int i=0; i<vector.length; ++i) {
            vector[i] /= denominator;
        }
        return vector;
    }
    public static double[] hadamard(double[] vec1, double[] vec2) {
        double dot[] = new double[vec1.length];
        for(int i=0; i<vec1.length; ++i)
            dot[i] = vec1[i]*vec2[i];
        return dot;
    }
    public static double[] scalarVectorMultiplication(double scalar, double[] vec) {
        for(int i=0; i<vec.length; ++i)
            vec[i] *= scalar;
        return vec;
    }
    public static double[] scalarVectorAddition(double scalar, double[] vec) {
        for(int i=0; i<vec.length; ++i)
            vec[i] += scalar;
        return vec;
    }
    public static double[] vectorPower(double[] vec, double power) {
        for(int i=0; i<vec.length; ++i)
            vec[i] = Math.pow(vec[i], power);
        return vec;
    }
    public static double[] reflected(double[] vec, double[] axis) {
        double[] tempAxis = LinearAlgebra.scalarVectorMultiplication(
                2*LinearAlgebra.dot(vec, axis), axis
        );
        return LinearAlgebra.vectorSubtraction(vec, tempAxis);
    }
    public static double[] clip(double[] vec, double low, double high) {
        for(int i=0; i<vec.length; ++i) {
            if(vec[i] < low)
                vec[i] = low;
            else if(vec[i] > high)
                vec[i] = high;
        }
        return vec;
    }
}
