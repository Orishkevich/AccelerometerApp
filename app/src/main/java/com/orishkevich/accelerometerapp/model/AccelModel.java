package com.orishkevich.accelerometerapp.model;


public class AccelModel {

    private float x;
    private float y;
    private float z;
    private long mil;

    public AccelModel(float x, float y, float z, long mil) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.mil = mil;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public long getMil() {
        return mil;
    }

    public void setMil(long mil) {
        this.mil = mil;
    }


}
