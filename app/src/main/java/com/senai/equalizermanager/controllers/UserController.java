package com.senai.equalizermanager.controllers;

import android.content.Context;

import androidx.room.Room;

import com.senai.equalizermanager.dao.AppDatabase;
import com.senai.equalizermanager.dao.UserDao;
import com.senai.equalizermanager.models.User;

public class UserController {
    private User user;
    private UserDao dao;
    private AppDatabase db;

    public UserController(Context context){
        this.user = new User();
        this.db = Room.databaseBuilder(context, AppDatabase.class, "database-name").build();
    }

    public User createUser(String username){
        user = new User();
        user.setUsername(username);
        db.userDao().insertAll(user);
        return user;
    }

}