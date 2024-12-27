package com.senai.equalizermanager.controllers;

import android.content.Context;

import androidx.room.Room;

import com.senai.equalizermanager.dao.AppDatabase;
import com.senai.equalizermanager.models.User;

import java.util.List;

public class UserController {
    private AppDatabase db;

    public UserController(Context context){
        this.db = Room.databaseBuilder(context, AppDatabase.class, "database-name").build();
    }

    public User createUser(String username) throws Exception{
        if(username == null || username.trim().isEmpty()){
            throw new Exception("O nome de usuário não pode ser vazio.");
        }
        User user = new User();
        user.setUsername(username);
        db.userDao().insertUser(user);
        return user;
    }

    public List<User> getAllUser(){
        return db.userDao().getAll();
    }

    public User getUserById(int id) throws Exception{
        User user = db.userDao().getUserById(id);
        if(user == null){
            throw new Exception("Usuário não encontrado.");
        }
        return user;
    }

    public void deleteUser(int id){
        User user = db.userDao().getUserById(id);
        db.userDao().delete(user);
    }
}