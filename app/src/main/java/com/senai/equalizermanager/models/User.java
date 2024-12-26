package com.senai.equalizermanager.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "username")
    private String username;
//    private UserPreferences preferences;

    public User(){
        this.id = 0;
        this.username = "";
//        this.preferences = new UserPreferences(60, 250, 4000);
    }

    public User(int id, String username, EqualizerSettings preferences) {
        this.id = id;
        this.username = username;
//        this.preferences = preferences;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public void SetPreferences(int low_freq, int mid_freq, int high_freq) throws Exception {
//        if(mid_freq < 250 || mid_freq > 4000){
//            throw new Exception();
//        }
//        this.preferences.setMid_freq(low_freq);
//        this.preferences.setMid_freq(mid_freq);
//        this.preferences.setHigh_freq(high_freq);
//    }
}
