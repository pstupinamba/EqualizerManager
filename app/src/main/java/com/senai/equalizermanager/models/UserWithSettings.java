package com.senai.equalizermanager.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.senai.equalizermanager.models.EqualizerSettings;
import com.senai.equalizermanager.models.User;

import java.util.List;

public class UserWithSettings {
    @Embedded
    private User user;

    @Relation(
            parentColumn = "id",
            entityColumn = "id_user"
    )
    private List<EqualizerSettings> settings;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<EqualizerSettings> getSettings() {
        return settings;
    }

    public void setSettings(List<EqualizerSettings> settings) {
        this.settings = settings;
    }
}
