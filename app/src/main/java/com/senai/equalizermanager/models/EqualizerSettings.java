package com.senai.equalizermanager.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "equalizer_settings", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id", childColumns = "id_user", onDelete = ForeignKey.CASCADE))
public class EqualizerSettings {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int id_user;
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

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
