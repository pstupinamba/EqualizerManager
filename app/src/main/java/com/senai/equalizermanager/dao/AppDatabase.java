package com.senai.equalizermanager.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.senai.equalizermanager.models.EqualizerSettings;
import com.senai.equalizermanager.models.User;

@Database(entities = {User.class, EqualizerSettings.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract EqualizerSettingsDao equalizerSettingsDao();
    public abstract UserWithSettingsDao userWithSettingsDao();
}
