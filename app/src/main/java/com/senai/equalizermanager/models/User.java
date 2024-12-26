package com.senai.equalizermanager.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    public User() {
        this.id = 0;
        this.username = "";
    }

    public User(long id, String username, EqualizerSettings preferences) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
