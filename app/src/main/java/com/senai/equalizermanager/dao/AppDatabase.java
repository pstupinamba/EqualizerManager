package com.senai.equalizermanager.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ramonpapes.soundmanager.models.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
