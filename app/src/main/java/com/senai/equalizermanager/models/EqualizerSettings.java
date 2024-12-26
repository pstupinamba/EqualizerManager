package com.senai.equalizermanager.models;

public class EqualizerSettings {
    private int low_freq;
    private int mid_freq;
    private int high_freq;

    public EqualizerSettings(){
        this.low_freq = 0;
        this.mid_freq = 0;
        this.high_freq = 0;
    }

    public EqualizerSettings(int low_freq, int mid_freq, int high_freq) {
        this.low_freq = low_freq;
        this.mid_freq = mid_freq;
        this.high_freq = high_freq;
    }

    public int getLow_freq() {
        return low_freq;
    }

    public void setLow_freq(int low_freq) {
        this.low_freq = low_freq;
    }

    public int getMid_freq() {
        return mid_freq;
    }

    public void setMid_freq(int mid_freq) {
        this.mid_freq = mid_freq;
    }

    public int getHigh_freq() {
        return high_freq;
    }

    public void setHigh_freq(int high_freq) {
        this.high_freq = high_freq;
    }
}
