package com.senai.equalizermanager.dao;//                for(User user : users){
//                    Log.d("User: ", "" + user.getId());
//                }

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.senai.equalizermanager.models.EqualizerSettings;
import com.senai.equalizermanager.models.User;

@Database(entities = {User.class, EqualizerSettings.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract EqualizerSettingsDao equalizerSettingsDao();
    public abstract UserWithSettingsDao userWithSettingsDao();
    
}
