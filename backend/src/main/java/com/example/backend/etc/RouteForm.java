package com.example.backend.etc;

public class RouteForm {
    private double[][] path;
    private String[] instructions;

    public double[][] getPath() {
        return path;
    }
    public void setPath(double[][] path) {
        this.path = path;
    }
    public String[] getInstructions() {
        return instructions;
    }
    public void setInstructions(String[] instructions) {
        this.instructions = instructions;
    }
}
