package com.senai.equalizermanager.controllers;

import android.content.Context;
import androidx.room.Room;

import com.senai.equalizermanager.dao.AppDatabase;
import com.senai.equalizermanager.models.UserWithSettings;

import java.util.List;


public class UserWithSettingsController {
    private AppDatabase db;


    public UserWithSettingsController(Context context){
        this.db = Room.databaseBuilder(context, AppDatabase.class, "database-name").build();
    }

    public List<UserWithSettings> getAllUserWithSettings(){
        return db.userWithSettingsDao().getAllUsersWithSettings();
    }

    public UserWithSettings getUserWithSettingsById(int userId) throws Exception {
        UserWithSettings userWithSettings = db.userWithSettingsDao().getUserWithSettings(userId);
        if (userWithSettings == null) {
            throw new Exception("Usuário com configurações não encontrado.");
        }
        return userWithSettings;
    }

}
